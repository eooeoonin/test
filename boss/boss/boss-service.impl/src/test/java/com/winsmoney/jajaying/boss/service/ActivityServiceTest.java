/**
* Project:boss
* File:Activity.java
* Date:2017-09-04
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.domain.ActivityDomain;
import com.winsmoney.jajaying.boss.domain.model.Activity;
import com.winsmoney.jajaying.boss.service.request.ActivityReqVo;
import com.winsmoney.jajaying.boss.service.response.ActivityResVo;
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
* ClassName: Activity
* Description: 活动表 服务接口测试
* date: 2017-09-04 05:21:24
* @author: CodeCreator
*/
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class ActivityServiceTest
{

	@Autowired
	private ActivityDomain activityDomain;
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
		Activity data = new Activity();
		data.setCreatedBy("sss");
		data.setName("abc");
		data.setBody("abc");
		data.setDesc("abc");
		data.setStartTime(new Date());
		data.setEndTime(new Date());
		data.setTemplateId("abc");
		data.setPic1("abc");
		data.setPic2("abc");
		data.setPic3("abc");
		data.setPic4("abc");
		data.setPic5("abc");
		data.setRule1("333");
		data.setRule2("333");
		data.setRule3("333");
		data.setWxName("abc");
		data.setWxDesc("333");
		data.setWxPic("abc");

		int result = activityDomain.insert(data);
    	System.out.println("添加Activity结果："+data.getId());
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