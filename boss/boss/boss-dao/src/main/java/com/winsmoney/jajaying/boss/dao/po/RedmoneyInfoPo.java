/**
 * Project:boss
 * File:RedmoneyInfo.java
 * Date:2016-10-09
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: Po定义 date: 2016-10-09 02:14:08
 * 
 * @author: CodeCreator
 */
public class RedmoneyInfoPo extends BasePo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	* 手机号
	*/
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 红包金额
	 */
	private Money accountMoney;

	/**
	 * 有效天数
	 */
	private Integer times;

	/**
	 * 类型(0发送成功,1发送失败)
	 */
	private String type;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Money getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(Money accountMoney) {
		this.accountMoney = accountMoney;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}