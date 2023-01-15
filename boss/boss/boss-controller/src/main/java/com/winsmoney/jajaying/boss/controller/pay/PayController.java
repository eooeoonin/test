package com.winsmoney.jajaying.boss.controller.pay;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.pay.service.IPayService;
import com.winsmoney.jajaying.pay.service.ITransactionLogService;
import com.winsmoney.jajaying.pay.service.enums.OrderStatus;
import com.winsmoney.jajaying.pay.service.request.TransactionLogReqVo;
import com.winsmoney.jajaying.pay.service.request.TransactionLogSearchReqVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.jajaying.pay.service.response.PayResVo;
import com.winsmoney.jajaying.pay.service.response.TransactionLogResVo;
import com.winsmoney.jajaying.trade.service.IPayRecordService;
import com.winsmoney.jajaying.trade.service.request.UserRechargeWithdrawReqVo;
import com.winsmoney.jajaying.trade.service.response.PayRecordResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;
/*
 * 网关
 * */
@Controller
@RequestMapping(value="/pay")
public class PayController {

	@Autowired
	private IPayRecordService payRecordService;
	@Autowired
	private ITransactionLogService transactionLogService;
	
	@Autowired
	private IPayService iPayService;
	
	SensitiveInfoUtils sensitiveInfoUtils = new SensitiveInfoUtils();
	
	@RequestMapping(value="/transactionlog/getLog",method=RequestMethod.POST)
	@ResponseBody
	public Result getLog(TransactionLogSearchReqVo transactionLogReqVo, int pageNo, int pageSize) {
		if(StringUtils.isBlank(transactionLogReqVo.getBankMobile()))
			transactionLogReqVo.setBankMobile(null);
		if(transactionLogReqVo.getBusiness() != null)
			if(StringUtils.isBlank(transactionLogReqVo.getBusiness().getCode()))
				transactionLogReqVo.setBusiness(null);
		PayCommonResult<PageInfo<TransactionLogResVo>>  transactionLogResVoList = transactionLogService.searchList(transactionLogReqVo, pageNo, pageSize);
		
		if(transactionLogResVoList.isSuccess()) {
			for(TransactionLogResVo transactionLogResVo:transactionLogResVoList.getBusinessObject().getList()) {
				if(transactionLogResVo.getCardNo() != null && !transactionLogResVo.getCardNo().equals("null"))
					transactionLogResVo.setCardNo(sensitiveInfoUtils.bankCard(transactionLogResVo.getCardNo()));
				if(transactionLogResVo.getBankMobile() != null && !transactionLogResVo.getBankMobile().equals("null"))
					transactionLogResVo.setBankMobile(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getBankMobile()));
				if(transactionLogResVo.getPayeeUsername() != null && !transactionLogResVo.getPayeeUsername().equals("null"))
					transactionLogResVo.setPayeeUsername(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getPayeeUsername()));
				if(transactionLogResVo.getPayerUsername() != null && !transactionLogResVo.getPayerUsername().equals("null"))
					transactionLogResVo.setPayerUsername(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getPayerUsername()));
			}
			return Result.success(transactionLogResVoList.getBusinessObject());
		}
			
		else
			return Result.error(transactionLogResVoList.getResultCodeMsg());
					
	}
	
	@RequestMapping(value="/transactionlog/getLog2",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<TransactionLogResVo> getLog2(TransactionLogSearchReqVo transactionLogReqVo, int pageNo, int pageSize) {
		
		PayCommonResult<PageInfo<TransactionLogResVo>>  transactionLogResVoList = transactionLogService.searchList(transactionLogReqVo, pageNo, pageSize);
		if(transactionLogResVoList.isSuccess()) {
			for(TransactionLogResVo transactionLogResVo:transactionLogResVoList.getBusinessObject().getList()) {
				if(transactionLogResVo.getCardNo() != null && !transactionLogResVo.getCardNo().equals("null"))
					transactionLogResVo.setCardNo(sensitiveInfoUtils.bankCard(transactionLogResVo.getCardNo()));
				if(transactionLogResVo.getPayeeUsername() != null && !transactionLogResVo.getPayeeUsername().equals("null"))
					transactionLogResVo.setPayeeUsername(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getPayeeUsername()));
			}
		}
	
			return  transactionLogResVoList.getBusinessObject();
					
	}
	
	@RequestMapping(value="/transactionlog/getLogById",method=RequestMethod.POST)
	@ResponseBody
	public Result getLogById(TransactionLogReqVo transactionLogReqVo ) {
		PayCommonResult<TransactionLogResVo>  transactionLogResVo = transactionLogService.get(transactionLogReqVo);
		if(transactionLogResVo.isSuccess()) {
			if(transactionLogResVo.getBusinessObject().getCardNo() != null && !transactionLogResVo.getBusinessObject().getCardNo().equals("null"))
				transactionLogResVo.getBusinessObject().setCardNo(sensitiveInfoUtils.bankCard(transactionLogResVo.getBusinessObject().getCardNo()));
			if(transactionLogResVo.getBusinessObject().getBankMobile() != null && !transactionLogResVo.getBusinessObject().getBankMobile().equals("null"))
				transactionLogResVo.getBusinessObject().setBankMobile(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getBusinessObject().getBankMobile()));
			if(transactionLogResVo.getBusinessObject().getPayeeUsername() != null && !transactionLogResVo.getBusinessObject().getPayeeUsername().equals("null"))
				transactionLogResVo.getBusinessObject().setPayeeUsername(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getBusinessObject().getPayeeUsername()));
			if(transactionLogResVo.getBusinessObject().getPayerUsername() != null && !transactionLogResVo.getBusinessObject().getPayerUsername().equals("null"))
				transactionLogResVo.getBusinessObject().setPayerUsername(sensitiveInfoUtils.mobilePhone(transactionLogResVo.getBusinessObject().getPayerUsername()));
			return Result.success(transactionLogResVo.getBusinessObject());
		}
			
		else
			return Result.error(transactionLogResVo.getResultCodeMsg());
					
	}
	
	@RequestMapping(value="/transactionlog/revise", method = RequestMethod.POST)
	@ResponseBody
	
	public PayCommonResult<PayResVo> revise(String requestId){
		PayCommonResult<PayResVo> result = iPayService.reviseByRequestId(requestId);
		return result;
	}
}
