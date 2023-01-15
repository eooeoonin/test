/**
* Project:boss
* File:ActivityRequest.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.math.BigDecimal;
import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.Money;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.ActivityRequestMapper;
import com.winsmoney.jajaying.boss.dao.po.ActivityRequestPo;
import com.winsmoney.jajaying.boss.domain.model.ActivityRequest;

/**
* Description: 活动请求 管理器
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
@Component
public class ActivityRequestManager extends BaseManager<ActivityRequest,ActivityRequestMapper,ActivityRequestPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityRequestManager.class);


    public BigDecimal getSum(String activityId) {
        return this.getSlaveMapper().getSum( activityId );
    }
}


