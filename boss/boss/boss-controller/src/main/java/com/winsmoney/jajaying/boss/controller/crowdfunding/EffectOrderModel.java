/**
* Project Name:mgt
* File Name:OrderRecordModel.java
* Package Name:com.winsmoney.jajaying.mgt.model
* Date:2016年6月30日上午11:59:48
* Copyright (c) 2016, wangbinlei@jajaying.com All Rights Reserved.
*/

package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.Date;

import com.winsmoney.platform.framework.core.util.Money;

/**
* Description: TODO ADD Description. <br/>
* Date: 2016年6月30日 上午11:59:48 <br/>
* @author wangbinlei
* @since JDK 1.7
*/
public class EffectOrderModel {

    /**
     * 订购时间
     */
    private Date orderingTime;

    /**
     * 全额支付时间
     */
    private Date payTime;

    /**
     * 电话
     */
    private String phone;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * 省份证
     */
    private String idcard;
    
    /**
     * 订单金额
     */
    private Money amount;
    
    /**
     * 权益名称
     */
    private String subProductName;

	public Date getOrderingTime() {
		return orderingTime;
	}

	public void setOrderingTime(Date orderingTime) {
		this.orderingTime = orderingTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
}

    