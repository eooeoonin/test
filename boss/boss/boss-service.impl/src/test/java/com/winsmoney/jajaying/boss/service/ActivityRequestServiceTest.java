/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.service.request.ActivityRequestReqVo;
import com.winsmoney.jajaying.boss.service.response.ActivityRequestResVo;
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
 * Description: 活动请求 服务接口
 * date: 2017-11-23 02:43:12
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)
public class ActivityRequestServiceTest
{
    @Autowired
    private IActivityRequestService activityRequestService;
    /**
     * 根据项目编号查询活动奖池金额
     */
    @Test
    public void  getSum( ){
        String activityId = "20171127L027105000000000000000022";
         BossCommonResult<Money> result = activityRequestService.getSum( activityId );
         System.out.println(JSONObject.toJSONString( result ));
        while (true){

        }
    }
}



