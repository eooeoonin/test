package com.winsmoney.jajaying.boss.domain.job;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IErrorLogService;
import com.winsmoney.jajaying.basedata.service.enums.ErrorLogType;
import com.winsmoney.jajaying.basedata.service.request.ErrorLogReqVo;
import com.winsmoney.jajaying.boss.domain.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.strategy.SendBatchMsgStrateay;
import com.winsmoney.jajaying.boss.domain.utils.GenerateRedisKeyUtils;
import com.winsmoney.jajaying.msgcenter.service.IMessageService;
import com.winsmoney.jajaying.msgcenter.service.ISmsAgencyService;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.MessageResVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.enums.UserType;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.redis.Exception.LockException;
import com.winsmoney.platform.framework.redis.api.LockManager;
import com.winsmoney.platform.framework.redis.map.MapManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Component
public class SendSmsBatchJob implements SimpleJob {
	private final static WinsMoneyLogger LOG = WinsMoneyLoggerFactory.getLogger(SendSmsBatchJob.class);
	@Autowired
	private SendBatchMsgStrateay sendBatchMsgStrateay;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private ISmsAgencyService smsAgencyService;
	@Autowired
	private MapManager mapManager;
	@Autowired
	private IErrorLogService errorLogService;
	@Autowired
	private LockDomain lockDomain;
	@Resource(name = "lockManager")
	LockManager lockManager;

	@Override
	public void execute(ShardingContext shardingContext) {
		String smsContent = "";
		int pageSize = sendBatchMsgStrateay.getMaxPhones();
		String shardingParameter = shardingContext.getShardingParameter();
		String firstShardingCountKey = GenerateRedisKeyUtils.getFirstShardingCountKey(shardingParameter);
		Lock lock = lockManager.createLock(firstShardingCountKey, 3600 * 12 * 1000, 1, TimeUnit.MILLISECONDS);
		try {
			boolean lockFirst = lock.tryLock();
			if (lockFirst) {
				String shardingCountkey = GenerateRedisKeyUtils.getShardingCountKey(shardingParameter);
				boolean shardingCanSend = lockDomain.canSend(shardingCountkey);
				if (shardingCanSend) {
					String contentKey = GenerateRedisKeyUtils.getSmsBatchContentKey();
					smsContent = mapManager.get(contentKey, String.class);
					LOG.info(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s", Thread.currentThread().getId(), new Date(), shardingContext, "simple job"));
					UserInfoReqVo userInfoReqVo = constructUserReqVo(shardingParameter);
					int pageNum = 1;
					while (true) {
						LOG.info("请求user分页列表入参pageNum=" + pageNum + "pageSize=" + pageSize + "userInfoReqVo" + JSONObject.toJSONString(userInfoReqVo));
						UserCommonResult<PageInfo<UserInfoResVo>> usersInfo = userInfoService.listUserInfoByShard(userInfoReqVo, pageNum, pageSize);
						try {
							send(smsContent, usersInfo);
						} catch (Exception e) {
							saveErrorLog(smsContent, shardingContext, ErrorLogType.PAGE.getCode(), pageNum);
							LOG.error("分页发送短信时出错,shardingParameter= " + shardingParameter + "pageNum=" + pageNum + "smsContent=" + smsContent, e);
						}
						if (usersInfo.getBusinessObject().isIsLastPage()) {
							lockDomain.decrementCount(shardingCountkey);
							return;
						} else {
							pageNum++;
						}
					}
				} else {
					LOG.info("批量发送短信时该分片当天次数已用完, shardingParameter=" + shardingParameter + "shardingCanSend=" + shardingCanSend);
				}
			}
		} catch (LockException e) {
			LOG.error("获取分布式锁异常" + JSONObject.toJSONString(shardingContext), e);
		} catch (BusinessException e) {
			LOG.error("给平台所有用户发短信,分片参数次数扣减时异常，分片参数为" + JSONObject.toJSONString(shardingContext), e);
		} catch (Exception e) {
			saveErrorLog(smsContent, shardingContext, ErrorLogType.SHARDING.getCode(), 0);
			LOG.error("给平台所有用户发短信时整个分片数据失败,分片参数为" + JSONObject.toJSONString(shardingContext), e);
		} finally {
			lock.unlock();
		}
	}

	private UserInfoReqVo constructUserReqVo(String shardingParameter) {
		UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
		userInfoReqVo.setUserType(UserType.PERSON);
		userInfoReqVo.setId(shardingParameter);
		return userInfoReqVo;
	}

	private void send(String smsContent, UserCommonResult<PageInfo<UserInfoResVo>> usersInfo) {
		if (!usersInfo.isSuccess()) {
			LOG.error("调用user listUserInfoByShard 服务异常");
		}
		if (usersInfo.isSuccess() && null != usersInfo.getBusinessObject() && !usersInfo.getBusinessObject().getList().isEmpty()) {
			MessageReqVo userMobiles = sendBatchMsgStrateay.constructMobile(usersInfo.getBusinessObject().getList(), smsContent);
			MessageCommonResult<MessageResVo> sendMsgForBoss = messageService.sendMsgForBoss(userMobiles);
			if (!sendMsgForBoss.isSuccess()) {
				throw new BusinessException(BossErrorCode.PAGE_ERROR);
			}
		}
	}



	private void saveErrorLog(String smsContent, ShardingContext shardingContext, String type, int pageNum) {
		ErrorLogReqVo errorLogReqVo = new ErrorLogReqVo();
		errorLogReqVo.setType(type);
		errorLogReqVo.setContent(smsContent);
		errorLogReqVo.setShardingParameter(shardingContext.getShardingParameter());
		if (pageNum > 0) {
			errorLogReqVo.setPageNum(pageNum);
		}
		errorLogReqVo.setErrorMsg(JSONObject.toJSONString(shardingContext));
		errorLogService.insert(errorLogReqVo);
	}



}
