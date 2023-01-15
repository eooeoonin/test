/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.boss.domain.MenuDomain;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;
import com.winsmoney.jajaying.boss.service.request.MenuReqVo;
import com.winsmoney.jajaying.boss.service.response.MenuResVo;
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
 * ClassName: Menu Description: 服务接口测试 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class MenuServiceTest {

	@Autowired
	private IMenuService menuService;
	@Autowired
	private MenuDomain menuDomain;
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
		MenuReqVo menuReqVo = new MenuReqVo();
		menuReqVo.setParentId("111112");
		BossCommonResult<MenuResVo> menuList = menuService.getMenu(menuReqVo);
		System.out.println(menuList);
	}

	@Test
	public void testPageList() throws Exception {

	}

	@Test
	public void testCount() throws Exception {

	}

	@Test
	public void testInsert() throws Exception {
		MenuReqVo data = new MenuReqVo();
		data.setDisplayOrder(123);
		data.setLevel(123);
		data.setStyle("abc");
		data.setText("abc");
		data.setUrl("abc");
		data.setParentId("abc");

		BossCommonResult<Integer> result = menuService.insertMenu(data);
		System.out.println("添加Menu结果：" + result);
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}