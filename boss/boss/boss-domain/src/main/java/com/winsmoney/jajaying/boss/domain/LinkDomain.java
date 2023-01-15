/**
* Project:boss
* File:Link.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.LinkManager;
import com.winsmoney.jajaying.boss.domain.model.Link;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: link
* Description: 链接表 实现
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
@Service
public class LinkDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(LinkDomain.class);

    @Autowired
    private LinkManager linkManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Link getById(String id)
    {
        Link r=linkManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Link get(Link condition)
    {
        Link result= linkManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(Link condition)
    {
        int result = linkManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Link> list(Link condition)
    {
        List<Link> result = linkManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Link> list(Link condition, int pageNo, int pageSize)
    {
        PageInfo<Link> result = linkManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(Link value)
    {
        int result = linkManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(Link value)
    {
        int result = linkManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = linkManager.delete(id);
        return result;
    }
    
    public int deleteByAcode(String aCode)
    {
        int result = linkManager.deleteByAcode(aCode);
        return result;
    }
    
}
