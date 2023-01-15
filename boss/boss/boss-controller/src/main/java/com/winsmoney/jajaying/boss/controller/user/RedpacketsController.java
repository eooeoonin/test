package com.winsmoney.jajaying.boss.controller.user;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.RedmoneyInfoDomain;
import com.winsmoney.jajaying.boss.domain.model.RedmoneyInfo;
import com.winsmoney.jajaying.boss.domain.strategy.SendBatchMsgStrateay;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.msgcenter.service.IMessageService;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyService;
import com.winsmoney.jajaying.redmoney.service.enums.IssuerType;
import com.winsmoney.jajaying.redmoney.service.enums.MerchantCode;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneySourceTypeEnum;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneyTypeEnum;
import com.winsmoney.jajaying.redmoney.service.request.SendRedMoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyCommonResult;
import com.winsmoney.jajaying.redmoney.service.response.SendRedMoneyResVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 批量发红包
 *
 * @author Moon
 */
@Controller
@RequestMapping("/user")
public class RedpacketsController {
    Executor pool = Executors.newFixedThreadPool(3);
    @Autowired
    private RedmoneyInfoDomain redMoneyDomain;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRedMoneyService redMoneyService;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private SendBatchMsgStrateay sendBatchMsgStrateay;
    @Autowired
    private IMessageService messageService;

    /**
     * 按id取得能发的用户id列表
     *
     * @param ids
     * @return
     */
    @RequestMapping("redpackets/getCanSendUserIds")
    @ResponseBody
    public List<UserInfoResVo> listByIds(String userId) {
        String[] split = userId.split(";");
        List list2 = new ArrayList<>();
        for (String lsit : split) {
            list2.add(lsit);
        }
        UserCommonResult<List<UserInfoResVo>> listByIds = userInfoService.listByIds(list2);
        List<UserInfoResVo> businessObject = listByIds.getBusinessObject();
        return businessObject;
    }

    /**
     * 红包列表展示
     */
    @RequestMapping("/historyredpackets/sendRedMoney/list")
    @ResponseBody
    public PageInfo<RedmoneyInfo> list(RedmoneyInfo condition, int pageNo, int pageSize) {
        if (StringUtils.isBlank(condition.getType())) {
            condition.setType(null);
        }
        if (StringUtils.isBlank(condition.getUserId())) {
            condition.setUserId(null);
        }
        PageInfo<RedmoneyInfo> list = redMoneyDomain.list(condition, pageNo, pageSize);
        return list;
    }


    @RequestMapping("redpackets/sendRedMoneyWithMsg")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "批量发红包")
    public Result sendRedMoneyWithMsg(@RequestParam(value = "userIds[]") String[] userRoleIds, String smsContent, int expireDay, Money amount) {
        for (String userRoleId : userRoleIds) {
            UserCommonResult<UserInfoResVo> userInfoResult = userInfoService.getByRoleUserId(userRoleId);
            if (userInfoResult.isSuccess() && null != userInfoResult.getBusinessObject().getId()) {
                UserInfoResVo userInfo = userInfoResult.getBusinessObject();
                SendRedMoneyReqVo sendRedMoneyReqVo = new SendRedMoneyReqVo();
                if (StringUtils.isNotBlank(userInfo.getRealName())) {
                    sendRedMoneyReqVo.setRealName(userInfo.getRealName());
                }
                if (StringUtils.isNotBlank(userInfo.getFrom())) {
                    sendRedMoneyReqVo.setDeviceType(userInfo.getFrom());
                }
                if (StringUtils.isNotBlank(userInfo.getRegistrationId())) {
                    sendRedMoneyReqVo.setRegistrationId(userInfo.getRegistrationId());
                }
                if (StringUtils.isNotBlank(userInfo.getRegisterMobile())) {
                    sendRedMoneyReqVo.setMobile(userInfo.getRegisterMobile());
                }
                sendRedMoneyReqVo.setUserId(userRoleId);
                sendRedMoneyReqVo.setExpireDay(expireDay);
                sendRedMoneyReqVo.setAmount(amount);
                sendRedMoneyReqVo.setOutOrderNo(sequenceGenerator.getUUID());
                sendRedMoneyReqVo.setRedmoneyType(RedMoneyTypeEnum.CASH);
                sendRedMoneyReqVo.setSendSms(false);//是否发送短信 默认不发送
                sendRedMoneyReqVo.setSourceTypeEnum(RedMoneySourceTypeEnum.SYSTEM);
                sendRedMoneyReqVo.setTradeTime(new Date());
                sendRedMoneyReqVo.setMerchantCode(MerchantCode.P2P);
                sendRedMoneyReqVo.setIssuer(IssuerType.CUSTOMER);
                RedmoneyCommonResult<SendRedMoneyResVo> sendRedMoneyResult = redMoneyService.sendRedMoney(sendRedMoneyReqVo);
                RedmoneyInfo redmoneyInfo = new RedmoneyInfo();
                if (sendRedMoneyResult.isSuccess()) {
                    redmoneyInfo.setType("0");//状态
                } else {
                    redmoneyInfo.setType("1");
                }
                redmoneyInfo.setUserId(sendRedMoneyReqVo.getUserId());//用户ID
                redmoneyInfo.setAccountMoney(sendRedMoneyReqVo.getAmount());//红包金额
                redmoneyInfo.setRealName(sendRedMoneyReqVo.getRealName());//真实姓名
                redmoneyInfo.setTimes(sendRedMoneyReqVo.getExpireDay());//有效天数
                redmoneyInfo.setPhone(sendRedMoneyReqVo.getMobile());//手机号
                redmoneyInfo.setStatus("1");//1群发
                int insert = redMoneyDomain.insert(redmoneyInfo);
            }
        }
        //批量发红包时给所有用户发短信
        sendMsgBatch(userRoleIds, smsContent);
        return Result.success(null);
    }


    private void sendMsgBatch(String[] splits, final String smsContent) {
        int maxPhones = sendBatchMsgStrateay.getMaxPhones();
        List<List<String>> userIds = sendBatchMsgStrateay.splitList(Arrays.asList(splits), maxPhones);
        for (final List<String> ids : userIds) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    UserCommonResult<List<UserInfoResVo>> listUserCommonResult = userInfoService.listByIds(ids);
                    MessageReqVo userMobiles = sendBatchMsgStrateay.constructMobile(listUserCommonResult.getBusinessObject(), smsContent);
                    messageService.sendMsgForBoss(userMobiles);
                }
            });
        }
    }

}
