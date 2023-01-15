/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
* Description: 活动请求 返回结果
* date: 2017-11-23 03:15:54
* @author: CodeCreator
*/
public class ActivityRequestResVo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
	* 
	*/
	private String id ;


    /**
	* 
	*/
	private String status ;


    /**
	* 
	*/
	private String createdBy ;


    /**
	* 
	*/
	private String editedBy ;


    /**
	* 
	*/
	private Date createTime ;


    /**
	* 
	*/
	private Date modifyTime ;


    /**
	* 交易时间
	*/
	private Date tradeTime ;


    /**
	* 活动值
	*/
	private Long transValue ;


    /**
	* 请求单号
	*/
	private String requestId ;


    /**
	* 项目号
	*/
	private String projectId ;


    /**
	* 活动号
	*/
	private String activityId ;


    /**
	* 用户编码
	*/
	private String userId ;


    /**
	* 处理状态
	*/
	private String handleStatus ;



    public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


    public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}


    public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}


    public String getEditedBy()
	{
		return editedBy;
	}

	public void setEditedBy(String editedBy)
	{
		this.editedBy = editedBy;
	}


    public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}


    public Date getModifyTime()
	{
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}


    public Date getTradeTime()
	{
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime)
	{
		this.tradeTime = tradeTime;
	}


    public Long getTransValue()
	{
		return transValue;
	}

	public void setTransValue(Long transValue)
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



