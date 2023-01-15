/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.service.request.DepartmentReqVo;
import com.winsmoney.jajaying.boss.service.response.DepartmentResVo;
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
 * ClassName: Department Description: 服务接口测试 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class DepartmentServiceTest {

	@Autowired
	private IDepartmentService departmentService;

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
		DepartmentReqVo data = new DepartmentReqVo();
		data.setDepartmentType("abc");
		data.setParentDepartmentId("abc");
		data.setIsInherit("abc");
		data.setDepartmentName("abc");
		data.setDepartmentCode("abc");

		BossCommonResult<Integer> result = departmentService.insertDepartment(data);
		System.out.println("添加Department结果：" + result);
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}