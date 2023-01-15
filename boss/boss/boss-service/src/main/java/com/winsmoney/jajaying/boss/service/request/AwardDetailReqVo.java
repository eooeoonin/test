/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: awardDetail
 * Description: 奖品详情(抽奖号码) Service请求参数
 * date: 2017-11-23 03:42:47
 * @author: CodeCreator
 */
public class AwardDetailReqVo implements Serializable
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
     * 编码
     */
    private String code ;

    /**
     * 用户编码
     */
    private String userId ;

    /**
     * 用户编码
     */
    private String activityId ;

    /**
     * 中奖状态
     */
    private String awardStatus ;


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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    public String getAwardStatus()
    {
        return awardStatus;
    }

    public void setAwardStatus(String awardStatus)
    {
        this.awardStatus = awardStatus;
    }


}
