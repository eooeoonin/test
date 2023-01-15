/**
* Project:boss
* File:Activity.java
* Date:2017-09-04
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.Date;
import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.ActivityMapper;
import com.winsmoney.jajaying.boss.dao.po.ActivityPo;
import com.winsmoney.jajaying.boss.domain.model.Activity;

/**
* Description: 活动表 管理器
* date: 2017-09-04 05:21:24
* @author: CodeCreator
*/
@Component
public class ActivityManager extends BaseManager<Activity,ActivityMapper,ActivityPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityManager.class);


    public List<Activity> activityList(  Date tradeTime  ) {
        return BeanMapper.mapList( this.getSlaveMapper().activityList( tradeTime ) , Activity.class );
    }
}


