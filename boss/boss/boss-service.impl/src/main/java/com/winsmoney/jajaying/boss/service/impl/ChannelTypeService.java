///**
// * Project:boss
// * File:ChannelType.java
// * Date:2017-08-31
// * Copyright (c) 2017 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import java.util.List;
//
//import com.winsmoney.jajaying.boss.domain.model.ChannelType;
//import com.winsmoney.jajaying.boss.domain.ChannelTypeDomain;
//import com.winsmoney.jajaying.boss.service.IChannelTypeService;
//import com.winsmoney.jajaying.boss.service.request.ChannelTypeReqVo;
//import com.winsmoney.jajaying.boss.service.response.ChannelTypeResVo;
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
//* Description: 渠道类型 服务实现
//* date: 2017-08-31 05:16:26
//* @author: CodeCreator
//*/
//@Service(value = "channelTypeService")
//public class ChannelTypeService implements IChannelTypeService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelTypeService.class);
//
//    @Autowired
//    private ChannelTypeDomain channelTypeDomain;
//
//    @Autowired
//    private ErrorCodeClient errorCodeClient;
//
//    /**
//    * 通过id查询 渠道类型
//    * @param id
//    * @return ChannelTypeResVo
//    */
//    public BossCommonResult<ChannelTypeResVo> getById(String id)
//    {
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ChannelTypeResVo result = null;
//        if(StringUtils.isEmpty(id))
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                ChannelType channelType = channelTypeDomain.getById(id);
//                result = BeanMapper.map(channelType,ChannelTypeResVo.class);
//
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询ChannelType异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //转外部错误码(1为自定义内码)
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//	/**
//     * 单笔查询 渠道类型
//     * @param channelTypeReqVo
//     * @return ChannelTypeResVo
//     */
//    public BossCommonResult<ChannelTypeResVo> get(ChannelTypeReqVo channelTypeReqVo)
//	{
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ChannelTypeResVo result = null;
//        if(null == channelTypeReqVo )
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                ChannelType condition = BeanMapper.map(channelTypeReqVo,ChannelType.class);
//                ChannelType channelType = channelTypeDomain.get(condition);
//                result = BeanMapper.map(channelType,ChannelTypeResVo.class);
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询ChannelType异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //将内部错误码转为外部错误码
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//    /**
//    * 统计 渠道类型
//    * @param channelTypeReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> count(ChannelTypeReqVo channelTypeReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            ChannelType condition = BeanMapper.map(channelTypeReqVo,ChannelType.class);
//            Integer count = channelTypeDomain.count(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("统计ChannelType异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//     * 分页列表
//     * @param channelTypeReqVo 查询条件
//     * @param pageNo 当前页码
//     * @param pageSize 每页条数
//     * @return
//     */
//    public BossCommonResult<PageInfo<ChannelTypeResVo>> list(ChannelTypeReqVo channelTypeReqVo, int pageNo, int pageSize)
//    {
//        BossCommonResult<PageInfo<ChannelTypeResVo>> result;
//        try
//        {
//            ChannelType condition = BeanMapper.map(channelTypeReqVo,ChannelType.class);
//            PageInfo<ChannelType> list = channelTypeDomain.list(condition,pageNo,pageSize);
//            PageInfo<ChannelTypeResVo> pageListChannelTypeResVo= BeanMapper.map(list,PageInfo.class);
//            pageListChannelTypeResVo.setList(BeanMapper.mapList(list.getList(),ChannelTypeResVo.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListChannelTypeResVo);
//        }
//        catch (Exception e)
//        {
//            logger.error("分页列表ChannelType异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 添加 渠道类型
//    * @param channelTypeReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> insert(ChannelTypeReqVo channelTypeReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = channelTypeDomain.insert(BeanMapper.map(channelTypeReqVo,ChannelType.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("添加ChannelType异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 更新 渠道类型
//    * @param channelTypeReqVo
//    * @return Integer
//    */
//    public BossCommonResult update(ChannelTypeReqVo channelTypeReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            ChannelType condition = BeanMapper.map(channelTypeReqVo,ChannelType.class);
//            int count  = channelTypeDomain.update(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("更新ChannelType异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 删除 渠道类型
//    * @param id
//    * @return Integer
//    */
//    public BossCommonResult<Integer> delete(String id)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = channelTypeDomain.delete(id);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("删除ChannelType异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//}
//
//
//
