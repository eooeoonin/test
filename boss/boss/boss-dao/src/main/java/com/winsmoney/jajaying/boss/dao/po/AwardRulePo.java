/**
 * Project:boss
 * File:AwardRule.java
 * Date:2017-11-24
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description: 活动表扩展表设置(集采类) Po定义
* date: 2017-11-24 06:03:33
* @author: CodeCreator
*/
public class AwardRulePo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String createdBy;

    /**
     * 得奖名称
     */
    private String name;

    /**
     * 中奖号（命中值）
     */
    private String standardAmount;

    /**
     * 操作状态
     */
    private String handleStatus;

    /**
     * 活动编号
     */
    private String activityId;

    /**
     * 是否阳光普照
     */
    private Boolean sunShines;

    /**
     * 
     */
    private String awardValue;

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

    public Boolean getSunShines()
    {
        return sunShines;
    }

    public void setSunShines(Boolean sunShines)
    {
        this.sunShines = sunShines;
    }

    public String getAwardValue()
    {
        return awardValue;
    }

    public void setAwardValue(String awardValue)
    {
        this.awardValue = awardValue;
    }




}