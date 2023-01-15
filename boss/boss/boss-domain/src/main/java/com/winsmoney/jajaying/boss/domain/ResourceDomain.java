/**
* Project:boss
* File:Resource.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.ResourceManager;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* ClassName: resource
* Description:  实现
* date: 2016-08-03 06:03:17
* @author: CodeCreator
*/
@Service
public class ResourceDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ResourceDomain.class);

    @Autowired
    private ResourceManager resourceManager;


    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Resource getById(String id)
    {
        Resource r=resourceManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Resource getResource(Resource condition)
    {
        Resource result= resourceManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int countResource(Resource condition)
    {
        int result = resourceManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Resource> listResource(Resource condition)
    {
        List<Resource> result = resourceManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Resource> listResource(Resource condition, int pageNo, int pageSize)
    {
        PageInfo<Resource> result = resourceManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insertResource(Resource value)
    {
        int result = resourceManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int updateResource(Resource value)
    {
        int result = resourceManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int deleteResource(String id)
    {
        int result = resourceManager.delete(id);
        return result;
    }
    public List<Resource> findResourcesByRoleId(String roleId){
		return resourceManager.findResourcesByRoleId(roleId);
    	
    }

	public List<Resource> getPermissonByRoleId(String roleId) {
		return resourceManager.getPermissonByRoleId(roleId);
	}
	public List<Resource> getPermissonFlagByRoleId(String roleId) {
		return resourceManager.getPermissonFlagByRoleId(roleId);
	}

	public int deleteResourcesByRoleId(String roleId) {
		return resourceManager.deleteResourcesByRoleId(roleId);
		
	}

}
