/**
 * Project:boss
 * File:ChannelType.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description: 渠道类型 Po定义
* date: 2017-08-31 05:16:25
* @author: CodeCreator
*/
public class ChannelTypePo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String createdBy;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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