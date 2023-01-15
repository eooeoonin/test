/**
* Project:boss
* File:Template.java
* Date:2017-09-04
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.TemplateReqVo;
import com.winsmoney.jajaying.boss.service.response.TemplateResVo;
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
* ClassName: Template
* Description: 模板表 服务接口测试
* date: 2017-09-04 04:11:05
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class TemplateServiceTest
{

	@Autowired
	private ITemplateService templateService;

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
		TemplateReqVo data = new TemplateReqVo();
		data.setCreatedBy("abc");
		data.setName("abc");
		data.setType("abc");
		data.setImgNum(123);
		data.setFileName("abc");

		BossCommonResult<Integer> result = templateService.insert(data);
    	System.out.println("添加Template结果："+result);
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