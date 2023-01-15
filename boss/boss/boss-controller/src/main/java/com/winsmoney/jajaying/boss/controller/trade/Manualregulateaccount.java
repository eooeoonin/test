package com.winsmoney.jajaying.boss.controller.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.redmoney.service.enums.MerchantCode;
import com.winsmoney.jajaying.trade.service.IAdjustmentRecordService;
import com.winsmoney.jajaying.trade.service.IDepositAdjustmentService;
import com.winsmoney.jajaying.trade.service.request.AdjustmentRecordReqVo;
import com.winsmoney.jajaying.trade.service.request.DepositAdjustmentReqVo;
import com.winsmoney.jajaying.trade.service.response.AdjustmentRecordResVo;
import com.winsmoney.jajaying.trade.service.response.DepositAdjustmentResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

/**
 * 手动调帐
 */
@Controller
@RequestMapping(value = "/capital")
public class Manualregulateaccount {
    private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(Manualregulateaccount.class);

    @Autowired
    private IAdjustmentRecordService adjustmentRecordService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private IDepositAdjustmentService depositAdjustmentService;
    @Autowired
    private IInfoService infoService;
    
    //企业用户
    @Autowired
    private IEnterpriseUserService enterpriseUserService;

    /**
     * 单笔查询 企业用户
     *
     * @param request
     * @return EnterpriseUserResVo
     */
    @RequestMapping(value = "/{menu}/getfarenRecord", method = RequestMethod.POST)
    @ResponseBody
    public EnterpriseUserResVo getfarenRecord(EnterpriseUserReqVo request) {
        UserCommonResult<EnterpriseUserResVo> result = enterpriseUserService.getEnterpriseUser(request);
        return result.getBusinessObject();
    }

    /*
     * 获取调帐记录
     * */
    @RequestMapping(value = "/{menu}/getAdjustmentRecord", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<DepositAdjustmentResVo> getAdjustmentRecord(DepositAdjustmentReqVo adjustmentRecordReqVo, String pageNo, String pageSize) {
        if (StringUtils.isBlank(adjustmentRecordReqVo.getRealName())) {
            adjustmentRecordReqVo.setRealName(null);
        }
        if (StringUtils.isBlank(adjustmentRecordReqVo.getEditedBy())) {
            adjustmentRecordReqVo.setEditedBy(null);
        }
        if (adjustmentRecordReqVo.getAdjustmentType() != null)
            if (StringUtils.isBlank(adjustmentRecordReqVo.getAdjustmentType().getCode())) {
                adjustmentRecordReqVo.setAdjustmentType(null);
            }
        if (adjustmentRecordReqVo.getUserType() != null) {
            if (StringUtils.isBlank(adjustmentRecordReqVo.getUserType().getCode())) {
                adjustmentRecordReqVo.setUserType(null);
            }
        }
        if (StringUtils.isBlank(adjustmentRecordReqVo.getMobile()))
            adjustmentRecordReqVo.setMobile(null);
        TradeCommonResult<PageInfo<DepositAdjustmentResVo>> adjustmentRecord = depositAdjustmentService.list(adjustmentRecordReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        for (DepositAdjustmentResVo adjustmentRecordResVo : adjustmentRecord.getBusinessObject().getList()) {
            if (adjustmentRecordResVo.getMobile() != null)
                adjustmentRecordResVo.setMobile(SensitiveInfoUtils.mobilePhone(adjustmentRecordResVo.getMobile()));
        }

        return adjustmentRecord.getBusinessObject();
    }

    /**
     * 单笔查询 转账记录 t_adjustment_record
     */
    @RequestMapping(value = "/{menu}/getAdjustmentRecordinfo", method = RequestMethod.POST)
    @ResponseBody
    public AdjustmentRecordResVo getAdjustmentRecordinfo(AdjustmentRecordReqVo adjustmentRecordReqVo) {
        TradeCommonResult<AdjustmentRecordResVo> result = adjustmentRecordService.getAdjustmentRecord(adjustmentRecordReqVo);
        result.getBusinessObject().setMobile(SensitiveInfoUtils.mobilePhone(result.getBusinessObject().getMobile()));
        return result.getBusinessObject();
    }

    @RequestMapping(value = "/manualregulateaccount/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<UserInfoResVo> getUserInfo(UserInfoReqVo userInfoReqVo, String pageNo, String pageSize) {
        if (userInfoReqVo.getUserType() != null) {
            if (StringUtils.isBlank(userInfoReqVo.getUserType().getCode())) {
                userInfoReqVo.setUserType(null);
            }
        }
        if (StringUtils.isBlank(userInfoReqVo.getRealName())) {
            userInfoReqVo.setRealName(null);
        }
        if (StringUtils.isBlank(userInfoReqVo.getRegisterMobile())) {
            userInfoReqVo.setRegisterMobile(null);
        }
        if (StringUtils.isBlank(userInfoReqVo.getId())) {
            userInfoReqVo.setId(null);
        }
        UserCommonResult<PageInfo<UserInfoResVo>> userInfoResVoList = userInfoService.listUserInfo(userInfoReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        for (UserInfoResVo userInfoResVo : userInfoResVoList.getBusinessObject().getList()) {
            if (userInfoResVo.getRegisterMobile() != null)
                userInfoResVo.setRegisterMobile(SensitiveInfoUtils.mobilePhone(userInfoResVo.getRegisterMobile()));
        }
        return userInfoResVoList.getBusinessObject();
    }


    /*
     * 获取systemUserId
     * */
 /*   @RequestMapping(value = "/{menu}/getSystemUserId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSystemUserId() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", SystemUserId.PLATFORM.getUserId());
        return result;

    }*/

    /*
     * 手动转账
     * */
    @RequestMapping(value = "/manualregulateaccount/adjustUserAccount", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "手动转账")
    public Result adjustUserAccount(DepositAdjustmentReqVo adjustmentRecordReqVo, HttpServletRequest httpRequest) {
    	Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
    	adjustmentRecordReqVo.setEditedBy(staffSession.getRealName());
    	  logger.info("接口{}入参:" + JSONObject.toJSONString(adjustmentRecordReqVo), IAdjustmentRecordService.class);
          TradeCommonResult<String> result = depositAdjustmentService.depositAdjustment(adjustmentRecordReqVo);
          logger.info("接口{}出参:" + JSONObject.toJSONString(result), IAdjustmentRecordService.class);
          if (result.isSuccess()) {
              return Result.success(result);
          } else {
              return Result.error(result.getResultCodeMsg());
          }

    }


    /*
     * 查账户余额
     * */
    @RequestMapping(value = "/modifiedaccount/getBalance", method = RequestMethod.POST)
    @ResponseBody
    public Result getBalance(String userId) {
    	   BaseReqVo baseReqVo=new BaseReqVo();
           baseReqVo.setUserId(userId);
           DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> result = infoService.queryUserInfo(baseReqVo);
        if (result.isSuccess())
            return Result.success(result.getBusinessObject());
        else
            return Result.error(result.getResultCodeMsg());

    }

   /* 
     * 调账记录
     * 
    @RequestMapping(value = "/modifiedaccount/getSystemAccountLog", method = RequestMethod.POST)
    @ResponseBody
    public Result getSystemAccountLog(AccountLogReqVo accountLogReqVo, String pageNo, String pageSize) {
        AccountCommonResult<PageInfo<AccountLogResVo>> accountLogResVoPage = null;
        List<AccountLogResForm> forms = new ArrayList<AccountLogResForm>();
        accountLogResVoPage = accountLogService.list(accountLogReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize), BusinessType.DEFAULT);
        if (accountLogResVoPage.isSuccess()) {
            for (AccountLogResVo accountLogResVo : accountLogResVoPage.getBusinessObject().getList()) {
                AccountLogResForm form = BeanMapper.map(accountLogResVo, AccountLogResForm.class);
                if (accountLogResVo.getSubTransCode() != null)
                    form.setSubTransCodeCn(accountLogResVo.getSubTransCode().getMsg());
                forms.add(form);
            }
            PageInfo pageInfo = accountLogResVoPage.getBusinessObject();
            pageInfo.setList(forms);
            return Result.success(pageInfo);
        } else
            return Result.error(accountLogResVoPage.getResultCodeMsg());
    }
*/
    /*
     * 手动调账
     * system_0001-->调账
     * */
   /* @RequestMapping(value = "/modifiedaccount/modifiedaccount", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "手动调账")
    public Map<String, Object> modifiedaccount(RechargeReqVo rechargeReqVo) {
        Map<String, Object> result = new HashMap<String, Object>();
        rechargeReqVo.setOutOrderNo(sequenceGenerator.getUUID());
        rechargeReqVo.setTradeTime(new Date());
        rechargeReqVo.setMerchantCode(MerchantCode.FINANCING);
        logger.info("接口{}入参:" + JSONObject.toJSONString(rechargeReqVo));
        AccountCommonResult<RechargeResVo> adjustmentAccountResult = accountBusService.recharge(rechargeReqVo);
        logger.info("接口{}出参:" + JSONObject.toJSONString(adjustmentAccountResult));
        result.put("result", adjustmentAccountResult.getResultCodeMsg());
        return result;

    }
*/
    /*
     * 手动调账
     * system_0002、system_0003-->从system_0001户中转账
     * */
  /*  @RequestMapping(value = "/modifiedaccount/transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> transfer(AdjustmentAccountReqVo adjustmentAccountReqVo) {
        Map<String, Object> result = new HashMap<String, Object>();
        adjustmentAccountReqVo.setUserIdOut(SystemUserId.PLATFORM.getUserId());
        adjustmentAccountReqVo.setOutOrderNo(sequenceGenerator.getUUID());
        adjustmentAccountReqVo.setTradeTime(new Date());
        adjustmentAccountReqVo.setAccountInType(AccountType.Co);
        adjustmentAccountReqVo.setAccountOutType(AccountType.Co);
        logger.info("接口{}入参:" + JSONObject.toJSONString(adjustmentAccountReqVo));
        AccountCommonResult<AdjustmentAccountResVo> transferResVo = accountBusService.adjustmentAccount(adjustmentAccountReqVo);
        logger.info("接口{}出参:" + JSONObject.toJSONString(transferResVo), IAccountBusService.class);
        result.put("result", transferResVo.getResultCodeMsg());
        return result;

    }
*/
    /**
     * 获取当前用户
     */
    @RequestMapping(value = "/{menu}/getUnBankStaffName", method = RequestMethod.POST)
    @ResponseBody
    public String getUnBankStaffName(HttpServletRequest request) {
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        return staffSession.getStaffName();
    }

}
