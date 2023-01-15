/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.StaffReqVo;
import com.winsmoney.jajaying.boss.service.response.StaffResVo;
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
 * ClassName: Staff Description: 服务接口测试 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class StaffServiceTest {

	@Autowired
	private IStaffService staffService;

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
		StaffReqVo data = new StaffReqVo();
		data.setDepartmentId("abc");
		data.setRoleId("abc");
		data.setUserId("abc");
		data.setPassword("abc");
		data.setStaffName("abc");
		data.setSex("abc");
		data.setRealName("abc");
		data.setEmail("abc");
		data.setMobile("abc");
		data.setPhone("abc");
		data.setAge(123);
		data.setBirthday("abc");
		data.setIdCard("abc");
		data.setQq("abc");
		data.setLowest(123);
		data.setHighest(123);
		data.setFirstTaskNo(123);
		data.setSecondTaskNo(123);
		data.setX509skeyId("abc");

		BossCommonResult<Integer> result = staffService.insertStaff(data);
		System.out.println("添加Staff结果：" + result);
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}