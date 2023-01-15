///**
// * Project:boss
// * File:AwardListLog.java
// * Date:2018-03-08
// * Copyright (c) 2018 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import com.winsmoney.jajaying.boss.domain.model.AwardListLog;
//import com.winsmoney.jajaying.boss.domain.AwardListLogDomain;
//import com.winsmoney.jajaying.boss.service.IAwardListLogService;
//import com.winsmoney.jajaying.boss.service.request.AwardListLogReqVo;
//import com.winsmoney.jajaying.boss.service.response.AwardListLogResVo;
//
//import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
//import com.winsmoney.platform.framework.core.util.BeanMapper;
//import com.winsmoney.framework.pagehelper.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
//* Description: 奖励记录 服务实现
//* date: 2018-03-08 03:06:17
//* @author: CodeCreator
//*/
//@Service(value = "awardListLogService")
//public class AwardListLogService extends CommonService<AwardListLogReqVo,AwardListLogResVo> implements IAwardListLogService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardListLogService.class);
//
//    @Autowired
//    private AwardListLogDomain awardListLogDomain;
//
//    @Override
//    protected AwardListLogResVo innerGetById(String id)
//    {
//        AwardListLog awardListLog = awardListLogDomain.getById(id);
//        return BeanMapper.map(awardListLog,AwardListLogResVo.class);
//    }
//
//    @Override
//    protected AwardListLogResVo innerGet(AwardListLogReqVo awardListLogReqVo)
//    {
//        AwardListLog condition = BeanMapper.map(awardListLogReqVo,AwardListLog.class);
//        AwardListLog awardListLog = awardListLogDomain.get(condition);
//        return BeanMapper.map(awardListLog,AwardListLogResVo.class);
//    }
//
//    @Override
//    protected Integer innerCount(AwardListLogReqVo awardListLogReqVo)
//    {
//        AwardListLog condition = BeanMapper.map(awardListLogReqVo,AwardListLog.class);
//        Integer result = awardListLogDomain.count(condition);
//        return result;
//    }
//
//    @Override
//    protected PageInfo<AwardListLogResVo> innerList(AwardListLogReqVo awardListLogReqVo, int pageNo, int pageSize)
//    {
//        AwardListLog condition = BeanMapper.map(awardListLogReqVo, AwardListLog.class);
//        PageInfo<AwardListLog> list = awardListLogDomain.list(condition, pageNo, pageSize);
//        PageInfo<AwardListLogResVo> result = BeanMapper.map(list, PageInfo.class);
//        result.setList(BeanMapper.mapList(list.getList(), AwardListLogResVo.class));
//        return result;
//    }
//
//    @Override
//    protected Integer innerInsert(AwardListLogReqVo awardListLogReqVo)
//    {
//        AwardListLog value = BeanMapper.map(awardListLogReqVo, AwardListLog.class);
//        Integer result = awardListLogDomain.insert(value);
//        return result;
//    }
//
//    @Override
//    protected Integer innerUpdate(AwardListLogReqVo awardListLogReqVo)
//    {
//        AwardListLog value = BeanMapper.map(awardListLogReqVo, AwardListLog.class);
//        Integer result = awardListLogDomain.update(value);
//        return result;
//    }
//
//    @Override
//    protected Integer innerDelete(String id)
//    {
//        Integer result = awardListLogDomain.delete(id);
//        return result;
//    }
//}
//
//
//
