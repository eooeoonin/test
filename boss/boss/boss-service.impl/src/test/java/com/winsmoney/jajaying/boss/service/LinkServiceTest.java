/**
* Project:boss
* File:Link.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.LinkReqVo;
import com.winsmoney.jajaying.boss.service.response.LinkResVo;
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
* ClassName: Link
* Description: 链接表 服务接口测试
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class LinkServiceTest
{

	@Autowired
	private ILinkService linkService;

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
		LinkReqVo data = new LinkReqVo();
		data.setCreatedBy("abc");
		data.setLinkName("abc");
		data.setChannelName("abc");
		data.setChannelCode("abc");
		data.setActivityName("abc");
		data.setAActivityCode("abc");

		BossCommonResult<Integer> result = linkService.insert(data);
    	System.out.println("添加Link结果："+result);
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