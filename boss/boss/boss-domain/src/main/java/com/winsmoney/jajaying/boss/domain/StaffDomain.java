/**
* Project:boss
* File:Staff.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.StaffManager;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.MD5;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: staff
* Description:  实现
* date: 2016-08-03 06:03:18
* @author: CodeCreator
*/
@Service
public class StaffDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(StaffDomain.class);

    @Autowired
    private StaffManager staffManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Staff getById(String id)
    {
        Staff r=staffManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Staff getStaff(Staff condition)
    {
        Staff result= staffManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int countStaff(Staff condition)
    {
        int result = staffManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Staff> listStaff(Staff condition)
    {
        List<Staff> result = staffManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Staff> listStaff(Staff condition, int pageNo, int pageSize)
    {
        PageInfo<Staff> result = staffManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insertStaff(Staff value)
    {	
    	value.setPassword(MD5.encodeByMd5AndSalt(value.getPassword()));
        int result = staffManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int updateStaff(Staff value)
    {
        int result = staffManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int deleteStaff(String id)
    {
        int result = staffManager.delete(id);
        return result;
    }

	public Staff getStaffByStaffName(String staffName) {
		return staffManager.getStaffByStaffName(staffName);
	}

	public Staff getStaffByIdCard(String idCard) {
		return staffManager.getStaffByIdCard(idCard);
	}

}
