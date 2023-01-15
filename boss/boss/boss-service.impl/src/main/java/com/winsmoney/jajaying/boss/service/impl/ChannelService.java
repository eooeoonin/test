///**
// * Project:boss
// * File:Channel.java
// * Date:2017-08-31
// * Copyright (c) 2017 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import java.util.List;
//
//import com.winsmoney.jajaying.boss.domain.model.Channel;
//import com.winsmoney.jajaying.boss.domain.ChannelDomain;
//import com.winsmoney.jajaying.boss.service.IChannelService;
//import com.winsmoney.jajaying.boss.service.request.ChannelReqVo;
//import com.winsmoney.jajaying.boss.service.response.ChannelResVo;
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
//* Description: 渠道表 服务实现
//* date: 2017-08-31 05:12:49
//* @author: CodeCreator
//*/
//@Service(value = "channelService")
//public class ChannelService implements IChannelService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelService.class);
//
//    @Autowired
//    private ChannelDomain channelDomain;
//
//    @Autowired
//    private ErrorCodeClient errorCodeClient;
//
//    /**
//    * 通过id查询 渠道表
//    * @param id
//    * @return ChannelResVo
//    */
//    public BossCommonResult<ChannelResVo> getById(String id)
//    {
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ChannelResVo result = null;
//        if(StringUtils.isEmpty(id))
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Channel channel = channelDomain.getById(id);
//                result = BeanMapper.map(channel,ChannelResVo.class);
//
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Channel异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //转外部错误码(1为自定义内码)
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//	/**
//     * 单笔查询 渠道表
//     * @param channelReqVo
//     * @return ChannelResVo
//     */
//    public BossCommonResult<ChannelResVo> get(ChannelReqVo channelReqVo)
//	{
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        ChannelResVo result = null;
//        if(null == channelReqVo )
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Channel condition = BeanMapper.map(channelReqVo,Channel.class);
//                Channel channel = channelDomain.get(condition);
//                result = BeanMapper.map(channel,ChannelResVo.class);
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Channel异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //将内部错误码转为外部错误码
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//    /**
//    * 统计 渠道表
//    * @param channelReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> count(ChannelReqVo channelReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Channel condition = BeanMapper.map(channelReqVo,Channel.class);
//            Integer count = channelDomain.count(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("统计Channel异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//     * 分页列表
//     * @param channelReqVo 查询条件
//     * @param pageNo 当前页码
//     * @param pageSize 每页条数
//     * @return
//     */
//    public BossCommonResult<PageInfo<ChannelResVo>> list(ChannelReqVo channelReqVo, int pageNo, int pageSize)
//    {
//        BossCommonResult<PageInfo<ChannelResVo>> result;
//        try
//        {
//            Channel condition = BeanMapper.map(channelReqVo,Channel.class);
//            PageInfo<Channel> list = channelDomain.list(condition,pageNo,pageSize);
//            PageInfo<ChannelResVo> pageListChannelResVo= BeanMapper.map(list,PageInfo.class);
//            pageListChannelResVo.setList(BeanMapper.mapList(list.getList(),ChannelResVo.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListChannelResVo);
//        }
//        catch (Exception e)
//        {
//            logger.error("分页列表Channel异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 添加 渠道表
//    * @param channelReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> insert(ChannelReqVo channelReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = channelDomain.insert(BeanMapper.map(channelReqVo,Channel.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("添加Channel异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 更新 渠道表
//    * @param channelReqVo
//    * @return Integer
//    */
//    public BossCommonResult update(ChannelReqVo channelReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Channel condition = BeanMapper.map(channelReqVo,Channel.class);
//            int count  = channelDomain.update(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("更新Channel异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 删除 渠道表
//    * @param id
//    * @return Integer
//    */
//    public BossCommonResult<Integer> delete(String id)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = channelDomain.delete(id);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("删除Channel异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//}
//
//
//
