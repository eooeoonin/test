package com.winsmoney.jajaying.boss.controller.trade;

//import com.winsmoney.jajaying.account.service.response.AccountLogResVo;

public class AccountLogResForm{
	/*
	 * 用户名
	 * */
	private String registerMobile;

	/*
	 * 子交易编码中文
	 * */
	private String subTransCodeCn;
	
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
		
}
