/**
 * Project:boss
 * File:Channel.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description: 渠道表 Po定义
* date: 2017-08-31 05:12:49
* @author: CodeCreator
*/
public class ChannelPo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String createdBy;

    /**
     * 渠道号
     */
    private String code;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 渠道类型
     */
    private String channelTypeId;

    /**
     * 
     */
    private String channelType;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getChannelTypeId()
    {
        return channelTypeId;
    }

    public void setChannelTypeId(String channelTypeId)
    {
        this.channelTypeId = channelTypeId;
    }

    public String getChannelType()
    {
        return channelType;
    }

    public void setChannelType(String channelType)
    {
        this.channelType = channelType;
    }




}