package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;

import com.winsmoney.platform.framework.core.util.Money;
/**
 * 批量红包pojo
 * @author Moon
 *
 */
public class RedMoneyVo implements Serializable {
	//真实姓名
	private  String realName;
	
	//红包金额
	private Money account;
	
	
	//有效期
	
	private Integer times;
	
	
	//类型(0成功 1失败)
	private Integer type;


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public Money getAccount() {
		return account;
	}


	public void setAccount(Money account) {
		this.account = account;
	}


	public Integer getTimes() {
		return times;
	}


	public void setTimes(Integer times) {
		this.times = times;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
}
