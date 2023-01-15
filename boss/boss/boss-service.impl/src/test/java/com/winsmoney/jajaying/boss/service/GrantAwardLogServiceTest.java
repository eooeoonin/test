/**
* Project:boss
* File:GrantAwardLog.java
* Date:2018-03-08
* Copyright (c) 2018 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.GrantAwardLogReqVo;
import com.winsmoney.jajaying.boss.service.response.GrantAwardLogResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import java.util.Date;

import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
* ClassName: GrantAwardLog
* Description:  服务接口测试
* date: 2018-03-08 09:49:50
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class GrantAwardLogServiceTest
{

	@Autowired
	private IGrantAwardLogService grantAwardLogService;

	@Test
	public void exeTest()
	{

	}

	@Test
	public void testGetById() throws Exception
	{
		TradeCommonResult<GrantAwardLogResVo> result = grantAwardLogService.getById("1");
    	System.out.println("result:"+result);
	}

	@Test
	public void testGet() throws Exception
	{
		GrantAwardLogReqVo request = new GrantAwardLogReqVo();
    	request.setId("1");
		TradeCommonResult<GrantAwardLogResVo> result = grantAwardLogService.get(request);
        System.out.println("result:"+result);
	}

	@Test
	public void testList() throws Exception
	{

	}

	@Test
	public void testPageList() throws Exception
	{

	}

	@Test
	public void testCount() throws Exception
	{

	}

	@Test
	public void testInsert() throws Exception
	{
		GrantAwardLogReqVo data = new GrantAwardLogReqVo();
		data.setUserId("abc");
		data.setProjectId("abc");
		data.setAwardPoolId("abc");
		data.setAwardPoolNum(123);
		data.setCreatedBy("abc");

		TradeCommonResult<GrantAwardLogResVo> result = grantAwardLogService.insert(data);
    	System.out.println("添加GrantAwardLog结果："+result);
	}

	@Test
	public void testUpdate() throws Exception
	{

	}

	@Test
	public void testDel() throws Exception
	{

	}

}