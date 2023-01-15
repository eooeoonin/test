/**
 * Project:boss
 * File:ChannelType.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
* Description: 渠道类型 返回结果
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
public class ChannelTypeResVo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
	* 
	*/
	private String id ;


    /**
	* 
	*/
	private String name ;


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



    public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


    public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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



}



