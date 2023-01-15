/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import java.util.List;

import com.winsmoney.jajaying.boss.domain.model.UserRole;
import com.winsmoney.jajaying.boss.domain.UserRoleDomain;
import com.winsmoney.jajaying.boss.service.IUserRoleService;
import com.winsmoney.jajaying.boss.service.request.UserRoleReqVo;
import com.winsmoney.jajaying.boss.service.response.UserRoleResVo;
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
* date: 2016-08-03 06:03:18
* @author: CodeCreator
*/
@Service(value = "userRoleService")
public class UserRoleService implements IUserRoleService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleDomain userRoleDomain;

    @Autowired
    private ErrorCodeClient errorCodeClient;

	/**
     * 单笔查询 
     * @param userRoleReqVo
     * @return UserRoleResVo
     */
    public BossCommonResult<UserRoleResVo> getUserRole(UserRoleReqVo userRoleReqVo)
	{
        BossCommonResult<UserRoleResVo> result;
        try
        {
            UserRole condition = BeanMapper.map(userRoleReqVo,UserRole.class);
            UserRole userRole = userRoleDomain.getUserRole(condition);
            UserRoleResVo userRoleResVo = BeanMapper.map(userRole,UserRoleResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),userRoleResVo);
        }
        catch (Exception e)
        {
            logger.error("取得UserRole异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 列表 
    * @param userRoleReqVo
    * @return List<UserRoleResVo>
    */
    public BossCommonResult<List<UserRoleResVo>> listUserRole(UserRoleReqVo userRoleReqVo)
    {
        BossCommonResult<List<UserRoleResVo>> result;
        try
        {
            UserRole condition = BeanMapper.map(userRoleReqVo,UserRole.class);
            List<UserRole> list = userRoleDomain.listUserRole(condition);
            List<UserRoleResVo> listUserRoleResVo = BeanMapper.mapList(list,UserRoleResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),listUserRoleResVo);
        }
        catch (Exception e)
        {
            logger.error("列表UserRole异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 统计 
    * @param userRoleReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> countUserRole(UserRoleReqVo userRoleReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            UserRole condition = BeanMapper.map(userRoleReqVo,UserRole.class);
            Integer count = userRoleDomain.countUserRole(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("统计UserRole异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
     * 分页列表
     * @param userRoleReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    public BossCommonResult<PageInfo<UserRoleResVo>> listUserRole(UserRoleReqVo userRoleReqVo, int pageNo, int pageSize)
    {
        BossCommonResult<PageInfo<UserRoleResVo>> result;
        try
        {
            UserRole condition = BeanMapper.map(userRoleReqVo,UserRole.class);
            PageInfo<UserRole> list = userRoleDomain.listUserRole(condition,pageNo,pageSize);
            PageInfo<UserRoleResVo> pageListUserRoleResVo= BeanMapper.map(list,PageInfo.class);
            pageListUserRoleResVo.setList(BeanMapper.mapList(list.getList(),UserRoleResVo.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListUserRoleResVo);
        }
        catch (Exception e)
        {
            logger.error("分页列表UserRole异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 添加 
    * @param userRoleReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> insertUserRole(UserRoleReqVo userRoleReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = userRoleDomain.insertUserRole(BeanMapper.map(userRoleReqVo,UserRole.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("添加UserRole异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 更新 
    * @param userRoleReqVo
    * @return Integer
    */
    public BossCommonResult updateUserRole(UserRoleReqVo userRoleReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            UserRole condition = BeanMapper.map(userRoleReqVo,UserRole.class);
            int count  = userRoleDomain.updateUserRole(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("更新UserRole异常",e);
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
    public BossCommonResult deleteUserRole(String id)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = userRoleDomain.deleteUserRole(id);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("删除UserRole异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }
}



