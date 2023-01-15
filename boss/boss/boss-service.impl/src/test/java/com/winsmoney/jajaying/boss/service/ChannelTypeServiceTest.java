/**
* Project:boss
* File:ChannelType.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.ChannelTypeReqVo;
import com.winsmoney.jajaying.boss.service.response.ChannelTypeResVo;
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
* ClassName: ChannelType
* Description: 渠道类型 服务接口测试
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class ChannelTypeServiceTest
{

	@Autowired
	private IChannelTypeService channelTypeService;

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
		ChannelTypeReqVo data = new ChannelTypeReqVo();
		data.setName("abc");
		data.setCreatedBy("abc");

		BossCommonResult<Integer> result = channelTypeService.insert(data);
    	System.out.println("添加ChannelType结果："+result);
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