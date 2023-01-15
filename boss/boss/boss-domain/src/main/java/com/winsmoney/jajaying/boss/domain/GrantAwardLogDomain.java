/**
* Project:boss
* File:GrantAwardLog.java
* Date:2018-03-08
* Copyright (c) 2018 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.GrantAwardLogManager;
import com.winsmoney.jajaying.boss.domain.model.GrantAwardLog;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: grantAwardLog
* Description:  实现
* date: 2018-03-08 09:49:50
* @author: CodeCreator
*/
@Service
public class GrantAwardLogDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(GrantAwardLogDomain.class);

    @Autowired
    private GrantAwardLogManager grantAwardLogManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public GrantAwardLog getById(String id)
    {
        GrantAwardLog r=grantAwardLogManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public GrantAwardLog get(GrantAwardLog condition)
    {
        GrantAwardLog result= grantAwardLogManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(GrantAwardLog condition)
    {
        int result = grantAwardLogManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<GrantAwardLog> list(GrantAwardLog condition)
    {
        List<GrantAwardLog> result = grantAwardLogManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<GrantAwardLog> list(GrantAwardLog condition, int pageNo, int pageSize)
    {
        PageInfo<GrantAwardLog> result = grantAwardLogManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(GrantAwardLog value)
    {
        int result = grantAwardLogManager.insert(value);
        return result;
    }

    /**
    * 批量插入
    */
    public List<GrantAwardLog> insertList(List<GrantAwardLog> value)
    {
         List<GrantAwardLog> result = grantAwardLogManager.insertList(value);
         return result;
    }

    /**
     * 更新
     */
    public int update(GrantAwardLog value)
    {
        int result = grantAwardLogManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = grantAwardLogManager.delete(id);
        return result;
    }

}
