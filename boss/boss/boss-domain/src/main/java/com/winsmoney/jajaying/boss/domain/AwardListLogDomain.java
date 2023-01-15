/**
* Project:boss
* File:AwardListLog.java
* Date:2018-03-08
* Copyright (c) 2018 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.AwardListLogManager;
import com.winsmoney.jajaying.boss.domain.model.AwardListLog;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: awardListLog
* Description: 奖励记录 实现
* date: 2018-03-08 03:06:17
* @author: CodeCreator
*/
@Service
public class AwardListLogDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardListLogDomain.class);

    @Autowired
    private AwardListLogManager awardListLogManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public AwardListLog getById(String id)
    {
        AwardListLog r=awardListLogManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public AwardListLog get(AwardListLog condition)
    {
        AwardListLog result= awardListLogManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(AwardListLog condition)
    {
        int result = awardListLogManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<AwardListLog> list(AwardListLog condition)
    {
        List<AwardListLog> result = awardListLogManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<AwardListLog> list(AwardListLog condition, int pageNo, int pageSize)
    {
        PageInfo<AwardListLog> result = awardListLogManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(AwardListLog value)
    {
        int result = awardListLogManager.insert(value);
        return result;
    }

    /**
    * 批量插入
    */
    public List<AwardListLog> insert(List<AwardListLog> value)
    {
         List<AwardListLog> result = awardListLogManager.insertList (value);
         return result;
    }

    /**
     * 更新
     */
    public int update(AwardListLog value)
    {
        int result = awardListLogManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = awardListLogManager.delete(id);
        return result;
    }

}
