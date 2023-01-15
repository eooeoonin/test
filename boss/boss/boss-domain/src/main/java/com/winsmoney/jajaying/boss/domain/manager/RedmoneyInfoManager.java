/**
* Project:boss
* File:RedmoneyInfo.java
* Date:2016-10-09
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.RedmoneyInfoMapper;
import com.winsmoney.jajaying.boss.dao.po.RedmoneyInfoPo;
import com.winsmoney.jajaying.boss.domain.model.RedmoneyInfo;

/**
* Description:  管理器
* date: 2016-10-09 02:14:08
* @author: CodeCreator
*/
@Component
public class RedmoneyInfoManager extends BaseManager<RedmoneyInfo,RedmoneyInfoMapper,RedmoneyInfoPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(RedmoneyInfoManager.class);
   


}


