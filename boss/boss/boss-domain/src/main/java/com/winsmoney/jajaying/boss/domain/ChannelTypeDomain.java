/**
* Project:boss
* File:ChannelType.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.ChannelTypeManager;
import com.winsmoney.jajaying.boss.domain.model.ChannelType;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: channelType
* Description: 渠道类型 实现
* date: 2017-08-31 05:16:25
* @author: CodeCreator
*/
@Service
public class ChannelTypeDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelTypeDomain.class);

    @Autowired
    private ChannelTypeManager channelTypeManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public ChannelType getById(String id)
    {
        ChannelType r=channelTypeManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public ChannelType get(ChannelType condition)
    {
        ChannelType result= channelTypeManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(ChannelType condition)
    {
        int result = channelTypeManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<ChannelType> list(ChannelType condition)
    {
        List<ChannelType> result = channelTypeManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<ChannelType> list(ChannelType condition, int pageNo, int pageSize)
    {
        PageInfo<ChannelType> result = channelTypeManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(ChannelType value)
    {
        int result = channelTypeManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(ChannelType value)
    {
        int result = channelTypeManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = channelTypeManager.delete(id);
        return result;
    }

}
