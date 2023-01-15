package com.winsmoney.jajaying.boss.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.winsmoney.jajaying.trade.service.IAdjustmentRecordService;
import com.winsmoney.jajaying.trade.service.request.AdjustmentRecordReqVo;
import com.winsmoney.jajaying.trade.service.response.AdjustmentRecordResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;

@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class AccountAdjustmentServiceTest {
	@Autowired
	private IAdjustmentRecordService adjustmentRecordService;
	@Test
	public void aa() {
		AdjustmentRecordReqVo adjustmentRecordReqVo = new AdjustmentRecordReqVo();
		TradeCommonResult<AdjustmentRecordResVo> accountData = adjustmentRecordService.transferAccount(adjustmentRecordReqVo);
		System.out.println(JSON.toJSONString(accountData.getBusinessObject()));
	}
	
}
