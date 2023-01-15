/**
* Project:boss
* File:Channel.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.ChannelReqVo;
import com.winsmoney.jajaying.boss.service.response.ChannelResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
* ClassName: Channel
* Description: 渠道表 服务接口测试
* date: 2017-08-31 05:12:49
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class ChannelServiceTest
{

	@Autowired
	private IChannelService channelService;

	@Test
	public void exeTest()
	{

	}

	@Test
	public void testGetById() throws Exception
	{

	}

	@Test
	public void testGet() throws Exception
	{

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
		ChannelReqVo data = new ChannelReqVo();
		data.setCreatedBy("abc");
		data.setCode("abc");
		data.setName("abc");


		BossCommonResult<Integer> result = channelService.insert(data);
    	System.out.println("添加Channel结果："+result);
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