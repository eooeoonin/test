/**
* Project:boss
* File:RedmoneyInfo.java
* Date:2016-10-09
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.RedmoneyInfoManager;
import com.winsmoney.jajaying.boss.domain.model.RedmoneyInfo;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: redmoneyInfo
* Description:  实现
* date: 2016-10-09 02:14:08
* @author: CodeCreator
*/
@Service
public class RedmoneyInfoDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(RedmoneyInfoDomain.class);

    @Autowired
    private RedmoneyInfoManager redmoneyInfoManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public RedmoneyInfo getById(String id)
    {
        RedmoneyInfo r=redmoneyInfoManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public RedmoneyInfo get(RedmoneyInfo condition)
    {
        RedmoneyInfo result= redmoneyInfoManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(RedmoneyInfo condition)
    {
        int result = redmoneyInfoManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<RedmoneyInfo> list(RedmoneyInfo condition)
    {
        List<RedmoneyInfo> result = redmoneyInfoManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<RedmoneyInfo> list(RedmoneyInfo condition, int pageNo, int pageSize)
    {
        PageInfo<RedmoneyInfo> result = redmoneyInfoManager.list(condition,pageNo,pageSize);
        logger.info(result.toString());
        return result;
    }

    /**
     * 插入
     */
    public int insert(RedmoneyInfo value)
    {
        int result = redmoneyInfoManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(RedmoneyInfo value)
    {
        int result = redmoneyInfoManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = redmoneyInfoManager.delete(id);
        return result;
    }

}
