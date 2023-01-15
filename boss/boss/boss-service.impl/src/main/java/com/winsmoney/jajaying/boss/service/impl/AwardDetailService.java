/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.model.ActivityRequest;
import com.winsmoney.jajaying.boss.domain.model.AwardDetail;
import com.winsmoney.jajaying.boss.domain.AwardDetailDomain;
import com.winsmoney.jajaying.boss.service.IAwardDetailService;
import com.winsmoney.jajaying.boss.service.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.service.request.ActivityRequestReqVo;
import com.winsmoney.jajaying.boss.service.request.AwardDetailReqVo;
import com.winsmoney.jajaying.boss.service.response.AwardDetailResVo;
import com.winsmoney.jajaying.boss.service.response.AwardDetailResVo;

import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.platform.framework.log.LoggerLevel;
import com.winsmoney.platform.framework.log.annotation.DigestLogAnnotated;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Description: 奖品详情(抽奖号码) 服务实现
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
//@Service(value = "awardDetailService")
public class AwardDetailService implements IAwardDetailService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardDetailService.class);

    @Autowired
    private AwardDetailDomain awardDetailDomain;

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<AwardDetailResVo> getById(String id) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( id ));
        BossCommonResult<AwardDetailResVo> result;
        if (StringUtils.isBlank( id )) {
            return  new BossCommonResult<AwardDetailResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            AwardDetail awardDetail = awardDetailDomain.getById( id );
            return new BossCommonResult<AwardDetailResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( awardDetail , AwardDetailResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<AwardDetailResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<AwardDetailResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<AwardDetailResVo> get(AwardDetailReqVo awardDetailReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardDetailReqVo ));
        BossCommonResult<AwardDetailResVo> result;
        if ( null == awardDetailReqVo ) {
            return  new BossCommonResult<AwardDetailResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            AwardDetail awardDetail = awardDetailDomain.get( BeanMapper.map( awardDetailReqVo , AwardDetail.class ));
            return new BossCommonResult<AwardDetailResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( awardDetail , AwardDetailResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<AwardDetailResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<AwardDetailResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> count(AwardDetailReqVo awardDetailReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardDetailReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardDetailReqVo ) {
            return new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            Integer count = awardDetailDomain.count( BeanMapper.map( awardDetailReqVo , AwardDetail.class ));
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
    public BossCommonResult<PageInfo<AwardDetailResVo>> list(AwardDetailReqVo awardDetailReqVo, int pageNo, int pageSize) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardDetailReqVo ));
        BossCommonResult<PageInfo<AwardDetailResVo>> result;
        if ( null == awardDetailReqVo ) {
            return  new BossCommonResult<PageInfo<AwardDetailResVo>>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            PageInfo<AwardDetail> list = awardDetailDomain.list( BeanMapper.map( awardDetailReqVo , AwardDetail.class ), pageNo , pageSize );
            PageInfo<AwardDetailResVo> pageInfo = BeanMapper.map(list, PageInfo.class);
            pageInfo.setList(BeanMapper.mapList(list.getList(), AwardDetailResVo.class));
            return new BossCommonResult<PageInfo<AwardDetailResVo>>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), pageInfo);
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<PageInfo<AwardDetailResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<PageInfo<AwardDetailResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> insert(AwardDetailReqVo awardDetailReqVo) {
        logger.info("----------保存 接口上送参数：" + JSONObject.toJSONString( awardDetailReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardDetailReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = awardDetailDomain.insert( BeanMapper.map( awardDetailReqVo , AwardDetail.class) );
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
    public BossCommonResult<Integer> update(AwardDetailReqVo awardDetailReqVo) {
        logger.info("----------更新 接口上送参数：" + JSONObject.toJSONString( awardDetailReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardDetailReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = awardDetailDomain.update( BeanMapper.map( awardDetailReqVo , AwardDetail.class ));
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
            int count = awardDetailDomain.delete( id );
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
    public BossCommonResult<Integer> getCount(String activityId) {
        logger.info("----------已经下发抽奖号码 接口上送参数：" + JSONObject.toJSONString( activityId ));
        BossCommonResult<Integer> result;
        if (StringUtils.isBlank( activityId )) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = awardDetailDomain.getCount( activityId );
            return new BossCommonResult<Integer>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), count );
        } catch (BusinessException e) {
            logger.error("已经下发抽奖号码 运行时异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("已经下发抽奖号码 运系统异常 " , e);
            result = new BossCommonResult<Integer>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------已经下发抽奖号码 结束：" + JSONObject.toJSONString(result));
        return result;
    }
}



