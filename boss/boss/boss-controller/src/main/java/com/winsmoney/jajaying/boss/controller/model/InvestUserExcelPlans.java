package com.winsmoney.jajaying.boss.controller.model;

import java.util.Date;

import com.winsmoney.platform.framework.core.util.Money;

public class InvestUserExcelPlans {
	/**
	 * 用户手机号
	 */
	private String registerMobile;
	/**
	 * 姓名
	 */
    private String realName;
    /**
     * 属性
     */
    private String userProperties;
    /**
     * 投资时间
     */
    private Date investTime;
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
	
	
	

	public String getRegisterMobile() {
		return registerMobile;
	}

	public void setRegisterMobile(String registerMobile) {
		this.registerMobile = registerMobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(String userProperties) {
		this.userProperties = userProperties;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
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
	
	
	
	
    

}
