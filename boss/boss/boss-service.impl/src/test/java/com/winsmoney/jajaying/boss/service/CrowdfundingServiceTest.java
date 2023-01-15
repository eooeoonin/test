/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.winsmoney.jajaying.crowdfunding.service.IProductInfoService;
import com.winsmoney.jajaying.crowdfunding.service.request.ProductInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.ProductInfoResVo;

/**
 * ClassName: Menu Description: 服务接口测试 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class CrowdfundingServiceTest {

	@Autowired
	private IProductInfoService productInfoService;
	
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
		ProductInfoReqVo productInfoReqVo = new ProductInfoReqVo();
		CrowdfundingCommonResult<PageInfo<ProductInfoResVo>> selectProductInfoPageList = productInfoService.selectProductInfoPageList(productInfoReqVo, 1, 1000);
		System.out.println(selectProductInfoPageList);
	}

	@Test
	public void testPageList() throws Exception {

	}

	@Test
	public void testCount() throws Exception {

	}

	@Test
	public void testInsert() throws Exception {
		
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testDel() throws Exception {

	}

}