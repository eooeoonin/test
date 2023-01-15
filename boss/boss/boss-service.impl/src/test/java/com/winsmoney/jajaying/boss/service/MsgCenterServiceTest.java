/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.msgcenter.service.IMessageService;
import com.winsmoney.jajaying.msgcenter.service.ISmsAgencyService;
import com.winsmoney.jajaying.msgcenter.service.enums.MessageType;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.ReceiverReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.SmsAgencyReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.SmsAgencyResVo;

/**
 * ClassName: UserRole Description: 服务接口测试 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class MsgCenterServiceTest {

	@Autowired
	private IMessageService messageService;
	@Autowired
	private ISmsAgencyService smsAgencyService;

	@Scope
	@Test
	public void sendSmsBatchTest() {
		long start = System.currentTimeMillis();
		MessageReqVo messageReqVo = new MessageReqVo();
		messageReqVo.setMerchant("P2P");
		messageReqVo.setType(MessageType.SMS);
		messageReqVo.setContent("元旦快乐！");
		List<ReceiverReqVo> receiveres = new ArrayList<ReceiverReqVo>();
		String mobiles = "15321850720";
//		String mobiles = "18600165064|18696488250|18101318101|13691077567|18511366821|18601212488|13126859991|15210549947|18401269595|17301218635|18210292773|18612113650|13910523388|15810240314|18610280990|18610280990|13811740408|15901096773|18311177618|13811711001|13520928158|13426347623|18610783963|18627324595|18301686722|15301375287|15110160033|15624974609|13520582447|15810015853|18210198561|13810550521|18600661216|18810719619|18612295380|13611273013|13910971532|13811207585|18500818804|18611882795|15727380987|18515216359|13810539754|13810549328|18710056659|18610122058|13811493610|13683108038|18611920559|13301289096|18410130710|17778196749|15010955136|15210080596|15321850720|13521077623|18523629134|18611912314|18911022580|18923149858|17736008903|15701574049|13623946735|15831982704|15848588550|17701340992|15230100510|18833058583|15010955282|13472179424|18033889956|13512841806|15101032472|15127087669|18631515828|13102888701|13001156213|13693088089|15711329537|18811332784|18811750043|13359181912|15175173657|18310297668|18722552415|18911739222|13116184796|13652115256|15910626292|18655555500|15122113253|15822869077|15522579739|15122523313|13803003868|13102988281|18904755779|15655290712|13821807901|18611100020|18622858277|13752617521|18686672912|13466634201|13773918058|15620187030|13702159440|15620907978";
		String[] split = mobiles.split("\\|");
		for (String s : split) {
			ReceiverReqVo receiverReqVo = new ReceiverReqVo();
			receiverReqVo.setCellphone(s);
			receiveres.add(receiverReqVo); 
		}
		messageReqVo.setReceiveres(receiveres);
		long dubboStart = System.currentTimeMillis();
//		MessageCommonResult<MessageResVo> sendMsgForBoss = messageService.sendMsgForBoss(messageReqVo);
		long end = System.currentTimeMillis();
		System.out.println(">>>>>>>>>整个接口耗时" + (end-start) + " start=" + start + " end=" + end);
		System.out.println(">>>>>>>>>dubbo接口耗时" + (end-dubboStart) + " dubboStart=" + dubboStart + " end=" + end);
//		System.out.println(sendMsgForBoss);
		
	}


	@Test
	public void testMaxPhones(){
		int result = 100;
		SmsAgencyReqVo condition = new SmsAgencyReqVo();
		condition.setOpen(1);
		MessageCommonResult<PageInfo<SmsAgencyResVo>> agencys = smsAgencyService.list(condition, 1, 10);
		if(agencys.isSuccess()){
			List<SmsAgencyResVo> list = agencys.getBusinessObject().getList();
			if(null != list && list.size() >=1){
				SmsAgencyResVo smsAgencyResVo = list.get(0);
				result = smsAgencyResVo.getMaxPhones();
			}else throw new RuntimeException("取得短信服务商所能发送最大的条数异常");
		}
		System.out.println(result);
	}
}