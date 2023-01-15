/**
* Project:boss
* File:AwardRule.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.AwardRuleManager;
import com.winsmoney.jajaying.boss.domain.model.AwardRule;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: awardRule
* Description: 活动表扩展表设置(集采类) 实现
* date: 2017-11-23 10:02:27
* @author: CodeCreator
*/
@Service
public class AwardRuleDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardRuleDomain.class);

    @Autowired
    private AwardRuleManager awardRuleManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public AwardRule getById(String id)
    {
        AwardRule r=awardRuleManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public AwardRule get(AwardRule condition)
    {
        AwardRule result= awardRuleManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(AwardRule condition)
    {
        int result = awardRuleManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<AwardRule> list(AwardRule condition)
    {
        List<AwardRule> result = awardRuleManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<AwardRule> list(AwardRule condition, int pageNo, int pageSize)
    {
        PageInfo<AwardRule> result = awardRuleManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(AwardRule value)
    {
        int result = awardRuleManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(AwardRule value)
    {
        int result = awardRuleManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = awardRuleManager.delete(id);
        return result;
    }

}
