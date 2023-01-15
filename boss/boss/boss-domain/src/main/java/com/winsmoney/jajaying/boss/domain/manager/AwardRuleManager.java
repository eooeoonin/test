/**
* Project:boss
* File:AwardRule.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.AwardRuleMapper;
import com.winsmoney.jajaying.boss.dao.po.AwardRulePo;
import com.winsmoney.jajaying.boss.domain.model.AwardRule;

/**
* Description: 活动表扩展表设置(集采类) 管理器
* date: 2017-11-23 10:02:27
* @author: CodeCreator
*/
@Component
public class AwardRuleManager extends BaseManager<AwardRule,AwardRuleMapper,AwardRulePo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardRuleManager.class);


}


