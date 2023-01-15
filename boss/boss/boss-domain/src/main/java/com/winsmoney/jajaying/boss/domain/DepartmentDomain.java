/**
* Project:boss
* File:Department.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.DepartmentManager;
import com.winsmoney.jajaying.boss.domain.model.Department;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: department
* Description:  实现
* date: 2016-08-03 06:03:16
* @author: CodeCreator
*/
@Service
public class DepartmentDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(DepartmentDomain.class);

    @Autowired
    private DepartmentManager departmentManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Department getById(String id)
    {
        Department r=departmentManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Department getDepartment(Department condition)
    {
        Department result= departmentManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int countDepartment(Department condition)
    {
        int result = departmentManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Department> listDepartment(Department condition)
    {
        List<Department> result = departmentManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Department> listDepartment(Department condition, int pageNo, int pageSize)
    {
        PageInfo<Department> result = departmentManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insertDepartment(Department value)
    {
        int result = departmentManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int updateDepartment(Department value)
    {
        int result = departmentManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int deleteDepartment(String id)
    {
        int result = departmentManager.delete(id);
        return result;
    }

	public Department getDepartmentByName(String departmentName) {
		return departmentManager.getDepartmentByName(departmentName);
	}

	public Department getDepartmentByCode(String departmentCode) {
		return departmentManager.getDepartmentByCode(departmentCode);
	}

}
