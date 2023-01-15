package com.winsmoney.jajaying.boss.controller.offline;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.offline.service.IBankOrderService;
import com.winsmoney.jajaying.offline.service.ITransLogService;
import com.winsmoney.jajaying.offline.service.ITransService;
import com.winsmoney.jajaying.offline.service.enums.TransStatus;
import com.winsmoney.jajaying.offline.service.request.BankOrderSearchReqVo;
import com.winsmoney.jajaying.offline.service.request.OfflinePayReqVo;
import com.winsmoney.jajaying.offline.service.request.OfflineRefundRetryReqVo;
import com.winsmoney.jajaying.offline.service.request.TransLogReqVo;
import com.winsmoney.jajaying.offline.service.request.TransLogSearchReqVo;
import com.winsmoney.jajaying.offline.service.response.BankOrderResVo;
import com.winsmoney.jajaying.offline.service.response.OfflineCommonResult;
import com.winsmoney.jajaying.offline.service.response.OfflinePayResVo;
import com.winsmoney.jajaying.offline.service.response.OfflineRefundResVo;
import com.winsmoney.jajaying.offline.service.response.TransLogResVo;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

@Controller
@RequestMapping("/bankorder")
public class BankOrder{

	@Autowired
	private IBankOrderService bankOrderService;

	@Autowired
	private ITransLogService transLogService;
	
	@Autowired
	private ITransService transService;

	
	@RequestMapping("/refundrecord_list/reviseOfflin")
 	@ResponseBody
 	public  String reviseOfflineP(OfflinePayReqVo offlinePayReqVo){
		OfflineCommonResult<OfflinePayResVo> reviseOfflinePa = transService.reviseOfflinePay(offlinePayReqVo);
	return reviseOfflinePa.getResultCodeMsg();
 	}
	

	@RequestMapping("/refundrecord_list/transLogList")
 	@ResponseBody
 	public  PageInfo<TransLogResVo> selectUserById(TransLogSearchReqVo transLogSearchReqVo,int pageNo, int pageSize){
		if(StringUtils.isBlank(transLogSearchReqVo.getIndexOrderId())){
			transLogSearchReqVo.setIndexOrderId(null);
 		}
		OfflineCommonResult<PageInfo<TransLogResVo>> BankOrderList = transLogService.searchList(transLogSearchReqVo, pageNo, pageSize);
		for(TransLogResVo tr : BankOrderList.getBusinessObject().getList()) {
			if(tr.getPayAccountNo() != null && !"".equals(tr.getPayAccountNo()))
				tr.setPayAccountNo(SensitiveInfoUtils.bankCard(tr.getPayAccountNo()));
			if(tr.getBankAccountNo() != null && !"".equals(tr.getBankAccountNo()))
				tr.setBankAccountNo(SensitiveInfoUtils.bankCard(tr.getBankAccountNo()));
			if(tr.getTransStatus() != null && !"".equals(tr.getTransStatus().getMessage()))	
				tr.setEditedBy(tr.getTransStatus().getMessage().toString());
		}
	return BankOrderList.getBusinessObject();
 	}

	@RequestMapping("/refundrecord_list/getById")
 	@ResponseBody
 	public  Result getById(String id){
	OfflineCommonResult<TransLogResVo> s = 	transLogService.getById(id);
	if(s.isSuccess()) {
		return Result.success(s);
	}
		return Result.error("调用失败");
	}
	@RequestMapping("/refundrecord_list/offlineRefundRetryOrClosed")
 	@ResponseBody
 	public  Result offlineRefundRetryOrClosed(String sequenceId,String payBankProvince,String payBankCity,String remark,String status){
		if("retry".equals(status)) {
			OfflineRefundRetryReqVo orf = new OfflineRefundRetryReqVo();
			TransLogReqVo te = new TransLogReqVo();
			te.setSequenceId(sequenceId);
			te.setPayBankProvince(payBankProvince);
			te.setPayBankCity(payBankCity);
			te.setRemark(remark);
			orf.setTransLog(te);
			OfflineCommonResult<OfflineRefundResVo> result = transLogService.offlineRefundRetryOrClosed(orf);
			if(result.isSuccess()) {
				return Result.success("success");
			}else {
				return Result.error("error");
			}
		}else {
			OfflineRefundRetryReqVo orf = new OfflineRefundRetryReqVo();
			TransLogReqVo te = new TransLogReqVo();
			te.setSequenceId(sequenceId);
			te.setRemark(remark);
			te.setTransStatus(TransStatus.REFUND_CLOSED);
			orf.setTransLog(te);
			OfflineCommonResult<OfflineRefundResVo> result = transLogService.offlineRefundRetryOrClosed(orf);
			if(result.isSuccess()) {
				return Result.success("success");
			}else {
				return	Result.error("error");
			}
		}
	}
	@RequestMapping("/bankorder_list/list")
 	@ResponseBody
 	public  PageInfo<BankOrderResVo> selectUserById(BankOrderSearchReqVo bankOrderSearchReqVo,int pageNo, int pageSize){
		if(StringUtils.isBlank(bankOrderSearchReqVo.getIndexOrderId())){
			bankOrderSearchReqVo.setIndexOrderId(null);
 		}
		if(StringUtils.isBlank(bankOrderSearchReqVo.getUserId())){
			bankOrderSearchReqVo.setUserId(null);
 		}
		OfflineCommonResult<PageInfo<BankOrderResVo>> BankOrderList = bankOrderService.searchList(bankOrderSearchReqVo, pageNo, pageSize);
		for(BankOrderResVo bo : BankOrderList.getBusinessObject().getList()) {
			if(bo.getPayAccountNo() != null && !"".equals(bo.getPayAccountNo()))
				bo.setPayAccountNo(SensitiveInfoUtils.bankCard(bo.getPayAccountNo()));
		}
		return BankOrderList.getBusinessObject();
 	}



	@RequestMapping("/bankorder_list/selectBankOrderById")
	@ResponseBody
	public BankOrderResVo selectBankOrderById(String id){
		OfflineCommonResult<BankOrderResVo> byId = bankOrderService.getById(id);
			if(byId.getBusinessObject().getPayAccountNo() != null && !"".equals(byId.getBusinessObject().getPayAccountNo()))
				byId.getBusinessObject().setPayAccountNo(SensitiveInfoUtils.bankCard(byId.getBusinessObject().getPayAccountNo()));
	return byId.getBusinessObject();
	}



}
