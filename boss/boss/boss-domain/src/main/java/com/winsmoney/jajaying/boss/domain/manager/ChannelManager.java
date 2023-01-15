/**
* Project:boss
* File:Channel.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.ChannelMapper;
import com.winsmoney.jajaying.boss.dao.po.ChannelPo;
import com.winsmoney.jajaying.boss.domain.model.Channel;

/**
* Description: 渠道表 管理器
* date: 2017-08-31 05:12:49
* @author: CodeCreator
*/
@Component
public class ChannelManager extends BaseManager<Channel,ChannelMapper,ChannelPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ChannelManager.class);


}


