package com.winsmoney.jajaying.boss.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.winsmoney.jajaying.boss.domain.job.TriggerSendSmsJob;

/**
 * Created by ChenKai on 2016/12/9.
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class TestTrigger {

	@Autowired
	private TriggerSendSmsJob triggerSendSmsJob;
	
	@Test
	public void testTrigger(){
//		triggerSendSmsJob.trigger("好好好");
		
	}
}
