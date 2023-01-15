/**
 * Project:boss
 * File:GrantAwardLog.java
 * Date:2018-03-08
 * Copyright (c) 2018 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description:  Po定义
* date: 2018-03-08 09:49:50
* @author: CodeCreator
*/
public class GrantAwardLogPo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String projectId;

    /**
     * 
     */
    private String awardPoolId;

    /**
     * 
     */
    private Integer awardPoolNum;

    /**
     * 
     */
    private String createdBy;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getProjectId()
    {
        return projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getAwardPoolId()
    {
        return awardPoolId;
    }

    public void setAwardPoolId(String awardPoolId)
    {
        this.awardPoolId = awardPoolId;
    }

    public Integer getAwardPoolNum()
    {
        return awardPoolNum;
    }

    public void setAwardPoolNum(Integer awardPoolNum)
    {
        this.awardPoolNum = awardPoolNum;
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