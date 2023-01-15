/**
 * Project:boss
 * File:AwardListLog.java
 * Date:2018-03-08
 * Copyright (c) 2018 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* ClassName: awardListLog
* Description: 奖励记录
* date: 2018-03-08 03:06:17
* @author: CodeCreator
*/
public class AwardListLog extends BaseModel
{

	private static final long serialVersionUID = -1L;

	/**
	* 奖励项目ID
	*/
	private String awardProjectId;
	/**
	* 奖励项目
	*/
	private String awardProject;
	/**
	* 发放类型 GROUP:群发,SINGLE:单发
	*/
	private String grantType;
	/**
	* 获奖人数
	*/
	private Integer awardAmount;
	/**
	* 创建人
	*/
	private String createdBy;

  	public String getAwardProjectId()
	{
		return awardProjectId;
	}

	public void setAwardProjectId(String awardProjectId)
	{
		this.awardProjectId = awardProjectId;
	}

 	public String getAwardProject()
	{
		return awardProject;
	}

	public void setAwardProject(String awardProject)
	{
		this.awardProject = awardProject;
	}

 	public String getGrantType()
	{
		return grantType;
	}

	public void setGrantType(String grantType)
	{
		this.grantType = grantType;
	}

 	public Integer getAwardAmount()
	{
		return awardAmount;
	}

	public void setAwardAmount(Integer awardAmount)
	{
		this.awardAmount = awardAmount;
	}

   	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

   

}