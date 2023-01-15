//package com.winsmoney.jajaying.boss.controller.platformManager;
//
//import com.winsmoney.framework.pagehelper.PageInfo;
//import com.winsmoney.jajaying.account.service.IAccountLogService;
//import com.winsmoney.jajaying.account.service.IQueryAccountService;
//import com.winsmoney.jajaying.account.service.request.AccountLogReqVo;
//import com.winsmoney.jajaying.account.service.response.AccountCommonResult;
//import com.winsmoney.jajaying.account.service.response.AccountInfoResVo;
//import com.winsmoney.jajaying.account.service.response.AccountLogResVo;
//import com.winsmoney.jajaying.boss.controller.model.AccountBalance;
//import com.winsmoney.jajaying.boss.domain.utils.Result;
//import com.winsmoney.jajaying.deposit.service.IAuthService;
//import com.winsmoney.jajaying.deposit.service.enums.RequestFrom;
//import com.winsmoney.jajaying.deposit.service.request.CommonApplyReqVo;
//import com.winsmoney.jajaying.deposit.service.response.CommonApplyResVo;
//import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
//import com.winsmoney.jajaying.trade.service.IPayService;
//import com.winsmoney.jajaying.trade.service.IWithdrawService;
//import com.winsmoney.jajaying.trade.service.enums.PayType;
//import com.winsmoney.jajaying.trade.service.request.PayReqVo;
//import com.winsmoney.jajaying.trade.service.response.PayApplyResVo;
//import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
//import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
//import com.winsmoney.jajaying.user.service.IUserInfoService;
//import com.winsmoney.jajaying.user.service.enums.EnterpriseType;
//import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
//import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
//import com.winsmoney.jajaying.user.service.response.UserCommonResult;
//import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
//import com.winsmoney.platform.framework.core.util.BeanMapper;
//import com.winsmoney.platform.framework.core.util.DateUtil;
//import com.winsmoney.platform.framework.core.util.LocalIPUtils;
//import com.winsmoney.platform.framework.core.util.Money;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.*;
//
//@Controller
//@RequestMapping(value = "/platformManager/accountflow")
//public class AccountFlowController {
//
//	@Autowired
//	private IAccountLogService accountLogService;
//
//
//    
//	@RequestMapping(value="/account")
//	@ResponseBody
//	public PageInfo<AccountLogResVo> accountflow(AccountLogReqVo request,Integer pageNo, Integer pageSize){
//		if(request.getTransAccount()==""){
//			request.setTransAccount(null);
//		}
//		if(request.getOtherAccount()==""){
//			request.setOtherAccount(null);
//		}
//	
//		
//		if(request.getOutOrderNo()==""){
//			request.setOutOrderNo(null);
//		}
//		request.setAccountType("Sys");
//		AccountCommonResult<PageInfo<AccountLogResVo>> list = accountLogService.list(request, pageNo, pageSize, null);
//		return list.getBusinessObject();
//	}
//
//
//}
