///**
//* Project:boss
//* File:AwardListLog.java
//* Date:2018-03-08
//* Copyright (c) 2018 jajaying.com All Rights Reserved.
//*/
//package com.winsmoney.jajaying.boss.service;
//
//import com.winsmoney.jajaying.boss.service.request.AwardListLogReqVo;
//import com.winsmoney.jajaying.boss.service.response.AwardListLogResVo;
//import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
//
//import java.util.Date;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
///**
//* ClassName: AwardListLog
//* Description: 奖励记录 服务接口测试
//* date: 2018-03-08 03:06:17
//* @author: CodeCreator
//*/
//@ContextConfiguration("classpath:spring/boss-bean.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
//public class AwardListLogServiceTest
//{
//
//	@Autowired
//	private IAwardListLogService awardListLogService;
//
//	@Test
//	public void exeTest()
//	{
//
//	}
//
//	@Test
//	public void testGetById() throws Exception
//	{
//		BossCommonResult<AwardListLogResVo> result = awardListLogService.getById("1");
//    	System.out.println("result:"+result);
//	}
//
//	@Test
//	public void testGet() throws Exception
//	{
//		AwardListLogReqVo request = new AwardListLogReqVo();
//    	request.setId("1");
//		BossCommonResult<AwardListLogResVo> result = awardListLogService.get(request);
//        System.out.println("result:"+result);
//	}
//
//	@Test
//	public void testList() throws Exception
//	{
//
//	}
//
//	@Test
//	public void testPageList() throws Exception
//	{
//
//	}
//
//	@Test
//	public void testCount() throws Exception
//	{
//
//	}
//
//	@Test
//	public void testInsert() throws Exception
//	{
//		AwardListLogReqVo data = new AwardListLogReqVo();
//		data.setAwardProjectId("abc");
//		data.setAwardProject("abc");
//		data.setGrantType("abc");
//		data.setAwardAmount(123);
//		data.setCreatedBy("abc");
//
//		BossCommonResult<Integer> result = awardListLogService.insert(data);
//    	System.out.println("添加AwardListLog结果："+result);
//	}
//
//	@Test
//	public void testUpdate() throws Exception
//	{
//
//	}
//
//	@Test
//	public void testDel() throws Exception
//	{
//
//	}
//
//}