/**
 * Project:boss
 * File:AwardDetailLog.java
 * Date:2017-11-24
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description: 奖品发放记录 Po定义
* date: 2017-11-24 04:59:41
* @author: CodeCreator
*/
public class AwardDetailLogPo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String createdBy;

    /**
     * 用户编码
     */
    private String userId;

    /**
     * 用户编码
     */
    private String activityId;

    /**
     * 活动奖励项目编号
     */
    private String projectId;

    /**
     * 奖励中奖码
     */
    private String awardCode;

    /**
     * 活动中奖码
     */
    private String activityCode;

    /**
     * 中奖状态
     */
    private String awardStatus;

    /**
     * 中奖级别
     */
    private String awardLevel;

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
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

    public String getProjectId()
    {
        return projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getAwardCode()
    {
        return awardCode;
    }

    public void setAwardCode(String awardCode)
    {
        this.awardCode = awardCode;
    }

    public String getActivityCode()
    {
        return activityCode;
    }

    public void setActivityCode(String activityCode)
    {
        this.activityCode = activityCode;
    }

    public String getAwardStatus()
    {
        return awardStatus;
    }

    public void setAwardStatus(String awardStatus)
    {
        this.awardStatus = awardStatus;
    }

    public String getAwardLevel()
    {
        return awardLevel;
    }

    public void setAwardLevel(String awardLevel)
    {
        this.awardLevel = awardLevel;
    }




}