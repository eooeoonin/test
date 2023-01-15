package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.jajaying.basedata.service.client.SystemConfigClient;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.job.LockDomain;
import com.winsmoney.jajaying.boss.domain.job.SendSmsBatchDomain;
import com.winsmoney.jajaying.boss.domain.job.TriggerSendSmsJob;
import com.winsmoney.jajaying.boss.domain.utils.GenerateRedisKeyUtils;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.msgcenter.service.IMessageService;
import com.winsmoney.jajaying.msgcenter.service.enums.MessageType;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.ReceiverReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.MessageResVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.redis.Exception.LockException;
import com.winsmoney.platform.framework.redis.api.LockManager;
import com.winsmoney.platform.framework.redis.map.MapManager;

/**
 * 发短信
 * 
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt")
public class sendMsgController {
	private final static WinsMoneyLogger LOG = WinsMoneyLoggerFactory.getLogger(sendMsgController.class);
	private final static String BATCH_SMS_KEY = "L02_BATCHSMS";

	@Autowired
	private IMessageService messageService;
	@Autowired
	private TriggerSendSmsJob triggerSendSmsJob;
	@Autowired
	private MapManager mapManager;
	@Autowired
	private SystemConfigClient systemConfigClient;
	@Autowired
	private SendSmsBatchDomain sendSmsBatchDomain;
	@Autowired
	private LockDomain lockDomain;
	@Autowired
	private IUserInfoService userService;
	@Resource(name = "lockManager")
	LockManager lockManager;

	/**
	 * 发短信
	 * 
	 * @param sid
	 * @return
	 */
	@RequestMapping("/shortmessage_list/short")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "发短信")
	public Boolean sendMsgForBoss(String content, String phoneOrUserId,String typeSelect) {
		if(typeSelect.equals("1")){//手机号
		MessageReqVo messageReqVo = new MessageReqVo();
		messageReqVo.setContent(content);
		messageReqVo.setMerchant("P2P");
		messageReqVo.setType(MessageType.SMS);
		messageReqVo.setKey(BATCH_SMS_KEY);
		List<ReceiverReqVo> receiveres = new ArrayList<>();
		String[] split = phoneOrUserId.split(";");
		for (String lsit : split) {
			ReceiverReqVo receiverReqVo = new ReceiverReqVo();
			receiverReqVo.setCellphone(lsit);
			receiveres.add(receiverReqVo);
		}
		messageReqVo.setReceiveres(receiveres);
		MessageCommonResult<MessageResVo> sendMsgForBoss = messageService.sendMsgForBoss(messageReqVo);
		return sendMsgForBoss.isSuccess();
		}else if(typeSelect.equals("2")){//用户Id
			MessageReqVo messageReqVo = new MessageReqVo();
			messageReqVo.setContent(content);
			messageReqVo.setMerchant("P2P");
			messageReqVo.setType(MessageType.SMS);
			messageReqVo.setKey(BATCH_SMS_KEY);
			List<ReceiverReqVo> receiveres = new ArrayList<>();
			String[] split = phoneOrUserId.split(";");
			for (String lsit : split) {
				UserCommonResult<UserInfoResVo> byId = userService.getByRoleUserId(lsit);
				ReceiverReqVo receiverReqVo = new ReceiverReqVo();
				receiverReqVo.setCellphone(byId.getBusinessObject().getRegisterMobile());
				receiveres.add(receiverReqVo);
			}
			messageReqVo.setReceiveres(receiveres);
			MessageCommonResult<MessageResVo> sendMsgForBoss = messageService.sendMsgForBoss(messageReqVo);
			return sendMsgForBoss.isSuccess();
		}
		return null;
		

	}

	@RequestMapping("/shortmessage_list/batch")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "批量发短信")
	public Result sendSmsBatch(String content) {
		if (StringUtils.isBlank(content)) {
			return Result.error("短信内容不能为空");
		}
		String firstLockKey = GenerateRedisKeyUtils.getFirstSmsBatchLockKey();
		// 锁过期时间为1000秒， 获取锁超时间为10000毫秒
		Lock lock = lockManager.createLock(firstLockKey, 1000 * 1000, 1, TimeUnit.MILLISECONDS);
		try {
			boolean firstLock = lock.tryLock();
			if (firstLock) {
				String batchCountkey = GenerateRedisKeyUtils.getSmsBatchCountKey();
				boolean canSend = lockDomain.canSend(batchCountkey);
				LOG.info(">>>给平台所有用户发送短信canSend=" + canSend + "content=" + content);
				if (canSend) {
					sendSmsBatchDomain.sendSmsBatch(content, batchCountkey);
				} else
					return Result.error("超过每天批量发送短信限制，请联系管理员");
			} else {
				return Result.error("请勿重复提交");
			}
		} catch (LockException e) {
			return Result.error("请勿重复提交");
		} catch (Exception e) {
			LOG.error("给平台所有用户发送短信失败", e);
			return Result.error("给平台所有用户发送短信失败");
		} finally {
			lock.unlock();
		}
		return Result.success(null);
	}

}
