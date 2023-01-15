/**
* Project Name:mgt
* File Name:SettleModel.java
* Package Name:com.winsmoney.jajaying.mgt.model
* Date:2016年4月19日下午4:52:38
* Copyright (c) 2016, wangbinlei@jajaying.com All Rights Reserved.
*/

package com.winsmoney.jajaying.boss.controller.model;

import com.winsmoney.platform.framework.core.util.Money;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
* Description: TODO ADD Description. <br/>
* Date: 2016年4月19日 下午4:52:38 <br/>
* @author wangbinlei
* @since JDK 1.7
*/
public class SettleModel {

    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 权益名称
     */
    private String subProductName;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 省份证
     */
    private String idcard;
    
    /**
     * 订购时间
     */
    private Date orderingTime;

    /**
     * 订单金额
     */
    private Money amount;
    
    /**
     * 是否买房 或者 使用倍筹
     */
    private String isBuyHouse;
    
    /**
     *购房利率
    */
    private String buyRate;
    
    private Date modifyTime;
    
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getOrderingTime() {
		return orderingTime;
	}

	public void setOrderingTime(Date orderingTime) {
		this.orderingTime = orderingTime;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String getIsBuyHouse() {
		return isBuyHouse;
	}

	public void setIsBuyHouse(String isBuyHouse) {
		if("1".equals(isBuyHouse)){
			this.isBuyHouse = "是";
		}else{
			this.isBuyHouse = "否";
		}
	}

	public String getBuyRate() {
		return StringUtils.isEmpty(buyRate)?"":buyRate + "%";
	}

	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}

    