/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import java.util.List;

import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.StaffDomain;
import com.winsmoney.jajaying.boss.service.IStaffService;
import com.winsmoney.jajaying.boss.service.request.StaffReqVo;
import com.winsmoney.jajaying.boss.service.response.StaffResVo;
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
@Service(value = "staffService")
public class StaffService implements IStaffService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(StaffService.class);

    @Autowired
    private StaffDomain staffDomain;

    @Autowired
    private ErrorCodeClient errorCodeClient;

	/**
     * 单笔查询 
     * @param staffReqVo
     * @return StaffResVo
     */
    public BossCommonResult<StaffResVo> getStaff(StaffReqVo staffReqVo)
	{
        BossCommonResult<StaffResVo> result;
        try
        {
            Staff condition = BeanMapper.map(staffReqVo,Staff.class);
            Staff staff = staffDomain.getStaff(condition);
            StaffResVo staffResVo = BeanMapper.map(staff,StaffResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),staffResVo);
        }
        catch (Exception e)
        {
            logger.error("取得Staff异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 列表 
    * @param staffReqVo
    * @return List<StaffResVo>
    */
    public BossCommonResult<List<StaffResVo>> listStaff(StaffReqVo staffReqVo)
    {
        BossCommonResult<List<StaffResVo>> result;
        try
        {
            Staff condition = BeanMapper.map(staffReqVo,Staff.class);
            List<Staff> list = staffDomain.listStaff(condition);
            List<StaffResVo> listStaffResVo = BeanMapper.mapList(list,StaffResVo.class);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),listStaffResVo);
        }
        catch (Exception e)
        {
            logger.error("列表Staff异常：" + e.getMessage(), e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 统计 
    * @param staffReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> countStaff(StaffReqVo staffReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Staff condition = BeanMapper.map(staffReqVo,Staff.class);
            Integer count = staffDomain.countStaff(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("统计Staff异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
     * 分页列表
     * @param staffReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    public BossCommonResult<PageInfo<StaffResVo>> listStaff(StaffReqVo staffReqVo, int pageNo, int pageSize)
    {
        BossCommonResult<PageInfo<StaffResVo>> result;
        try
        {
            Staff condition = BeanMapper.map(staffReqVo,Staff.class);
            PageInfo<Staff> list = staffDomain.listStaff(condition,pageNo,pageSize);
            PageInfo<StaffResVo> pageListStaffResVo= BeanMapper.map(list,PageInfo.class);
            pageListStaffResVo.setList(BeanMapper.mapList(list.getList(),StaffResVo.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListStaffResVo);
        }
        catch (Exception e)
        {
            logger.error("分页列表Staff异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 添加 
    * @param staffReqVo
    * @return Integer
    */
    public BossCommonResult<Integer> insertStaff(StaffReqVo staffReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = staffDomain.insertStaff(BeanMapper.map(staffReqVo,Staff.class));
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("添加Staff异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }

    /**
    * 更新 
    * @param staffReqVo
    * @return Integer
    */
    public BossCommonResult updateStaff(StaffReqVo staffReqVo)
    {
        BossCommonResult<Integer> result;
        try
        {
            Staff condition = BeanMapper.map(staffReqVo,Staff.class);
            int count  = staffDomain.updateStaff(condition);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("更新Staff异常",e);
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
    public BossCommonResult deleteStaff(String id)
    {
        BossCommonResult<Integer> result;
        try
        {
            int count  = staffDomain.deleteStaff(id);
            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
        }
        catch (Exception e)
        {
            logger.error("删除Staff异常",e);
            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
        }
        return result;
    }
}



