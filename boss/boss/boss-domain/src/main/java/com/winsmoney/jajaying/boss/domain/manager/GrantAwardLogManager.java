/**
* Project:boss
* File:GrantAwardLog.java
* Date:2018-03-08
* Copyright (c) 2018 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.GrantAwardLogMapper;
import com.winsmoney.jajaying.boss.dao.po.GrantAwardLogPo;
import com.winsmoney.jajaying.boss.domain.model.GrantAwardLog;

/**
* Description:  管理器
* date: 2018-03-08 09:49:50
* @author: CodeCreator
*/
@Component
public class GrantAwardLogManager extends BaseManager<GrantAwardLog,GrantAwardLogMapper,GrantAwardLogPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(GrantAwardLogManager.class);


}


