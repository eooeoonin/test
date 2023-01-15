/**
 * Project:boss
 * File:AwardRule.java
 * Date:2017-11-24
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
* Description: 活动表扩展表设置(集采类) 返回结果
* date: 2017-11-24 12:19:56
* @author: CodeCreator
*/
public class AwardRuleResVo implements Serializable
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
	* 得奖名称
	*/
	private String name ;


    /**
	* 中奖号（命中值）
	*/
	private String standardAmount ;


    /**
	* 操作状态
	*/
	private String handleStatus ;


    /**
	* 活动编号
	*/
	private String activityId ;

	/**
	 * 阳光普照
	 */
	private Boolean sunShines;

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


    public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


    public String getStandardAmount()
	{
		return standardAmount;
	}

	public void setStandardAmount(String standardAmount)
	{
		this.standardAmount = standardAmount;
	}


    public String getHandleStatus()
	{
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus)
	{
		this.handleStatus = handleStatus;
	}


    public String getActivityId()
	{
		return activityId;
	}

	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}

	public Boolean getSunShines() {
		return sunShines;
	}

	public void setSunShines(Boolean sunShines) {
		this.sunShines = sunShines;
	}
}



