package com.winsmoney.jajaying.boss.controller.bid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.ILoanService;
import com.winsmoney.jajaying.trade.service.request.LoanInvestorReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanReqVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorResVo;
import com.winsmoney.jajaying.trade.service.response.LoanResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;

@Controller
@RequestMapping("/automatic_bidding/bidrecord")
public class InvestmentController {

	
	@Autowired
	private ILoanInvestorService loanInvestorService;
	
	@Autowired
	private ILoanService loanService;
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<LoanInvestorResVo> list(LoanReqVo loanReqVo,LoanInvestorReqVo loanInvestorReqVo,int pageNo, int pageSize){
		TradeCommonResult<PageInfo<LoanInvestorResVo>> list = loanInvestorService.myInvestRecord(loanInvestorReqVo, pageNo, pageSize);
		if(list.getBusinessObject().getList().size() > 0){
			for (int i = 0; i < list.getBusinessObject().getList().size(); i++) {
				loanReqVo.setId(list.getBusinessObject().getList().get(i).getLoanCode());
				TradeCommonResult<LoanResVo> getLoan = loanService.getLoan(loanReqVo);
				list.getBusinessObject().getList().get(i).setLoanCode(getLoan.getBusinessObject().getTitle());
				list.getBusinessObject().getList().get(i).setInvestorStatus(getLoan.getBusinessObject().getStatus());
			}
		}
		return list.getBusinessObject();
	}
	
	
}
