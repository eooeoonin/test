package com.winsmoney.jajaying.boss.controller.trade;

import com.winsmoney.jajaying.redmoney.service.request.RedmoneyTradeExtendReqVo;

public class RedMoneyAccountLogReqForm extends RedmoneyTradeExtendReqVo {
	/*
	 * 用户名
	 * */
	private String registerMobile;
	//红包类型，查询红包流水时页面输入
	private String redMoneySource;

	public String getRedMoneySource() {
		return redMoneySource;
	}

	public void setRedMoneySource(String redMoneySource) {
		this.redMoneySource = redMoneySource;
	}

	public String getRegisterMobile() {
		return registerMobile;
	}

	public void setRegisterMobile(String registerMobile) {
		this.registerMobile = registerMobile;
	}

}
