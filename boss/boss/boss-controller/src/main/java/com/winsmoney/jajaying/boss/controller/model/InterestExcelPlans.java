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
public class InterestExcelPlans implements Serializable
{
    /**
	* 用户编号
	*/
	private String userCode ;
	
    /**
	* 手机号
	*/
	private String phone;
	
	/**
	 * 还款日
	 * @return
	 */
	private Date modifyTime;
	
    /**
	* 用户姓名
	*/
	private String userName;
	
   /**
	* 阶段、期数
	*/
	private Integer phase ;
	
	/**
	* 实付金额
	*/
	private Money realPay;
	
	/**
	 * 红包金额
	 */
	private Money redMoneyAmount;
	
	/**
	* 投资金额
	*/
	private Money investAmount;
	
	/**
	* 平台贴息
	*/
	private Money discount;
	
	/**
	* 计息区间收本金
	*/
	private Money principalAmount;
	
   /**
	* 利息
	*/
	private Money interest;
	
   /**
	* 合计
	*/
	private Money total;
	
	/**
	* 状态
	*/
	private String status;
	
	
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Money getRealPay() {
		return realPay;
	}

	public void setRealPay(Money realPay) {
		this.realPay = realPay;
	}

	public Money getRedMoneyAmount() {
		return redMoneyAmount;
	}

	public void setRedMoneyAmount(Money redMoneyAmount) {
		this.redMoneyAmount = redMoneyAmount;
	}

	public Money getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Money investAmount) {
		this.investAmount = investAmount;
	}

	public Money getDiscount() {
		return discount;
	}

	public void setDiscount(Money discount) {
		this.discount = discount;
	}

	public Money getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(Money principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Money getInterest() {
		return interest;
	}

	public void setInterest(Money interest) {
		this.interest = interest;
	}

	public Money getTotal() {
		return total;
	}

	public void setTotal(Money total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}



