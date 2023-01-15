/**
 * Project:boss
 * File:Channel.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: channel
 * Description: 渠道表 Service请求参数
 * date: 2017-08-31 05:12:49
 * @author: CodeCreator
 */
public class ChannelReqVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 渠道id
     */
    private String id ;

    /**
     * 
     */
    private String status ;

    /**
     * 
     */
    private String editedBy ;

    /**
     * 
     */
    private String createdBy ;

    /**
     * 
     */
    private Date createTime ;

    /**
     * 
     */
    private Date modifyTime ;

    /**
     * 渠道号
     */
    private String code ;

    /**
     * 渠道名称
     */
    private String name ;

    /**
     * 渠道类型
     */
    private String channelTypeId ;

    /**
     * 
     */
    private String channelType ;


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

    public String getEditedBy()
    {
        return editedBy;
    }

    public void setEditedBy(String editedBy)
    {
        this.editedBy = editedBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
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
