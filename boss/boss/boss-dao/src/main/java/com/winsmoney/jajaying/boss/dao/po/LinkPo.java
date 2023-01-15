/**
 * Project:boss
 * File:Link.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description: 链接表 Po定义
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
public class LinkPo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String createdBy;

    /**
     * 
     */
    private String linkName;

    /**
     * 
     */
    private String channelName;

    /**
     * 
     */
    private String channelCode;

    /**
     * 
     */
    private String activityName;

    /**
     * 
     */
    private String aActivityCode;

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
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