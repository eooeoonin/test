package com.winsmoney.jajaying.boss.controller.trade;

import com.winsmoney.jajaying.redmoney.service.response.RedmoneyTradeResVo;
import com.winsmoney.platform.framework.core.util.Money;

public class RedMoneyAccountLogResForm extends RedmoneyTradeResVo {
	/*
	 * 用户名
	 * */
	private String registerMobile;

	/*
	 * 子交易编码中文
	 * */
	private String subTransCodeCn;
	
	/*
	 * 余额
	 * */
	private Money remainBalance;
	
	public String getRegisterMobile() {
		return registerMobile;
	}

	public void setRegisterMobile(String registerMobile) {
		this.registerMobile = registerMobile;
	}
	
	public String getSubTransCodeCn() {
		return subTransCodeCn;
	}

	public void setSubTransCodeCn(String subTransCodeCn) {
		this.subTransCodeCn = subTransCodeCn;
	}

	public Money getRemainBalance() {
		return remainBalance;
	}

	public void setRemainBalance(Money remainBalance) {
		this.remainBalance = remainBalance;
	}
		
}
