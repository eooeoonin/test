/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.service.request.AwardDetailReqVo;
import com.winsmoney.jajaying.boss.service.response.AwardDetailResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.platform.framework.core.util.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description: 奖品详情(抽奖号码) 服务接口
 * date: 2017-11-23 02:43:12
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class AwardDetailServiceTest
{
    @Autowired
    private IAwardDetailService awardDetailService;

    @Test
    public void getCount(){
//        String activityId = "20171127L02710500000000000000002";
        String activityId = "20171127L02710500000000000000003";
        BossCommonResult<Integer> result =  awardDetailService.getCount( activityId );
        System.out.println(JSONObject.toJSONString( result ));
        while (true){

        }
    }
}



