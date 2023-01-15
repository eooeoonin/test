package com.winsmoney.jajaying.boss.controller.trade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyQueryService;
import com.winsmoney.jajaying.redmoney.service.IRedmoneyTradeService;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneySourceTypeEnum;
import com.winsmoney.jajaying.redmoney.service.request.RedmoneyTradeExtendReqVo;
import com.winsmoney.jajaying.redmoney.service.request.RedmoneyTradeReqVo;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyCommonResult;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyTradeResVo;
import com.winsmoney.jajaying.trade.service.ITradeQueryService;
import com.winsmoney.jajaying.trade.service.request.MoneyRecordReqVo;
import com.winsmoney.jajaying.trade.service.response.MoneyRecordResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

/**
 * 流水管理
 */
@Controller
@RequestMapping(value = "/capital")
public class AccountLogRecord {
    private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AccountLogRecord.class);
    /*进页面的时候不查询，查询条件必须带开始时间结束时间
    1、批量（查询所有）：先查账务(带开始结束时间，取accountNo)---根据accountNo查user
	2、单笔（根据用户名或用户id查）：先查user(取accountNo)-----根据accountNo查账务（带开始结束时间）
	3、批量（按类型查询）：先查账务（取accountNo）---根据accoundNo查user*/

    @Autowired
    private IUserInfoService userInfoService;
//    @Autowired
//    private IAccountLogService accountLogService;
    @Autowired
    private IRedmoneyTradeService redmoneyTradeService;
    @Autowired
    private IRedMoneyQueryService redMoneyQueryService;
    @Autowired
    private ITradeQueryService tradeQueryService;


    /**
     * 红包流水
     */
    @RequestMapping(value = "/redmoneyrecord/getRedMoneyLog", method = RequestMethod.POST)
    @ResponseBody
    public Result getRedMoneyLog(RedMoneyAccountLogReqForm redMoneyAccountLogReqForm, String pageNo, String pageSize) {
        UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
        RedmoneyTradeExtendReqVo redMoneyAccountLogReqVo = new RedmoneyTradeExtendReqVo();
        PageInfo pageInfo = null;
        List<RedMoneyAccountLogResForm> forms = new ArrayList<RedMoneyAccountLogResForm>();
        if (StringUtils.isNotBlank(redMoneyAccountLogReqForm.getUserId()))
            userInfoReqVo.setId(redMoneyAccountLogReqForm.getUserId());
        if (StringUtils.isNotBlank(redMoneyAccountLogReqForm.getRegisterMobile()))
            userInfoReqVo.setRegisterMobile(redMoneyAccountLogReqForm.getRegisterMobile());
        if (StringUtils.isNotBlank(redMoneyAccountLogReqForm.getRedMoneySource())) {
            RedMoneySourceTypeEnum enums = RedMoneySourceTypeEnum.getEnums(redMoneyAccountLogReqForm.getRedMoneySource());
            List<RedMoneySourceTypeEnum> sources = new ArrayList<>();
            sources.add(enums);
            redMoneyAccountLogReqVo.setRedmoneyTypeList(sources);
        }
        logger.info("接口{}入参:" + JSONObject.toJSONString(userInfoReqVo), IUserInfoService.class);
        UserCommonResult<PageInfo<UserInfoResVo>> UserInfoResVo = userInfoService.listUserInfo(userInfoReqVo, 1, 1);
        logger.info("接口{}出参:" + JSONObject.toJSONString(UserInfoResVo), IUserInfoService.class);
        if (UserInfoResVo.getBusinessObject().getSize() > 0) {
            redMoneyAccountLogReqVo.setUserId(UserInfoResVo.getBusinessObject().getList().get(0).getId());
            RedmoneyCommonResult<PageInfo<RedmoneyTradeResVo>> redMoneyAccountLogResVoPage = null ;
            if( null != redMoneyAccountLogReqForm.getStartTime() && null != redMoneyAccountLogReqForm.getEndTime()) {
                redMoneyAccountLogReqVo.setStartTime(redMoneyAccountLogReqForm.getStartTime());
                redMoneyAccountLogReqVo.setEndTime(redMoneyAccountLogReqForm.getEndTime());
                logger.info("接口{}入参:" + JSONObject.toJSONString(redMoneyAccountLogReqVo));
                redMoneyAccountLogResVoPage = redMoneyQueryService.listByTime(redMoneyAccountLogReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            }else{
                    logger.info("接口{}入参:" + JSONObject.toJSONString(redMoneyAccountLogReqVo));
                    redMoneyAccountLogResVoPage = redmoneyTradeService.list(redMoneyAccountLogReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            }
            logger.info("接口{}出参:" + JSONObject.toJSONString(redMoneyAccountLogResVoPage));
            if (redMoneyAccountLogResVoPage.isSuccess()) {
                for (RedmoneyTradeResVo redmoneyTradeResVo : redMoneyAccountLogResVoPage.getBusinessObject().getList()) {
                    RedMoneyAccountLogResForm form = BeanMapper.map(redmoneyTradeResVo, RedMoneyAccountLogResForm.class);
                    form.setUserId(UserInfoResVo.getBusinessObject().getList().get(0).getId());
                    if (UserInfoResVo.getBusinessObject().getList().get(0).getRegisterMobile() != null)
                        form.setRegisterMobile(SensitiveInfoUtils.mobilePhone(UserInfoResVo.getBusinessObject().getList().get(0).getRegisterMobile()));
                    forms.add(form);
                }
                pageInfo = redMoneyAccountLogResVoPage.getBusinessObject();
                pageInfo.setList(forms);
                return Result.success(pageInfo);
            } else
                return Result.error(redMoneyAccountLogResVoPage.getResultCodeMsg());
        } else {
            return Result.error("请输入查询条件");
        }

    }


    /**
     * 单个用户红包流水列表
     */
    @RequestMapping(value = "/redmoneyrecord/getPersonalRedMoneyLog", method = RequestMethod.POST)
    @ResponseBody
    public Result getPersonalRedMoneyLog(RedmoneyTradeReqVo redmoneyTradeReqVo, String pageNo, String pageSize) {
        RedmoneyCommonResult<PageInfo<RedmoneyTradeResVo>> redMoneyAccountLogResVoPage = redmoneyTradeService.list(redmoneyTradeReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        if (redMoneyAccountLogResVoPage.isSuccess()) {
            return Result.success(redMoneyAccountLogResVoPage.getBusinessObject());
        }
        return Result.error(redMoneyAccountLogResVoPage.getResultCodeMsg());
    }

    /**
     * 资金管理-账户流水，实际查询的是交易
     */
    @RequestMapping(value = "/accountrecord/getTradeBizLog", method = RequestMethod.POST)
    @ResponseBody
    public Result getTradeBizLog(TradeBizReqForm tradeBizReqForm, int pageNo, int pageSize) {
        List<TradeBizResForm> tradeBizResForms = new ArrayList<>();
        PageInfo pageInfo;
        String mobile = tradeBizReqForm.getRegisterMobile();
        String userId = tradeBizReqForm.getUserId();
        UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
        userInfoReqVo.setRegisterMobile(mobile);
        if (StringUtils.isNotBlank(mobile)) {
            userInfoReqVo.setRegisterMobile(mobile);
        } else {
            userInfoReqVo.setRegisterMobile(null);
        }
        if (StringUtils.isNotBlank(userId)) {
            userInfoReqVo.setId(userId);
        }
        UserCommonResult<UserInfoResVo> userInfoResult = userInfoService.getByUserIdAndRegisterMobile(userInfoReqVo.getId(),userInfoReqVo.getRegisterMobile());
        if (userInfoResult.isSuccess() && null != userInfoResult.getBusinessObject().getId()) {
            userId = userInfoResult.getBusinessObject().getRoleUserId();
            tradeBizReqForm.setUserId(userId);
        } else return Result.error("根据手机号和userId获取用户信息异常");

        if (StringUtils.isNotBlank(userId)) {
            UserCommonResult<UserInfoResVo> userInfoById = userInfoService.getByRoleUserId(userId);
            mobile = userInfoById.getBusinessObject().getRegisterMobile();
        }
        MoneyRecordReqVo moneyRecordReqVo = BeanMapper.map(tradeBizReqForm, MoneyRecordReqVo.class);
        TradeCommonResult<PageInfo<MoneyRecordResVo>> tradeBizResult = tradeQueryService.queryMoneyRecordList(moneyRecordReqVo, pageNo, pageSize);
        if (tradeBizResult.isSuccess()) {
            pageInfo = tradeBizResult.getBusinessObject();
            for (MoneyRecordResVo moneyRecordResVo : tradeBizResult.getBusinessObject().getList()) {
                TradeBizResForm tradeBizResForm = BeanMapper.map(moneyRecordResVo, TradeBizResForm.class);
                tradeBizResForm.setRegisterMobile(SensitiveInfoUtils.mobilePhone(mobile));
                tradeBizResForms.add(tradeBizResForm);
            }
            pageInfo.setList(tradeBizResForms);
            return Result.success(pageInfo);
        } else return Result.error("查询交易异常");
    }

    public static void main(String[] args) {
        RedMoneySourceTypeEnum enums = RedMoneySourceTypeEnum.getEnums("lottery");
        System.out.println(enums);
    }
}
