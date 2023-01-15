package com.winsmoney.jajaying.boss.controller.platformManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsmoney.jajaying.user.service.IUserRoleInfoService;
import com.winsmoney.jajaying.user.service.request.UserRoleInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserRoleInfoResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.model.AccountBalance;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IAuthService;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.enums.RequestFrom;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.request.CommonApplyReqVo;
import com.winsmoney.jajaying.deposit.service.request.auth.CompanyBindCardApplyReqVo;
import com.winsmoney.jajaying.deposit.service.response.CommonApplyResVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.trade.service.IFundAllocationService;
import com.winsmoney.jajaying.trade.service.IPayService;
import com.winsmoney.jajaying.trade.service.IWithdrawService;
import com.winsmoney.jajaying.trade.service.enums.PayType;
import com.winsmoney.jajaying.trade.service.enums.WithdrawType;
import com.winsmoney.jajaying.trade.service.request.FundAllocationReqVo;
import com.winsmoney.jajaying.trade.service.request.PayReqVo;
import com.winsmoney.jajaying.trade.service.request.WithdrawApplyReqVo;
import com.winsmoney.jajaying.trade.service.response.PayApplyResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.trade.service.response.WithdrawApplyResVo;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.enums.EnterpriseType;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.LocalIPUtils;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

@Controller
@RequestMapping(value = "/platformManager")
public class AccountManController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AccountManController.class);

    @Autowired
    private IUserInfoService userInfoService;
	@Autowired
    private IUserRoleInfoService userRoleInfoService;
    @Autowired
    private IEnterpriseUserService enterpriseUserService;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IPayService payService;
    @Autowired
    private IWithdrawService withdrawService;
	@Autowired
	private SequenceGenerator sequenceGenerator;
	@Autowired
	private IFundAllocationService fundAllocationService;
	@Autowired
	private IInfoService infoService;


	@RequestMapping("/account/list")
	@ResponseBody
	public PageInfo<AccountBalance> list(EnterpriseUserReqVo enterpriseUserReqVo,int pageNo,int pageSize){
		Map<String,Object> params = new HashMap<>();
		List<EnterpriseType> list = new ArrayList<>();
		list.add( EnterpriseType.SYS_GENERATE_000 );
//		list.add( EnterpriseType.SYS_GENERATE_001 );
		list.add( EnterpriseType.SYS_GENERATE_002 );
//		list.add( EnterpriseType.SYS_GENERATE_003 );
		list.add( EnterpriseType.SYS_GENERATE_004 );
		list.add( EnterpriseType.SYS_GENERATE_005);
//		list.add( EnterpriseType.SYS_GENERATE_006 );
		params.put("enterpriseTypes", list);
		enterpriseUserReqVo.setParams(params);
		UserCommonResult<PageInfo<EnterpriseUserResVo>> result = enterpriseUserService.listEnterpriseUser( enterpriseUserReqVo , 0 , 10 );
		PageInfo<AccountBalance> newPage = new PageInfo<AccountBalance>();
		PageInfo<EnterpriseUserResVo> page = result.getBusinessObject();
		List<AccountBalance> accountBalanceList = BeanMapper.mapList(page.getList(), AccountBalance.class);
		for(AccountBalance accountBalance : accountBalanceList){
			String userId = accountBalance.getUserId();
			BaseReqVo baseReqVo=new BaseReqVo();
		    baseReqVo.setUserId(userId);
		    DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> get = infoService.queryUserInfo(baseReqVo);
			accountBalance.setBalance(get.getBusinessObject().getBalance());
			accountBalance.setFreezeAmount(get.getBusinessObject().getFreezeAmount());
			UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
			userRoleInfoReqVo.setUserRoleId( userId );
			UserCommonResult<UserRoleInfoResVo> getById = userRoleInfoService.get( userRoleInfoReqVo ) ;
			accountBalance.setAuthState(getById.getBusinessObject().getAuthState());
		}
		
		newPage = BeanMapper.map(page, PageInfo.class);
		newPage.setList(accountBalanceList);
		return newPage;
	}



    @RequestMapping(value = "/account/companyUnbind")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "平台账户解绑银行卡")
    public Result companyUnbind(String userId,HttpServletRequest httpRequest) {
        try {
        	String localIp = httpRequest.getServerName();
        	int port = httpRequest.getServerPort();
        	String htmlAddress = "/platformManager/account_Notify.html";
        	String callBackUrl = "http://" + localIp +":"+port + htmlAddress;
            CommonApplyReqVo commonApplyReqVo = new CommonApplyReqVo();
            commonApplyReqVo.setCallbackUrl("BOSS");
            commonApplyReqVo.setRequestId(sequenceGenerator.getUUID());
            commonApplyReqVo.setRequestTime(new Date());
            commonApplyReqVo.setFrom(RequestFrom.PC);
            commonApplyReqVo.setIp(LocalIPUtils.getIp4Single());
            commonApplyReqVo.setUserId(userId);
            commonApplyReqVo.setCallbackUrl(callBackUrl);
            DepositCommonResult<CommonApplyResVo> unbindCardResult = authService.unbindCardApply(commonApplyReqVo);
            if(unbindCardResult.isSuccess()){
                return Result.success(unbindCardResult.getBusinessObject());
            }else{
                return Result.error(unbindCardResult.getResultCodeMsg());
            }
        } catch (Exception e) {
            logger.error("平台用户解绑失败", e);
        }
        return Result.error("平台用户解绑失败");
    }

    @RequestMapping(value = "/account/recharge")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "平台账户充值")
    public Result recharge(PayReqVo payReqVo,HttpServletRequest httpRequest) {
        try {
        	String localIp = httpRequest.getServerName();
        	int port = httpRequest.getServerPort();
        	String htmlAddress = "/platformManager/account_Notify.html";
        	String callBackUrl = "http://" + localIp +":"+port + htmlAddress;
            payReqVo.setMerchant("P2P");
            payReqVo.setUserIp(LocalIPUtils.getIp4Single());
            payReqVo.setFrom(RequestFrom.PC.getCode());
            payReqVo.setPayType(PayType.NETBANK);
            payReqVo.setCallbackUrl(callBackUrl);
            TradeCommonResult<PayApplyResVo> result = payService.apply(payReqVo);
            if(result.isSuccess()){
                return Result.success(result.getBusinessObject());
            }else{
                return Result.error(result.getResultCodeMsg());
            }
        } catch (Exception e) {
            logger.error("平台用户充值失败", e);
        }
        return Result.error("平台用户充值失败");
    }

    @RequestMapping(value = "/account/open")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "平台账户开户")
	public Result openAccount(String id,HttpServletRequest httpRequest){
    	String localIp = httpRequest.getServerName();
    	int port = httpRequest.getServerPort();
    	String htmlAddress = "/platformManager/account_Notify.html";
    	String callBackUrl = "http://" + localIp +":"+port + htmlAddress;
    	String requestId = sequenceGenerator.getUUID();
    	String ip =	LocalIPUtils.getIp4Single();
		UserCommonResult<EnterpriseUserResVo> data = enterpriseUserService.getById(id);
		EnterpriseUserResVo resVo = data.getBusinessObject();
		CompanyBindCardApplyReqVo reqVo = new CompanyBindCardApplyReqVo();
		reqVo.setUserId(resVo.getUserId());
		reqVo.setRequestId(requestId);
		reqVo.setRequestTime(new Date());
		reqVo.setFrom(RequestFrom.PC);
		reqVo.setCallbackUrl(callBackUrl);
		if(null != resVo.getEnterpriseType()){
			String utype = resVo.getEnterpriseType().toString();
		}
		reqVo.setIp(ip);
        //非必输 start
        //非必输 end
        reqVo.setBankCode(resVo.getOpenAccountBank());//银行编码x
        reqVo.setBankCardNo(resVo.getBankAccount());//对公账户
        try {
        	DepositCommonResult<CommonApplyResVo> result= authService.companyBindCardApply(reqVo);
     
        if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
        }catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
	}
    @RequestMapping(value = "/withdraw")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "平台账户开提现")
    public Result withdraw(WithdrawApplyReqVo withdrawApplyReqVo,HttpServletRequest httpRequest) {
        try {
        	String localIp = httpRequest.getServerName();
        	int port = httpRequest.getServerPort();
        	String htmlAddress = "/platformManager/account_Notify.html";
        	String callBackUrl = "http://" + localIp +":"+port + htmlAddress;
            withdrawApplyReqVo.setMerchant("P2P");
            withdrawApplyReqVo.setUserIp(LocalIPUtils.getIp4Single());
            withdrawApplyReqVo.setFrom(RequestFrom.PC.getCode());
            withdrawApplyReqVo.setCallbackUrl(callBackUrl);
            withdrawApplyReqVo.setWithdrawType(WithdrawType.COMMON);
            TradeCommonResult<WithdrawApplyResVo> result = withdrawService.apply(withdrawApplyReqVo);
            if(result.isSuccess()){
                return Result.success(result.getBusinessObject());
            }else{
                return Result.error(result.getResultCodeMsg());
            }
        } catch (Exception e) {
            logger.error("平台用户提现失败", e);
        }
        return Result.error("平台用户提现失败");
    }

    @RequestMapping(value = "/account/getPlatformAccounts")
    @ResponseBody
    public Result getPlatformAccounts() {
        List<EnterpriseUserResVo> result = new ArrayList<EnterpriseUserResVo>();
        for (EnterpriseType enterpriseType : EnterpriseType.values()) {
            if(!EnterpriseType.COMMON.equals(enterpriseType) && !EnterpriseType.SYS_GENERATE_003.equals(enterpriseType) && !EnterpriseType.SYS_GENERATE_001.equals(enterpriseType)&& !EnterpriseType.SYS_GENERATE_006.equals(enterpriseType)){
                EnterpriseUserResVo enterpriseUserResVo = new EnterpriseUserResVo();
                enterpriseUserResVo.setId(enterpriseType.getCode());
                enterpriseUserResVo.setEnterpriseName(enterpriseType.getMessage());
                result.add(enterpriseUserResVo);
            }
        }
        return Result.success(result);
    }

    @RequestMapping("/transfer/balance")
	@ResponseBody
	public Result balance(String userId){
    	try{
    		BaseReqVo baseReqVo=new BaseReqVo();
		    baseReqVo.setUserId(userId);
		    DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> get = infoService.queryUserInfo(baseReqVo);
    	 if (get.isSuccess()) {
 			return Result.success(get);
 		} else {
 			return Result.error(get.getResultCodeMsg());
 		}
    	}catch(Exception e){
    		return Result.error("数据获取异常");
    	}
	}

    @RequestMapping("/transfer/init")
	@ResponseBody
	public Result transferInit(){
    	DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> result;
		List<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> air = new ArrayList<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo>();
		List<String> l = new ArrayList<String>();
		l.add("SYS_GENERATE_000");
		l.add("SYS_GENERATE_002");
		try{
			for(String userId : l){
				BaseReqVo baseReqVo=new BaseReqVo();
			    baseReqVo.setUserId(userId);
			    result = infoService.queryUserInfo(baseReqVo);
				air.add(result.getBusinessObject());
			}
			return Result.success(air);
		}catch(Exception e){
			return Result.error("数据获取异常");
		}
	}


    @RequestMapping("/transfer/subTran")
	@ResponseBody
	public Result subTran(String userIdIn, Money amount) {
		try {
			FundAllocationReqVo farv = new FundAllocationReqVo();
			farv.setAmount(amount);
			farv.setUserIdIn(userIdIn);
			TradeCommonResult<String> result = fundAllocationService.fundAllocation(farv);
			if (result.isSuccess()) {
				return Result.success(result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			return Result.error("数据获取异常");
		}

	}


    @RequestMapping(value = "/account/getPlatformAccount")
    @ResponseBody
    public Result getPlatformAccount(String userId) {
        try {
        	BaseReqVo baseReqVo=new BaseReqVo();
		    baseReqVo.setUserId(userId);
		    DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> result = infoService.queryUserInfo(baseReqVo);
            if(result.isSuccess()){
                return Result.success(result.getBusinessObject());
            }else  return Result.error("取得平台用户信息异常");
        } catch (Exception e) {
            return Result.error("取得平台用户信息异常");
        }
    }

}
