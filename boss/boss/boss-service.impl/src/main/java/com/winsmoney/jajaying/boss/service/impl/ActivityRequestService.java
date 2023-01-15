/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.model.ActivityRequest;
import com.winsmoney.jajaying.boss.domain.ActivityRequestDomain;
import com.winsmoney.jajaying.boss.service.IActivityRequestService;
import com.winsmoney.jajaying.boss.service.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.service.request.ActivityRequestReqVo;
import com.winsmoney.jajaying.boss.service.response.ActivityRequestResVo;

import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.log.LoggerLevel;
import com.winsmoney.platform.framework.log.annotation.DigestLogAnnotated;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Description: 活动请求 服务实现
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
//@Service(value = "activityRequestService")
public class ActivityRequestService implements IActivityRequestService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityRequestService.class);

    @Autowired
    private ActivityRequestDomain activityRequestDomain;
    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<ActivityRequestResVo> getById(String id) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( id ));
        BossCommonResult<ActivityRequestResVo> result;
        if (StringUtils.isBlank( id )) {
            return  new BossCommonResult<ActivityRequestResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            ActivityRequest activityRequest = activityRequestDomain.getById( id );
            return new BossCommonResult<ActivityRequestResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( activityRequest , ActivityRequestResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<ActivityRequestResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<ActivityRequestResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<ActivityRequestResVo> get(ActivityRequestReqVo activityRequestReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( activityRequestReqVo ));
        BossCommonResult<ActivityRequestResVo> result;
        if ( null == activityRequestReqVo ) {
            return  new BossCommonResult<ActivityRequestResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            ActivityRequest activityRequest = activityRequestDomain.get( BeanMapper.map( activityRequestReqVo , ActivityRequest.class ));
            return new BossCommonResult<ActivityRequestResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( activityRequest , ActivityRequestResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<ActivityRequestResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<ActivityRequestResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> count(ActivityRequestReqVo activityRequestReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( activityRequestReqVo ));
        BossCommonResult<Integer> result;
        if ( null == activityRequestReqVo ) {
            return new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            Integer count = activityRequestDomain.count( BeanMapper.map( activityRequestReqVo , ActivityRequest.class ));
            return new BossCommonResult<Integer>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), count );
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<PageInfo<ActivityRequestResVo>> list(ActivityRequestReqVo activityRequestReqVo, int pageNo, int pageSize) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( activityRequestReqVo ));
        BossCommonResult<PageInfo<ActivityRequestResVo>> result;
        if ( null == activityRequestReqVo ) {
            return  new BossCommonResult<PageInfo<ActivityRequestResVo>>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            PageInfo<ActivityRequest> list = activityRequestDomain.list( BeanMapper.map( activityRequestReqVo , ActivityRequest.class ), pageNo , pageSize );
            PageInfo<ActivityRequestResVo> pageInfo = BeanMapper.map(list, PageInfo.class);
            pageInfo.setList(BeanMapper.mapList(list.getList(), ActivityRequestResVo.class));
            return new BossCommonResult<PageInfo<ActivityRequestResVo>>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), pageInfo);
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<PageInfo<ActivityRequestResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<PageInfo<ActivityRequestResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> insert(ActivityRequestReqVo activityRequestReqVo) {
        logger.info("----------保存 接口上送参数：" + JSONObject.toJSONString( activityRequestReqVo ));
        BossCommonResult<Integer> result;
        if ( null == activityRequestReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = activityRequestDomain.insert( BeanMapper.map( activityRequestReqVo , ActivityRequest.class) );
            return new BossCommonResult<Integer>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), count);
        } catch (BusinessException e) {
            logger.error("保存 运行时异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("保存 运系统异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------保存 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> update(ActivityRequestReqVo activityRequestReqVo) {
        logger.info("----------更新 接口上送参数：" + JSONObject.toJSONString( activityRequestReqVo ));
        BossCommonResult<Integer> result;
        if ( null == activityRequestReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = activityRequestDomain.update( BeanMapper.map( activityRequestReqVo , ActivityRequest.class ));
            return new BossCommonResult<Integer>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), count);
        } catch (BusinessException e) {
            logger.error("更新 运行时异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("更新 运系统异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------更新 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> delete(String id) {
        logger.info("----------删除 接口上送参数：" + JSONObject.toJSONString( id ));
        BossCommonResult<Integer> result;
        if (StringUtils.isBlank( id )) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = activityRequestDomain.delete( id );
            return new BossCommonResult<Integer>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), count );
        } catch (BusinessException e) {
            logger.error("删除 运行时异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("删除 运系统异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------删除 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Money> getSum(String activityId) {
        logger.info("----------根据项目编号查询活动奖池金额 接口上送参数：" + JSONObject.toJSONString( activityId ));
        BossCommonResult<Money> result;
        if (StringUtils.isBlank( activityId )) {
            return  new BossCommonResult<Money>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            Money amount = activityRequestDomain.getSum( activityId );
            return new BossCommonResult<Money>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), amount );
        } catch (BusinessException e) {
            logger.error("根据项目编号查询活动奖池金额 运行时异常 " , e);
            result = new BossCommonResult<Money>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("根据项目编号查询活动奖池金额 运系统异常 " , e);
            result = new BossCommonResult<Money>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------根据项目编号查询活动奖池金额 结束：" + JSONObject.toJSONString(result));
        return result;
    }
}



