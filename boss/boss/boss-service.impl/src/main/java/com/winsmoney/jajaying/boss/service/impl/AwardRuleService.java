/**
 * Project:boss
 * File:AwardRule.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.model.AwardRule;
import com.winsmoney.jajaying.boss.domain.AwardRuleDomain;
import com.winsmoney.jajaying.boss.service.IAwardRuleService;
import com.winsmoney.jajaying.boss.service.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.service.request.AwardRuleReqVo;
import com.winsmoney.jajaying.boss.service.response.AwardRuleResVo;

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
* Description: 活动表扩展表设置(集采类) 服务实现
* date: 2017-11-23 10:02:27
* @author: CodeCreator
*/
//@Service(value = "awardRuleService")
public class AwardRuleService implements IAwardRuleService
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardRuleService.class);

    @Autowired
    private AwardRuleDomain awardRuleDomain;

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<AwardRuleResVo> getById(String id) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( id ));
        BossCommonResult<AwardRuleResVo> result;
        if (StringUtils.isBlank( id )) {
            return  new BossCommonResult<AwardRuleResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            AwardRule awardRule = awardRuleDomain.getById( id );
            return new BossCommonResult<AwardRuleResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( awardRule , AwardRuleResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<AwardRuleResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<AwardRuleResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<AwardRuleResVo> get(AwardRuleReqVo awardRuleReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardRuleReqVo ));
        BossCommonResult<AwardRuleResVo> result;
        if ( null == awardRuleReqVo ) {
            return  new BossCommonResult<AwardRuleResVo>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            AwardRule awardRule = awardRuleDomain.get( BeanMapper.map( awardRuleReqVo , AwardRule.class ));
            return new BossCommonResult<AwardRuleResVo>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), BeanMapper.map( awardRule , AwardRuleResVo.class));
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<AwardRuleResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<AwardRuleResVo>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> count(AwardRuleReqVo awardRuleReqVo) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardRuleReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardRuleReqVo ) {
            return new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            Integer count = awardRuleDomain.count( BeanMapper.map( awardRuleReqVo , AwardRule.class ));
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
    public BossCommonResult<PageInfo<AwardRuleResVo>> list(AwardRuleReqVo awardRuleReqVo, int pageNo, int pageSize) {
        logger.info("----------查询 接口上送参数：" + JSONObject.toJSONString( awardRuleReqVo ));
        BossCommonResult<PageInfo<AwardRuleResVo>> result;
        if ( null == awardRuleReqVo ) {
            return  new BossCommonResult<PageInfo<AwardRuleResVo>>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            PageInfo<AwardRule> list = awardRuleDomain.list( BeanMapper.map( awardRuleReqVo , AwardRule.class ), pageNo , pageSize );
            PageInfo<AwardRuleResVo> pageInfo = BeanMapper.map(list, PageInfo.class);
            pageInfo.setList(BeanMapper.mapList(list.getList(), AwardRuleResVo.class));
            return new BossCommonResult<PageInfo<AwardRuleResVo>>(BossErrorCode.SUCCESS.getCode(), BossErrorCode.SUCCESS.getDesc(), pageInfo);
        } catch (BusinessException e) {
            logger.error("查询 运行时异常 " , e);
            result = new BossCommonResult<PageInfo<AwardRuleResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        } catch (Exception e) {
            logger.error("查询 运系统异常 " , e);
            result = new BossCommonResult<PageInfo<AwardRuleResVo>>( BossErrorCode.ERROR.getCode(),BossErrorCode.ERROR.getDesc(),null );
        }
        logger.info("----------查询 结束：" + JSONObject.toJSONString(result));
        return result;
    }

    @Override
    @DigestLogAnnotated(digestIdentificationCode = "0001", logFileName = "DEFAULT.DIG", loggerLevel = LoggerLevel.INFO)
    public BossCommonResult<Integer> insert(AwardRuleReqVo awardRuleReqVo) {
        logger.info("----------保存 接口上送参数：" + JSONObject.toJSONString( awardRuleReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardRuleReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = awardRuleDomain.insert( BeanMapper.map( awardRuleReqVo , AwardRule.class) );
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
    public BossCommonResult<Integer> update(AwardRuleReqVo awardRuleReqVo) {
        logger.info("----------更新 接口上送参数：" + JSONObject.toJSONString( awardRuleReqVo ));
        BossCommonResult<Integer> result;
        if ( null == awardRuleReqVo ) {
            return  new BossCommonResult<Integer>( BossErrorCode.PARAM_ERROR.getCode(),BossErrorCode.PARAM_ERROR.getDesc(),null );
        }
        try {
            int count = awardRuleDomain.update( BeanMapper.map( awardRuleReqVo , AwardRule.class ));
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
            int count = awardRuleDomain.delete( id );
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
}



