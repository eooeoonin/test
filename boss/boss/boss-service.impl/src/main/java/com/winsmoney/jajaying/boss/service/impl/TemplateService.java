///**
// * Project:boss
// * File:Template.java
// * Date:2017-09-04
// * Copyright (c) 2017 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import java.util.List;
//
//import com.winsmoney.jajaying.boss.domain.model.Template;
//import com.winsmoney.jajaying.boss.domain.TemplateDomain;
//import com.winsmoney.jajaying.boss.service.ITemplateService;
//import com.winsmoney.jajaying.boss.service.request.TemplateReqVo;
//import com.winsmoney.jajaying.boss.service.response.TemplateResVo;
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
//* Description: 模板表 服务实现
//* date: 2017-09-04 04:11:05
//* @author: CodeCreator
//*/
//@Service(value = "templateService")
//public class TemplateService implements ITemplateService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplateService.class);
//
//    @Autowired
//    private TemplateDomain templateDomain;
//
//    @Autowired
//    private ErrorCodeClient errorCodeClient;
//
//    /**
//    * 通过id查询 模板表
//    * @param id
//    * @return TemplateResVo
//    */
//    public BossCommonResult<TemplateResVo> getById(String id)
//    {
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        TemplateResVo result = null;
//        if(StringUtils.isEmpty(id))
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Template template = templateDomain.getById(id);
//                result = BeanMapper.map(template,TemplateResVo.class);
//
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Template异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //转外部错误码(1为自定义内码)
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//	/**
//     * 单笔查询 模板表
//     * @param templateReqVo
//     * @return TemplateResVo
//     */
//    public BossCommonResult<TemplateResVo> get(TemplateReqVo templateReqVo)
//	{
//        InnerErrorCode innerErrorCode = InnerErrorCode.Success;
//        TemplateResVo result = null;
//        if(null == templateReqVo )
//        {
//            innerErrorCode = InnerErrorCode.Param_Error;
//        }
//        if(InnerErrorCode.Success.equals(innerErrorCode))
//        {
//            try
//            {
//                Template condition = BeanMapper.map(templateReqVo,Template.class);
//                Template template = templateDomain.get(condition);
//                result = BeanMapper.map(template,TemplateResVo.class);
//            }
//            catch (Exception e)
//            {
//                logger.error("通过id查询Template异常：" + e.getMessage(), e);
//                innerErrorCode = InnerErrorCode.Failure;
//            }
//        }
//        ErrorCode outCode = errorCodeClient.changeCode(innerErrorCode.getCode()); //将内部错误码转为外部错误码
//        return new BossCommonResult(outCode.getCode(),outCode.getMessage(),result);
//    }
//
//
//    /**
//    * 统计 模板表
//    * @param templateReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> count(TemplateReqVo templateReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Template condition = BeanMapper.map(templateReqVo,Template.class);
//            Integer count = templateDomain.count(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("统计Template异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//     * 分页列表
//     * @param templateReqVo 查询条件
//     * @param pageNo 当前页码
//     * @param pageSize 每页条数
//     * @return
//     */
//    public BossCommonResult<PageInfo<TemplateResVo>> list(TemplateReqVo templateReqVo, int pageNo, int pageSize)
//    {
//        BossCommonResult<PageInfo<TemplateResVo>> result;
//        try
//        {
//            Template condition = BeanMapper.map(templateReqVo,Template.class);
//            PageInfo<Template> list = templateDomain.list(condition,pageNo,pageSize);
//            PageInfo<TemplateResVo> pageListTemplateResVo= BeanMapper.map(list,PageInfo.class);
//            pageListTemplateResVo.setList(BeanMapper.mapList(list.getList(),TemplateResVo.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),pageListTemplateResVo);
//        }
//        catch (Exception e)
//        {
//            logger.error("分页列表Template异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 添加 模板表
//    * @param templateReqVo
//    * @return Integer
//    */
//    public BossCommonResult<Integer> insert(TemplateReqVo templateReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = templateDomain.insert(BeanMapper.map(templateReqVo,Template.class));
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("添加Template异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 更新 模板表
//    * @param templateReqVo
//    * @return Integer
//    */
//    public BossCommonResult update(TemplateReqVo templateReqVo)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            Template condition = BeanMapper.map(templateReqVo,Template.class);
//            int count  = templateDomain.update(condition);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("更新Template异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//
//    /**
//    * 删除 模板表
//    * @param id
//    * @return Integer
//    */
//    public BossCommonResult<Integer> delete(String id)
//    {
//        BossCommonResult<Integer> result;
//        try
//        {
//            int count  = templateDomain.delete(id);
//            ErrorCode outCode = errorCodeClient.changeCode("1"); //转外部错误码(1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(),count);
//        }
//        catch (Exception e)
//        {
//            logger.error("删除Template异常",e);
//            ErrorCode outCode = errorCodeClient.changeCode("-1"); //转外部错误码(-1为自定义内码)
//            result = new BossCommonResult(outCode.getCode(),outCode.getMessage(), null);
//        }
//        return result;
//    }
//}
//
//
//
