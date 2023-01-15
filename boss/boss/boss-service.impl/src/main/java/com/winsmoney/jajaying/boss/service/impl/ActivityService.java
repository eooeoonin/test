///**
// * Project:boss
// * File:Activity.java
// * Date:2017-09-04
// * Copyright (c) 2017 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import java.util.List;
//
//import com.winsmoney.jajaying.boss.domain.model.Activity;
//import com.winsmoney.jajaying.boss.domain.ActivityDomain;
//import com.winsmoney.jajaying.boss.service.IActivityService;
//import com.winsmoney.jajaying.boss.service.request.ActivityReqVo;
//import com.winsmoney.jajaying.boss.service.response.ActivityResVo;
//import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
//import com.winsmoney.jajaying.basedata.service.client.ErrorCodeClient;
//import com.winsmoney.jajaying.boss.domain.exception.InnerErrorCode;
//
//import org.apache.commons.lang.StringUtils;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
//import com.winsmoney.platform.framework.core.model.ErrorCode;
//import com.winsmoney.platform.framework.core.util.BeanMapper;
//import com.winsmoney.framework.pagehelper.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
//* Description: 活动表 服务实现
//* date: 2017-09-04 05:21:24
//* @author: CodeCreator
//*/
//@Service(value = "activityService")
//public class ActivityService implements IActivityService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityService.class);
//
//    @Autowired
//    private ActivityDomain activityDomain;
//
//    @Autowired
//    private ErrorCodeClient errorCodeClient;
//
//    /**
//    * 通过id查询 活动表
//    * @param id
//    * @return ActivityResVo
//    */
//    public BossCommonResult<ActivityResVo> getById(String id)
//    {
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ActivityResVo result = null;
//        if(StringUtils.isEmpty(id))
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Activity activity = activityDomain.getById(id);
//                result = BeanMapper.map(activity,ActivityResVo.class);
//
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Activity异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //转外部错误码(1为自定义内码)
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//	/**
//     * 单笔查询 活动表
//     * @param activityReqVo
//     * @return ActivityResVo
//     */
//    public BossCommonResult<ActivityResVo> get(ActivityReqVo activityReqVo)
//	{
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ActivityResVo result = null;
//        if(null == activityReqVo )
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Activity condition = BeanMapper.map(activityReqVo,Activity.class);
//                Activity activity = activityDomain.get(condition);
//                result = BeanMapper.map(activity,ActivityResVo.class);
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Activity异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //将内部错误码转为外部错误码
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//    /**
//    * 统计 活动表
//    * @param activityReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> count(ActivityReqVo activityReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Activity condition = BeanMapper.map(activityReqVo,Activity.class);
//            Integer count = activityDomain.count(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("统计Activity异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//     * 分页列表
//     * @param activityReqVo 查询条件
//     * @param pageNo 当前页码
//     * @param pageSize 每页条数
//     * @return
//     */
//    public BossCommonResult<PageInfo<ActivityResVo>> list(ActivityReqVo activityReqVo, int pageNo, int pageSize)
//    {
//        BossCommonResult<PageInfo<ActivityResVo>> result;
//        try
//        {
//            Activity condition = BeanMapper.map(activityReqVo,Activity.class);
//            PageInfo<Activity> list = activityDomain.list(condition,pageNo,pageSize);
//            PageInfo<ActivityResVo> pageListActivityResVo= BeanMapper.map(list,PageInfo.class);
//            pageListActivityResVo.setList(BeanMapper.mapList(list.getList(),ActivityResVo.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListActivityResVo);
//        }
//        catch (Exception e)
//        {
//            logger.error("分页列表Activity异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 添加 活动表
//    * @param activityReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> insert(ActivityReqVo activityReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = activityDomain.insert(BeanMapper.map(activityReqVo,Activity.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("添加Activity异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 更新 活动表
//    * @param activityReqVo
//    * @return Integer
//    */
//    public BossCommonResult update(ActivityReqVo activityReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Activity condition = BeanMapper.map(activityReqVo,Activity.class);
//            int count  = activityDomain.update(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("更新Activity异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 删除 活动表
//    * @param id
//    * @return Integer
//    */
//    public BossCommonResult<Integer> delete(String id)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = activityDomain.delete(id);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("删除Activity异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//}
//
//
//
