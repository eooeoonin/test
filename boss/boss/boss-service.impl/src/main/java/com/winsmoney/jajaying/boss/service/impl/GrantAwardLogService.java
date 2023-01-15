///**
// * Project:boss
// * File:GrantAwardLog.java
// * Date:2018-03-08
// * Copyright (c) 2018 jajaying.com All Rights Reserved.
// */
//package com.winsmoney.jajaying.boss.service.impl;
//
//import com.winsmoney.jajaying.boss.domain.model.GrantAwardLog;
//import com.winsmoney.jajaying.boss.domain.GrantAwardLogDomain;
//import com.winsmoney.jajaying.boss.service.IGrantAwardLogService;
//import com.winsmoney.jajaying.boss.service.request.GrantAwardLogReqVo;
//import com.winsmoney.jajaying.boss.service.response.GrantAwardLogResVo;
//
//import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
//import com.winsmoney.platform.framework.core.util.BeanMapper;
//import com.winsmoney.framework.pagehelper.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
//* Description:  服务实现
//* date: 2018-03-08 09:49:50
//* @author: CodeCreator
//*/
//@Service(value = "grantAwardLogService")
//public class GrantAwardLogService extends CommonService<GrantAwardLogReqVo,GrantAwardLogResVo> implements IGrantAwardLogService
//{
//    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(GrantAwardLogService.class);
//
//    @Autowired
//    private GrantAwardLogDomain grantAwardLogDomain;
//
//    @Override
//    protected GrantAwardLogResVo innerGetById(String id)
//    {
//        GrantAwardLog grantAwardLog = grantAwardLogDomain.getById(id);
//        return BeanMapper.map(grantAwardLog,GrantAwardLogResVo.class);
//    }
//
//    @Override
//    protected GrantAwardLogResVo innerGet(GrantAwardLogReqVo grantAwardLogReqVo)
//    {
//        GrantAwardLog condition = BeanMapper.map(grantAwardLogReqVo,GrantAwardLog.class);
//        GrantAwardLog grantAwardLog = grantAwardLogDomain.get(condition);
//        return BeanMapper.map(grantAwardLog,GrantAwardLogResVo.class);
//    }
//
//    @Override
//    protected Integer innerCount(GrantAwardLogReqVo grantAwardLogReqVo)
//    {
//        GrantAwardLog condition = BeanMapper.map(grantAwardLogReqVo,GrantAwardLog.class);
//        Integer result = grantAwardLogDomain.count(condition);
//        return result;
//    }
//
//    @Override
//    protected PageInfo<GrantAwardLogResVo> innerList(GrantAwardLogReqVo grantAwardLogReqVo, int pageNo, int pageSize)
//    {
//        GrantAwardLog condition = BeanMapper.map(grantAwardLogReqVo, GrantAwardLog.class);
//        PageInfo<GrantAwardLog> list = grantAwardLogDomain.list(condition, pageNo, pageSize);
//        PageInfo<GrantAwardLogResVo> result = BeanMapper.map(list, PageInfo.class);
//        result.setList(BeanMapper.mapList(list.getList(), GrantAwardLogResVo.class));
//        return result;
//    }
//
//    @Override
//    protected Integer innerInsert(GrantAwardLogReqVo grantAwardLogReqVo)
//    {
//        GrantAwardLog value = BeanMapper.map(grantAwardLogReqVo, GrantAwardLog.class);
//        Integer result = grantAwardLogDomain.insert(value);
//        return result;
//    }
//
//    @Override
//    protected Integer innerUpdate(GrantAwardLogReqVo grantAwardLogReqVo)
//    {
//        GrantAwardLog value = BeanMapper.map(grantAwardLogReqVo, GrantAwardLog.class);
//        Integer result = grantAwardLogDomain.update(value);
//        return result;
//    }
//
//    @Override
//    protected Integer innerDelete(String id)
//    {
//        Integer result = grantAwardLogDomain.delete(id);
//        return result;
//    }
//}
//
//
//
