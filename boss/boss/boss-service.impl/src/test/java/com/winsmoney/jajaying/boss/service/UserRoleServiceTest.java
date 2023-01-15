/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.UserRoleReqVo;
import com.winsmoney.jajaying.boss.service.response.UserRoleResVo;
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
 * ClassName: UserRole Description: 服务接口测试 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class UserRoleServiceTest {

	@Autowired
	private IUserRoleService userRoleService;

	@Test
	public void exeTest() {

	}

	@Test
	public void testGetById() throws Exception {

	}

	@Test
	public void testGet() throws Exception {

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
		UserRoleReqVo data = new UserRoleReqVo();
		data.setExtendRole("abc");
		data.setRoleName("abc");
		data.setRoleType("abc");
		data.setRemark("abc");

		BossCommonResult<Integer> result = userRoleService.insertUserRole(data);
		System.out.println("添加UserRole结果：" + result);
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}