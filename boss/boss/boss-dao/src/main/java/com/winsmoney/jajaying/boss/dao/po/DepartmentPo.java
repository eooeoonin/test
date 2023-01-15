/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.platform.framework.core.model.BasePo;
import java.io.Serializable;
import java.util.Date;


/**
* Description:  Po定义
* date: 2016-08-03 06:03:16
* @author: CodeCreator
*/
public class DepartmentPo extends BasePo implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 部门类型
     */
    private String departmentType;

    /**
     * 上级部门id
     */
    private String parentDepartmentId;

    /**
     * 
     */
    private String isInherit;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门编码
     */
    private String departmentCode;

    public String getDepartmentType()
    {
        return departmentType;
    }

    public void setDepartmentType(String departmentType)
    {
        this.departmentType = departmentType;
    }

    public String getParentDepartmentId()
    {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(String parentDepartmentId)
    {
        this.parentDepartmentId = parentDepartmentId;
    }

    public String getIsInherit()
    {
        return isInherit;
    }

    public void setIsInherit(String isInherit)
    {
        this.isInherit = isInherit;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode()
    {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode)
    {
        this.departmentCode = departmentCode;
    }




}