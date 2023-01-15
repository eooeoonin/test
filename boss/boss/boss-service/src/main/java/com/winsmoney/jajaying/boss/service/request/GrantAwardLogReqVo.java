/**
 * Project:boss
 * File:GrantAwardLog.java
 * Date:2018-03-08
 * Copyright (c) 2018 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: grantAwardLog
 * Description:  Service请求参数
 * date: 2018-03-08 09:49:50
 * @author: CodeCreator
 */
public class GrantAwardLogReqVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String id ;

    /**
     * 
     */
    private String userId ;

    /**
     * 
     */
    private String projectId ;

    /**
     * 
     */
    private String awardPoolId ;

    /**
     * 
     */
    private Integer awardPoolNum ;

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
