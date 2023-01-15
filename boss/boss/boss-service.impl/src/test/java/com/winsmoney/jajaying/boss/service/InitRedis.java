package com.winsmoney.jajaying.boss.service;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.winsmoney.jajaying.boss.domain.utils.GenerateRedisKeyUtils;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.redis.map.MapManager;

@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class InitRedis {
	

	@Autowired
	private MapManager mapManager;
	
	@Test
	public void init() {
		//initSMS();
		getSMS();
	}
	
	private void initSMS() {
		String content = "这是短信内容222";
		String dateStr = DateUtil.formatToYYYYMMDDSlash(new Date());
		mapManager.set("P08:smsbatchCount:"+dateStr, content);
	}
	
	private void getSMS(){
		String dateStr = DateUtil.formatToYYYYMMDDSlash(new Date());
		String smsContent = mapManager.get("P08:smsbatchCount:"+dateStr, String.class);
		System.out.println(smsContent);
	}
}
