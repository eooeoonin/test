/**
 * Project:trade
 * File:InterestPlans.java
 * Date:2016-08-01
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.controller.model;

import com.winsmoney.platform.framework.core.util.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Description: interestplans 收息计划 返回结果
* date: 2016-08-01 10:15:09
* @author: CodeCreator
*/

public class InterestPlans implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
	* 主键
	*/
	private String id ;
    /**
	* 投资记录编号
	*/
	private String investorCode ;
	/**
	 * 借款编号
	 */
	private String borrowCode ;
	/**
	 * 项目编号编号
	 */
	private String loanCode ;
    /**
	* 用户编号
	*/
	private String userCode ;
    /**
	* 计息区间开始时间
	*/
	private Date interestStartDate ;
    /**
	* 计息区间结束时间
	*/
	private Date interestEndDate ;
    /**
	* 计息金额
	*/
	private Money interestAmount ;
    /**
	* 计息区间收本金
	*/
	private Money principalAmount ;
	/**
	* 平台收益
	*/
	private Money platformProfit;
    /**
	* 实际收息
	*/
	private Money actualInteresTamount ;
    /**
	* 此区间内每天计息金额
	*/
	private Money interestDayAmount ;
    /**
	* 用户逾期总收益
	*/
	private Money overdueInterest ;
    /**
	* 用户预期天收益
	*/
	private Money dayOverdueInterest ;
    /**
	* 逾期平台罚息收益
	*/
	private Money platformOverdueInterest ;
    /**
	* 提前还款用户收益
	*/
	private Money earlyRepayment ;
	/**
	* 用户提前天收益
	*/
	private Money dayEarlyInterest;
    /**
	* 提前还款平台收益
	*/
	private Money platformEarlyRepayment ;
    /**
	* 阶段、期数
	*/
	private Integer phase ;
	/**
	 * 借款还款 阶段、期数
	 */
	private Integer basePhase ;
    /**
	* 状态
	*/
	private String status ;
   /**
	* 罚息
	*/
	private Money penalty;
   /**
	* 标的名称
	*/
	private String loanName;
	/**
	* 项目利率
	*/
	private BigDecimal loanRate;
	
   /**
	* 项目利率
	*/
	private String phone;
	
   /**
	* 项目利率
	*/
	private String userName;
	
	/**
	 * 红包金额
	 */
	private Money redMoneyAmount;
	/**
	 * 总投资金额
	 */
	private Money amount;
	
	/**
	* 记录更新时间
	*/
	private Date modifyTime ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvestorCode() {
		return investorCode;
	}

	public void setInvestorCode(String investorCode) {
		this.investorCode = investorCode;
	}

	public String getBorrowCode() {
		return borrowCode;
	}

	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getInterestStartDate() {
		return interestStartDate;
	}

	public void setInterestStartDate(Date interestStartDate) {
		this.interestStartDate = interestStartDate;
	}

	public Date getInterestEndDate() {
		return interestEndDate;
	}

	public void setInterestEndDate(Date interestEndDate) {
		this.interestEndDate = interestEndDate;
	}

	public Money getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Money interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Money getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(Money principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Money getPlatformProfit() {
		return platformProfit;
	}

	public void setPlatformProfit(Money platformProfit) {
		this.platformProfit = platformProfit;
	}

	public Money getActualInteresTamount() {
		return actualInteresTamount;
	}

	public void setActualInteresTamount(Money actualInteresTamount) {
		this.actualInteresTamount = actualInteresTamount;
	}

	public Money getInterestDayAmount() {
		return interestDayAmount;
	}

	public void setInterestDayAmount(Money interestDayAmount) {
		this.interestDayAmount = interestDayAmount;
	}

	public Money getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(Money overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public Money getDayOverdueInterest() {
		return dayOverdueInterest;
	}

	public void setDayOverdueInterest(Money dayOverdueInterest) {
		this.dayOverdueInterest = dayOverdueInterest;
	}

	public Money getPlatformOverdueInterest() {
		return platformOverdueInterest;
	}

	public void setPlatformOverdueInterest(Money platformOverdueInterest) {
		this.platformOverdueInterest = platformOverdueInterest;
	}

	public Money getEarlyRepayment() {
		return earlyRepayment;
	}

	public void setEarlyRepayment(Money earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}

	public Money getDayEarlyInterest() {
		return dayEarlyInterest;
	}

	public void setDayEarlyInterest(Money dayEarlyInterest) {
		this.dayEarlyInterest = dayEarlyInterest;
	}

	public Money getPlatformEarlyRepayment() {
		return platformEarlyRepayment;
	}

	public void setPlatformEarlyRepayment(Money platformEarlyRepayment) {
		this.platformEarlyRepayment = platformEarlyRepayment;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Integer getBasePhase() {
		return basePhase;
	}

	public void setBasePhase(Integer basePhase) {
		this.basePhase = basePhase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Money getPenalty() {
		return penalty;
	}

	public void setPenalty(Money penalty) {
		this.penalty = penalty;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Money getRedMoneyAmount() {
		return redMoneyAmount;
	}

	public void setRedMoneyAmount(Money redMoneyAmount) {
		this.redMoneyAmount = redMoneyAmount;
	}
	
	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}