/**
 * Project:boss
 * File:AwardListLog.java
 * Date:2018-03-08
 * Copyright (c) 2018 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: awardListLog
 * Description: 奖励记录 Service请求参数
 * date: 2018-03-08 03:06:17
 * @author: CodeCreator
 */
public class AwardListLogReqVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id ;

    /**
     * 奖励项目ID
     */
    private String awardProjectId ;

    /**
     * 奖励项目
     */
    private String awardProject ;

    /**
     * 发放类型 GROUP:群发,SINGLE:单发
     */
    private String grantType ;

    /**
     * 获奖人数
     */
    private Integer awardAmount ;

    /**
     * 状态
     */
    private String status ;

    /**
     * 创建人
     */
    private String createdBy ;

    /**
     * 最终修改人
     */
    private String editedBy ;

    /**
     * 创建时间
     */
    private Date createTime ;

    /**
     * 最终修改时间
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

    public String getAwardProjectId()
    {
        return awardProjectId;
    }

    public void setAwardProjectId(String awardProjectId)
    {
        this.awardProjectId = awardProjectId;
    }

    public String getAwardProject()
    {
        return awardProject;
    }

    public void setAwardProject(String awardProject)
    {
        this.awardProject = awardProject;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public void setGrantType(String grantType)
    {
        this.grantType = grantType;
    }

    public Integer getAwardAmount()
    {
        return awardAmount;
    }

    public void setAwardAmount(Integer awardAmount)
    {
        this.awardAmount = awardAmount;
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
