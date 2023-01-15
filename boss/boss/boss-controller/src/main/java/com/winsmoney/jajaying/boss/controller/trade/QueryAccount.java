package com.winsmoney.jajaying.boss.controller.trade;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
//import com.winsmoney.jajaying.pay.service.IPayService;
//import com.winsmoney.jajaying.pay.service.ITransactionLogService;
//import com.winsmoney.jajaying.pay.service.request.TransactionLogReqVo;
//import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
//import com.winsmoney.jajaying.pay.service.response.PayResVo;
//import com.winsmoney.jajaying.pay.service.response.TransactionLogResVo;
import com.winsmoney.jajaying.trade.service.IPayRecordService;
import com.winsmoney.jajaying.trade.service.request.UserRechargeWithdrawReqVo;
import com.winsmoney.jajaying.trade.service.response.PayRecordResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

/**
 * 充值管理
 *
 */
@Controller
@RequestMapping(value = "/capital/accountquery")
public class QueryAccount {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(QueryAccount.class);
	@Autowired
	private IPayRecordService payRecordService;
//	@Autowired
//	private IPayService iPayService;
//    @Autowired
//    private ITransactionLogService transactionLogService;
	SensitiveInfoUtils sensitiveInfoUtils = new SensitiveInfoUtils();
	
	@RequestMapping(value="getRecharge", method = RequestMethod.POST)
	@ResponseBody
	public Result getRecharge(UserRechargeWithdrawReqVo userRechargeWithdrawReqVo, String pageNo, String pageSize) {
		//userRechargeWithdrawReqVo.setFlowDirection(1);
		if(StringUtils.isBlank(userRechargeWithdrawReqVo.getRegisterMobile()))
			userRechargeWithdrawReqVo.setRegisterMobile(null);
		if(StringUtils.isBlank(userRechargeWithdrawReqVo.getUserId()))
			userRechargeWithdrawReqVo.setUserId(null);
		if(userRechargeWithdrawReqVo.getTransStatus() != null)
			if(StringUtils.isBlank(userRechargeWithdrawReqVo.getTransStatus().getCode()))
				userRechargeWithdrawReqVo.setTransStatus(null);
		TradeCommonResult<PageInfo<PayRecordResVo>> payRecordResVoList = payRecordService.listUserRechargeWithdrawBoss(userRechargeWithdrawReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		if(payRecordResVoList.isSuccess()) {
//			for(PayRecordResVo payRecordResVo: payRecordResVoList.getBusinessObject().getList()) {
//				if(payRecordResVo.getRegisterMobile() != null) {
//					payRecordResVo.setRegisterMobile(sensitiveInfoUtils.mobilePhone(payRecordResVo.getRegisterMobile()));
//				}
//				TransactionLogReqVo request = new TransactionLogReqVo();
//				request.setRequestId(payRecordResVo.getId());
//				PayCommonResult<TransactionLogResVo>  re = transactionLogService.get(request);
//				if (re.isSuccess()) {
//					payRecordResVo.setMark(re.getBusinessObject().getChannel());
//				}
//			}
			return Result.success(payRecordResVoList.getBusinessObject());
		}
		else
			return Result.error(payRecordResVoList.getResultCodeMsg());
		
	}
	
	@RequestMapping(value="getRecharge2", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<PayRecordResVo> getRecharge2(UserRechargeWithdrawReqVo userRechargeWithdrawReqVo, String pageNo, String pageSize) {
		
		
		TradeCommonResult<PageInfo<PayRecordResVo>> payRecordResVoList = payRecordService.listUserRechargeWithdrawBoss(userRechargeWithdrawReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		
		return payRecordResVoList.getBusinessObject();
		
	}
	
	
//	@RequestMapping(value="replacement", method = RequestMethod.POST)
//	@ResponseBody
//	
//	public PayCommonResult<PayResVo> replacement(String requestId){
//		PayCommonResult<PayResVo> result = iPayService.reviseByRequestId(requestId);
//		return result;
//		}
	
	
}
