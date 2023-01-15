/**
* Project:boss
* File:Channel.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.ChannelManager;
import com.winsmoney.jajaying.boss.domain.model.Channel;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: channel
* Description: 渠道表 实现
* date: 2017-08-31 05:12:49
* @author: CodeCreator
*/
@Service
public class ChannelDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelDomain.class);

    @Autowired
    private ChannelManager channelManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Channel getById(String id)
    {
        Channel r=channelManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Channel get(Channel condition)
    {
        Channel result= channelManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(Channel condition)
    {
        int result = channelManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Channel> list(Channel condition)
    {
        List<Channel> result = channelManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Channel> list(Channel condition, int pageNo, int pageSize)
    {
        PageInfo<Channel> result = channelManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(Channel value)
    {
        int result = channelManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(Channel value)
    {
        int result = channelManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = channelManager.delete(id);
        return result;
    }

}
