/**
 * Project:boss
 * File:Template.java
 * Date:2017-09-04
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* ClassName: template
* Description: 模板表
* date: 2017-09-04 04:11:05
* @author: CodeCreator
*/
public class Template extends BaseModel
{

	private static final long serialVersionUID = -1L;

	/**
	* 
	*/
	private String createdBy;
	/**
	* 
	*/
	private String name;
	/**
	* 
	*/
	private String type;
	/**
	* 
	*/
	private Integer imgNum;
	/**
	* 模版缩略图
	*/
	private String fileName;

    	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

    	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

 	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

 	public Integer getImgNum()
	{
		return imgNum;
	}

	public void setImgNum(Integer imgNum)
	{
		this.imgNum = imgNum;
	}

 	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}



}