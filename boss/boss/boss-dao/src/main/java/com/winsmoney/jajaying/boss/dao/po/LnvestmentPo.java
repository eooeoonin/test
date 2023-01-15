package com.winsmoney.jajaying.boss.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.winsmoney.jajaying.trade.service.enums.LoanStatus;
import com.winsmoney.platform.framework.core.util.Money;

public class LnvestmentPo {
	
	
	private String loanCode;
	
	 /**
	  * 投资金额
	  */
	 private Money investAmount ;
	/**
	 * 投资时间
	 */
	 private Date investTime ;
	/**
     * 红包使用金额
     */
     private Money redMoneyAmount ;
     /**
      * 实际支付
      */
     private Money actualPay;
     /**
      * 标题名称
      */
     private String title ;
     /**
      * 标的利率
      */
     private BigDecimal yearRate ;
     /**
      * 标的状态
      */
     private String status ;
     /**
      * 项目周期
      */
     private Long projectCycle;

     /**
      * 投资状态
      * @return
      */
     private String investorStatus;
     
     
	
	public String getInvestorStatus() {
		return investorStatus;
	}
	public void setInvestorStatus(String investorStatus) {
		this.investorStatus = investorStatus;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public Long getProjectCycle() {
		return projectCycle;
	}
	public long setProjectCycle(Long projectCycle) {
		return this.projectCycle = projectCycle;
	}
	
	public Money getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(Money investAmount) {
		this.investAmount = investAmount;
	}
	public Date getInvestTime() {
		return investTime;
	}
	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
	public Money getRedMoneyAmount() {
		return redMoneyAmount;
	}
	public void setRedMoneyAmount(Money redMoneyAmount) {
		this.redMoneyAmount = redMoneyAmount;
	}

	public Money getActualPay() {
		return actualPay;
	}
	public void setActualPay(Money actualPay) {
		this.actualPay = actualPay;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getYearRate() {
		return yearRate;
	}
	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
     
   
	 
}
