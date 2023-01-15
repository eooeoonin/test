/**
 * Project:boss
 * File:Template.java
 * Date:2017-09-04
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: template
 * Description: 模板表 Service请求参数
 * date: 2017-09-04 04:11:05
 * @author: CodeCreator
 */
public class TemplateReqVo implements Serializable
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
    private String name ;

    /**
     * 
     */
    private String type ;

    /**
     * 
     */
    private Integer imgNum ;

    /**
     * 模版缩略图
     */
    private String fileName ;


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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getImgNum()
    {
        return imgNum;
    }

    public void setImgNum(Integer imgNum)
    {
        this.imgNum = imgNum;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


}
