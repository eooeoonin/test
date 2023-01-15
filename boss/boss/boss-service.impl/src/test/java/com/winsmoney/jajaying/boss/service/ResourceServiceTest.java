/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.domain.ResourceDomain;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.jajaying.boss.service.request.ResourceReqVo;
import com.winsmoney.jajaying.boss.service.response.ResourceResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: Resource Description: 服务接口测试 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class ResourceServiceTest {

	@Autowired
	private IResourceService resourceService;
	@Autowired
	private ResourceDomain resourceDomain;

	@Test
	public void exeTest() {

	}

	@Test
	public void testGetById() throws Exception {

	}

	@Test
	public void testGet() throws Exception {
			String roleId = "1";
			List<Resource> resources = resourceDomain.getPermissonFlagByRoleId(roleId);
			System.out.println(resources);
	}

	@Test
	public void testList() throws Exception {

	}

	@Test
	public void testPageList() throws Exception {

	}

	@Test
	public void testCount() throws Exception {

	}

	@Test
	public void testInsert() throws Exception {
		ResourceReqVo data = new ResourceReqVo();
		data.setText("abc");
		data.setRoleId("abc");
		data.setUrl("abc");
		data.setRemark("abc");

		BossCommonResult<Integer> result = resourceService.insertResource(data);
		System.out.println("添加Resource结果：" + result);
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}