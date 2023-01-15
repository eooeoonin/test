package com.winsmoney.jajaying.boss.service;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyQueryService;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyService;
import com.winsmoney.jajaying.redmoney.service.enums.IssuerType;
import com.winsmoney.jajaying.redmoney.service.enums.MerchantCode;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneySourceTypeEnum;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneyTypeEnum;
import com.winsmoney.jajaying.redmoney.service.request.QueryTotalRegisterRedmoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.request.RedmoneyTradeExtendReqVo;
import com.winsmoney.jajaying.redmoney.service.request.SendRedMoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.response.QueryTotalRegisterRedmoneyResVo;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyCommonResult;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyTradeResVo;
import com.winsmoney.jajaying.redmoney.service.response.SendRedMoneyResVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class RedmoneyServiceTest {
    @Autowired
    private IRedMoneyQueryService redMoneyQueryService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private IRedMoneyService redMoneyService;

    @Test
    public void testRedMoney() {
        RedmoneyTradeExtendReqVo redMoneyAccountLogReqVo = new RedmoneyTradeExtendReqVo();
//        redMoneyAccountLogReqVo.setUserId("C0812345678912ABCDEFGAC");
//        redMoneyAccountLogReqVo.setUserId("20170831C06374000000000000000009");
        redMoneyAccountLogReqVo.setStartTime(DateUtils.addDays(new Date(), -365));
        redMoneyAccountLogReqVo.setEndTime(new Date());
        RedmoneyCommonResult<PageInfo<RedmoneyTradeResVo>> pageInfoRedmoneyCommonResult = redMoneyQueryService.listByTime(redMoneyAccountLogReqVo, 1, 10);
        System.out.println(JSON.toJSONString(pageInfoRedmoneyCommonResult));
    }

    @Test
    public void testTotalRegisterRedmoney() {
        QueryTotalRegisterRedmoneyReqVo queryTotalRegisterRedmoneyReqVo = new QueryTotalRegisterRedmoneyReqVo();
        queryTotalRegisterRedmoneyReqVo.setStartTime(DateUtils.addDays(new Date(), -365));
        queryTotalRegisterRedmoneyReqVo.setEndTime(new Date());
        RedmoneyCommonResult<QueryTotalRegisterRedmoneyResVo> queryTotalRegisterRedmoney = redMoneyQueryService.queryTotalRegisterRedmoney(queryTotalRegisterRedmoneyReqVo);
        System.out.println(JSON.toJSONString(queryTotalRegisterRedmoney));
    }

    @Test
    public void testSendRedmoney() {
        SendRedMoneyReqVo sendRedMoneyReqVo = new SendRedMoneyReqVo();
        sendRedMoneyReqVo.setUserId("20170626C06test0000000000000000o");
        UserCommonResult<UserInfoResVo> listUserInfo = userInfoService.getByRoleUserId("20170626C06test0000000000000000o");
        sendRedMoneyReqVo.setRealName(listUserInfo.getBusinessObject().getRealName());
        sendRedMoneyReqVo.setRegistrationId(listUserInfo.getBusinessObject().getRegistrationId());
        sendRedMoneyReqVo.setDeviceType(listUserInfo.getBusinessObject().getFrom());
        sendRedMoneyReqVo.setMobile(listUserInfo.getBusinessObject().getRegisterMobile());
        sendRedMoneyReqVo.setOutOrderNo(sequenceGenerator.getUUID());
        sendRedMoneyReqVo.setTradeTime(new Date());
        sendRedMoneyReqVo.setRedmoneyType(RedMoneyTypeEnum.CASH);
        sendRedMoneyReqVo.setSourceTypeEnum(RedMoneySourceTypeEnum.SYSTEM);
        sendRedMoneyReqVo.setIssuer(IssuerType.CUSTOMER);
        sendRedMoneyReqVo.setMerchantCode(MerchantCode.P2P);
        sendRedMoneyReqVo.setAmount(new Money(6));
        sendRedMoneyReqVo.setExpireDay(7);
        sendRedMoneyReqVo.setSendSms(true);
        RedmoneyCommonResult<SendRedMoneyResVo> sendRedMoneyResult = redMoneyService.sendRedMoney(sendRedMoneyReqVo);
        System.out.println(sendRedMoneyResult);
    }

    @Test
    public void testAccount() {
//        AccountCommonResult<MainAccountInfoResVo> mainAccountInfoResVoAccountCommonResult = queryAccountService.queryGateWayAccountInfo("C0812345678912ABCDEFGAC");
//        AccountCommonResult<MainAccountInfoResVo> mainAccountInfoResVoAccountCommonResult = queryAccountService.queryGateWayAccountInfo("system_0001");
//        System.out.println(JSON.toJSONString(mainAccountInfoResVoAccountCommonResult));
    }

    @Test
    public void testLog(){
//        AccountLogReqVo accountLogReqVo = new AccountLogReqVo();
//        accountLogReqVo.setUserId(SystemUserId.PLATFORM.getUserId());
//        accountLogReqVo.setTransCode(TransCode.ADD_SYSTEM);
//        accountLogReqVo.setSubTransCode(TransCode.ADD_SYSTEM);
//        AccountCommonResult<PageInfo<AccountLogResVo>> result = accountLogService.list(accountLogReqVo, 1, 10, BusinessType.DEFAULT);
//        System.out.println(result);

    }

}













