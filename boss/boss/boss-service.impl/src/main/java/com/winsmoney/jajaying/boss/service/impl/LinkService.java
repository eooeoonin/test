///**
// * Project:boss
// * File:Link.java
// * Date:2017-08-31
// * Copyright (c) 2017 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import java.util.List;
//
//import com.winsmoney.jajaying.boss.domain.model.Link;
//import com.winsmoney.jajaying.boss.domain.LinkDomain;
//import com.winsmoney.jajaying.boss.service.ILinkService;
//import com.winsmoney.jajaying.boss.service.request.LinkReqVo;
//import com.winsmoney.jajaying.boss.service.response.LinkResVo;
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
//* Description: 链接表 服务实现
//* date: 2017-08-31 05:16:26
//* @author: CodeCreator
//*/
//@Service(value = "linkService")
//public class LinkService implements ILinkService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(LinkService.class);
//
//    @Autowired
//    private LinkDomain linkDomain;
//
//    @Autowired
//    private ErrorCodeClient errorCodeClient;
//
//    /**
//    * 通过id查询 链接表
//    * @param id
//    * @return LinkResVo
//    */
//    public BossCommonResult<LinkResVo> getById(String id)
//    {
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        LinkResVo result = null;
//        if(StringUtils.isEmpty(id))
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Link link = linkDomain.getById(id);
//                result = BeanMapper.map(link,LinkResVo.class);
//
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Link异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //转外部错误码(1为自定义内码)
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//	/**
//     * 单笔查询 链接表
//     * @param linkReqVo
//     * @return LinkResVo
//     */
//    public BossCommonResult<LinkResVo> get(LinkReqVo linkReqVo)
//	{
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        LinkResVo result = null;
//        if(null == linkReqVo )
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Link condition = BeanMapper.map(linkReqVo,Link.class);
//                Link link = linkDomain.get(condition);
//                result = BeanMapper.map(link,LinkResVo.class);
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Link异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //将内部错误码转为外部错误码
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//    /**
//    * 统计 链接表
//    * @param linkReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> count(LinkReqVo linkReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Link condition = BeanMapper.map(linkReqVo,Link.class);
//            Integer count = linkDomain.count(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("统计Link异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//     * 分页列表
//     * @param linkReqVo 查询条件
//     * @param pageNo 当前页码
//     * @param pageSize 每页条数
//     * @return
//     */
//    public BossCommonResult<PageInfo<LinkResVo>> list(LinkReqVo linkReqVo, int pageNo, int pageSize)
//    {
//        BossCommonResult<PageInfo<LinkResVo>> result;
//        try
//        {
//            Link condition = BeanMapper.map(linkReqVo,Link.class);
//            PageInfo<Link> list = linkDomain.list(condition,pageNo,pageSize);
//            PageInfo<LinkResVo> pageListLinkResVo= BeanMapper.map(list,PageInfo.class);
//            pageListLinkResVo.setList(BeanMapper.mapList(list.getList(),LinkResVo.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListLinkResVo);
//        }
//        catch (Exception e)
//        {
//            logger.error("分页列表Link异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 添加 链接表
//    * @param linkReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> insert(LinkReqVo linkReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = linkDomain.insert(BeanMapper.map(linkReqVo,Link.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("添加Link异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 更新 链接表
//    * @param linkReqVo
//    * @return Integer
//    */
//    public BossCommonResult update(LinkReqVo linkReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Link condition = BeanMapper.map(linkReqVo,Link.class);
//            int count  = linkDomain.update(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("更新Link异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 删除 链接表
//    * @param id
//    * @return Integer
//    */
//    public BossCommonResult<Integer> delete(String id)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = linkDomain.delete(id);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("删除Link异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//}
//
//
//
