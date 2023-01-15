/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* ClassName: awardDetail
* Description: 奖品详情(抽奖号码)
* date: 2017-11-23 03:42:47
* @author: CodeCreator
*/
public class AwardDetail extends BaseModel
{

	private static final long serialVersionUID = -1L;

	/**
	* 
	*/
	private String createdBy;
	/**
	* 编码
	*/
	private String code;
	/**
	* 用户编码
	*/
	private String userId;
	/**
	* 用户编码
	*/
	private String activityId;
	/**
	* 中奖状态
	*/
	private String awardStatus;

    	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

    	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

 	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

 	public String getActivityId()
	{
		return activityId;
	}

	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}

 	public String getAwardStatus()
	{
		return awardStatus;
	}

	public void setAwardStatus(String awardStatus)
	{
		this.awardStatus = awardStatus;
	}



}