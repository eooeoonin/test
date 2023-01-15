/**
* Project:boss
* File:Link.java
* Date:2017-08-31
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.LinkMapper;
import com.winsmoney.jajaying.boss.dao.po.LinkPo;
import com.winsmoney.jajaying.boss.dao.po.MenuPo;
import com.winsmoney.jajaying.boss.domain.model.Link;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;

/**
* Description: 链接表 管理器
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
@Component
public class LinkManager extends BaseManager<Link,LinkMapper,LinkPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(LinkManager.class);

	public int deleteByAcode(String aCode) {
		return this.getSlaveMapper().deleteByAcode(aCode);
	}


}


