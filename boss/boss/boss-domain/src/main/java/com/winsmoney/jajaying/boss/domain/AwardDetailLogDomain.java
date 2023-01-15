/**
* Project:boss
* File:AwardDetailLog.java
* Date:2017-11-24
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.AwardDetailLogManager;
import com.winsmoney.jajaying.boss.domain.model.AwardDetailLog;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: awardDetailLog
* Description: 奖品发放记录 实现
* date: 2017-11-24 04:59:41
* @author: CodeCreator
*/
@Service
public class AwardDetailLogDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardDetailLogDomain.class);

    @Autowired
    private AwardDetailLogManager awardDetailLogManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public AwardDetailLog getById(String id)
    {
        AwardDetailLog r=awardDetailLogManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public AwardDetailLog get(AwardDetailLog condition)
    {
        AwardDetailLog result= awardDetailLogManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(AwardDetailLog condition)
    {
        int result = awardDetailLogManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<AwardDetailLog> list(AwardDetailLog condition)
    {
        List<AwardDetailLog> result = awardDetailLogManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<AwardDetailLog> list(AwardDetailLog condition, int pageNo, int pageSize)
    {
        PageInfo<AwardDetailLog> result = awardDetailLogManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(AwardDetailLog value)
    {
        int result = awardDetailLogManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(AwardDetailLog value)
    {
        int result = awardDetailLogManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = awardDetailLogManager.delete(id);
        return result;
    }

}
