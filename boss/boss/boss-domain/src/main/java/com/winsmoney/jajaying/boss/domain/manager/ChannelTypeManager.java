/**
* Project:boss
* File:ChannelType.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.ChannelTypeMapper;
import com.winsmoney.jajaying.boss.dao.po.ChannelTypePo;
import com.winsmoney.jajaying.boss.domain.model.ChannelType;

/**
* Description: 渠道类型 管理器
* date: 2017-08-31 05:16:25
* @author: CodeCreator
*/
@Component
public class ChannelTypeManager extends BaseManager<ChannelType,ChannelTypeMapper,ChannelTypePo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelTypeManager.class);


}


