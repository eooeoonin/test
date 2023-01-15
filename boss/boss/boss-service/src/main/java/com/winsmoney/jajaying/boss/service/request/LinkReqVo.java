/**
 * Project:boss
 * File:Link.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: link
 * Description: 链接表 Service请求参数
 * date: 2017-08-31 05:16:26
 * @author: CodeCreator
 */
public class LinkReqVo implements Serializable
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
     * 
     */
    private String linkName ;

    /**
     * 
     */
    private String channelName ;

    /**
     * 
     */
    private String channelCode ;

    /**
     * 
     */
    private String activityName ;

    /**
     * 
     */
    private String aActivityCode ;


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

    public String getLinkName()
    {
        return linkName;
    }

    public void setLinkName(String linkName)
    {
        this.linkName = linkName;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getChannelCode()
    {
        return channelCode;
    }

    public void setChannelCode(String channelCode)
    {
        this.channelCode = channelCode;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getAActivityCode()
    {
        return aActivityCode;
    }

    public void setAActivityCode(String aActivityCode)
    {
        this.aActivityCode = aActivityCode;
    }


}
