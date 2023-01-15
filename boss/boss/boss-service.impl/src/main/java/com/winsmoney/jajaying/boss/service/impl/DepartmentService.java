/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import java.util.List;

import com.winsmoney.jajaying.boss.domain.model.Department;
import com.winsmoney.jajaying.boss.domain.DepartmentDomain;
import com.winsmoney.jajaying.boss.service.IDepartmentService;
import com.winsmoney.jajaying.boss.service.request.DepartmentReqVo;
import com.winsmoney.jajaying.boss.service.response.DepartmentResVo;
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
@Service(value = "departmentService")
public class DepartmentService implements IDepartmentService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDomain departmentDomain;

    @Autowired
    private ErrorCodeClient errorCodeClient;

	/**
     * 单笔查询 
     * @param departmentReqVo
     * @return DepartmentResVo
     */
    public BossCommonResult<DepartmentResVo> getDepartment(DepartmentReqVo departmentReqVo)
	{
        BossCommonResult<DepartmentResVo> result;
        try
        {
            Department condition = BeanMapper.map(departmentReqVo,Department.class);
            Department department = departmentDomain.getDepartment(condition);
            DepartmentResVo departmentResVo = BeanMapper.map(department,DepartmentResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),departmentResVo);
        }
        catch (Exception e)
        {
            logger.error("取得Department异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 列表 
    * @param departmentReqVo
    * @return List<DepartmentResVo>
    */
    public BossCommonResult<List<DepartmentResVo>> listDepartment(DepartmentReqVo departmentReqVo)
    {
        BossCommonResult<List<DepartmentResVo>> result;
        try
        {
            Department condition = BeanMapper.map(departmentReqVo,Department.class);
            List<Department> list = departmentDomain.listDepartment(condition);
            List<DepartmentResVo> listDepartmentResVo = BeanMapper.mapList(list,DepartmentResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),listDepartmentResVo);
        }
        catch (Exception e)
        {
            logger.error("列表Department异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 统计 
    * @param departmentReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> countDepartment(DepartmentReqVo departmentReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Department condition = BeanMapper.map(departmentReqVo,Department.class);
            Integer count = departmentDomain.countDepartment(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("统计Department异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
     * 分页列表
     * @param departmentReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    public BossCommonResult<PageInfo<DepartmentResVo>> listDepartment(DepartmentReqVo departmentReqVo, int pageNo, int pageSize)
    {
        BossCommonResult<PageInfo<DepartmentResVo>> result;
        try
        {
            Department condition = BeanMapper.map(departmentReqVo,Department.class);
            PageInfo<Department> list = departmentDomain.listDepartment(condition,pageNo,pageSize);
            PageInfo<DepartmentResVo> pageListDepartmentResVo= BeanMapper.map(list,PageInfo.class);
            pageListDepartmentResVo.setList(BeanMapper.mapList(list.getList(),DepartmentResVo.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListDepartmentResVo);
        }
        catch (Exception e)
        {
            logger.error("分页列表Department异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 添加 
    * @param departmentReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> insertDepartment(DepartmentReqVo departmentReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = departmentDomain.insertDepartment(BeanMapper.map(departmentReqVo,Department.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("添加Department异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 更新 
    * @param departmentReqVo
    * @return Integer
    */
    public BossCommonResult updateDepartment(DepartmentReqVo departmentReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Department condition = BeanMapper.map(departmentReqVo,Department.class);
            int count  = departmentDomain.updateDepartment(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("更新Department异常",e);
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
    public BossCommonResult deleteDepartment(String id)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = departmentDomain.deleteDepartment(id);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("删除Department异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }
}



