/**
 * Project:boss
 * File:RedmoneyInfo.java
 * Date:2016-10-09
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.util.Date;

/**
* ClassName: redmoneyInfo
* Description: 
* date: 2016-10-09 02:14:08
* @author: CodeCreator
*/
public class RedmoneyInfo extends BaseModel
{

	private static final long serialVersionUID = -1L;
	/**
	* 手机号
	*/
	private String phone;
	/**
	* 用户id
	*/
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
  	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

 	public Money getAccountMoney()
	{
		return accountMoney;
	}

	public void setAccountMoney(Money money)
	{
		this.accountMoney = money;
	}

 	public Integer getTimes()
	{
		return times;
	}

	public void setTimes(Integer times)
	{
		this.times = times;
	}

 	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

     

}