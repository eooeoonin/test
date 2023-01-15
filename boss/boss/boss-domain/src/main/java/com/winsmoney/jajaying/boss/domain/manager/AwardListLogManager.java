/**
* Project:boss
* File:AwardListLog.java
* Date:2018-03-08
* Copyright (c) 2018 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.AwardListLogMapper;
import com.winsmoney.jajaying.boss.dao.po.AwardListLogPo;
import com.winsmoney.jajaying.boss.domain.model.AwardListLog;

/**
* Description: 奖励记录 管理器
* date: 2018-03-08 03:06:17
* @author: CodeCreator
*/
@Component
public class AwardListLogManager extends BaseManager<AwardListLog,AwardListLogMapper,AwardListLogPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardListLogManager.class);


}


