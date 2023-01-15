/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* ClassName: activityRequest
* Description: 活动请求
* date: 2017-11-23 03:15:54
* @author: CodeCreator
*/
public class ActivityRequest extends BaseModel
{

	private static final long serialVersionUID = -1L;

	/**
	* 
	*/
	private String createdBy;
	/**
	* 交易时间
	*/
	private Date tradeTime;
	/**
	* 活动值
	*/
	private BigDecimal transValue;
	/**
	* 请求单号
	*/
	private String requestId;
	/**
	* 项目号
	*/
	private String projectId;
	/**
	* 活动号
	*/
	private String activityId;
	/**
	* 用户编码
	*/
	private String userId;
	/**
	* 处理状态
	*/
	private String handleStatus;

    	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

    	public Date getTradeTime()
	{
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime)
	{
		this.tradeTime = tradeTime;
	}

 	public BigDecimal getTransValue()
	{
		return transValue;
	}

	public void setTransValue(BigDecimal transValue)
	{
		this.transValue = transValue;
	}

 	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

 	public String getProjectId()
	{
		return projectId;
	}

	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

 	public String getActivityId()
	{
		return activityId;
	}

	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}

 	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

 	public String getHandleStatus()
	{
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus)
	{
		this.handleStatus = handleStatus;
	}



}