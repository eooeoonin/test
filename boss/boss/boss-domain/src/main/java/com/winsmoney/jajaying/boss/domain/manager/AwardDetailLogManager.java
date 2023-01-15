/**
* Project:boss
* File:AwardDetailLog.java
* Date:2017-11-24
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.AwardDetailLogMapper;
import com.winsmoney.jajaying.boss.dao.po.AwardDetailLogPo;
import com.winsmoney.jajaying.boss.domain.model.AwardDetailLog;

/**
* Description: 奖品发放记录 管理器
* date: 2017-11-24 04:59:41
* @author: CodeCreator
*/
@Component
public class AwardDetailLogManager extends BaseManager<AwardDetailLog,AwardDetailLogMapper,AwardDetailLogPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardDetailLogManager.class);


}


