package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowerResVo;
import com.winsmoney.jajaying.trade.service.response.GuaranteeResVo;

public class BorrowResBo extends BorrowResVo {
	private static final long serialVersionUID = 1L;

	//借款人信息
	private BorrowerResVo borrowerResVo;
	//担保相关信息
	private GuaranteeResVo guaranteeResVo;
	// 投资周期
	private String investDate;
	// 下次还款金额
	private String nextPayAmount;
	// 下次还款时间
	private String nextPayDate;
	public BorrowerResVo getBorrowerResVo() {
		return borrowerResVo;
	}
	public void setBorrowerResVo(BorrowerResVo borrowerResVo) {
		this.borrowerResVo = borrowerResVo;
	}
	public GuaranteeResVo getGuaranteeResVo() {
		return guaranteeResVo;
	}
	public void setGuaranteeResVo(GuaranteeResVo guaranteeResVo) {
		this.guaranteeResVo = guaranteeResVo;
	}
	public String getInvestDate() {
		return investDate;
	}
	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}
	public String getNextPayAmount() {
		return nextPayAmount;
	}
	public void setNextPayAmount(String nextPayAmount) {
		this.nextPayAmount = nextPayAmount;
	}
	public String getNextPayDate() {
		return nextPayDate;
	}
	public void setNextPayDate(String nextPayDate) {
		this.nextPayDate = nextPayDate;
	}

}
