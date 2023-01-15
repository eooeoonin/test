/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import java.util.List;

import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.jajaying.boss.domain.ResourceDomain;
import com.winsmoney.jajaying.boss.service.IResourceService;
import com.winsmoney.jajaying.boss.service.request.ResourceReqVo;
import com.winsmoney.jajaying.boss.service.response.ResourceResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.jajaying.basedata.service.client.ErrorCodeClient;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.model.ErrorCode;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Description:  服务实现
* date: 2016-08-03 06:03:17
* @author: CodeCreator
*/
@Service(value = "resourceService")
public class ResourceService implements IResourceService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private ResourceDomain resourceDomain;

    @Autowired
    private ErrorCodeClient errorCodeClient;

	/**
     * 单笔查询 
     * @param resourceReqVo
     * @return ResourceResVo
     */
    public BossCommonResult<ResourceResVo> getResource(ResourceReqVo resourceReqVo)
	{
        BossCommonResult<ResourceResVo> result;
        try
        {
            Resource condition = BeanMapper.map(resourceReqVo,Resource.class);
            Resource resource = resourceDomain.getResource(condition);
            ResourceResVo resourceResVo = BeanMapper.map(resource,ResourceResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),resourceResVo);
        }
        catch (Exception e)
        {
            logger.error("取得Resource异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 列表 
    * @param resourceReqVo
    * @return List<ResourceResVo>
    */
    public BossCommonResult<List<ResourceResVo>> listResource(ResourceReqVo resourceReqVo)
    {
        BossCommonResult<List<ResourceResVo>> result;
        try
        {
            Resource condition = BeanMapper.map(resourceReqVo,Resource.class);
            List<Resource> list = resourceDomain.listResource(condition);
            List<ResourceResVo> listResourceResVo = BeanMapper.mapList(list,ResourceResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),listResourceResVo);
        }
        catch (Exception e)
        {
            logger.error("列表Resource异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 统计 
    * @param resourceReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> countResource(ResourceReqVo resourceReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Resource condition = BeanMapper.map(resourceReqVo,Resource.class);
            Integer count = resourceDomain.countResource(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("统计Resource异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
     * 分页列表
     * @param resourceReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    public BossCommonResult<PageInfo<ResourceResVo>> listResource(ResourceReqVo resourceReqVo, int pageNo, int pageSize)
    {
        BossCommonResult<PageInfo<ResourceResVo>> result;
        try
        {
            Resource condition = BeanMapper.map(resourceReqVo,Resource.class);
            PageInfo<Resource> list = resourceDomain.listResource(condition,pageNo,pageSize);
            PageInfo<ResourceResVo> pageListResourceResVo= BeanMapper.map(list,PageInfo.class);
            pageListResourceResVo.setList(BeanMapper.mapList(list.getList(),ResourceResVo.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListResourceResVo);
        }
        catch (Exception e)
        {
            logger.error("分页列表Resource异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 添加 
    * @param resourceReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> insertResource(ResourceReqVo resourceReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = resourceDomain.insertResource(BeanMapper.map(resourceReqVo,Resource.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("添加Resource异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 更新 
    * @param resourceReqVo
    * @return Integer
    */
    public BossCommonResult updateResource(ResourceReqVo resourceReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Resource condition = BeanMapper.map(resourceReqVo,Resource.class);
            int count  = resourceDomain.updateResource(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("更新Resource异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 删除 
    * @param id
    * @return Integer
    */
    public BossCommonResult deleteResource(String id)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = resourceDomain.deleteResource(id);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("删除Resource异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }
}



