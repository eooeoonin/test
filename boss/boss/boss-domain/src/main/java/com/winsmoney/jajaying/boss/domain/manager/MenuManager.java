/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.MenuMapper;
import com.winsmoney.jajaying.boss.dao.po.MenuPo;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;

/**
 * Description: 管理器 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@Component
public class MenuManager extends BaseManager<Menu, MenuMapper, MenuPo> {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(MenuManager.class);

	public List<MenuBo> findAllOneLevelMenus() {
		List<MenuPo> menus = this.getSlaveMapper().findAllOneLevelMenus();
		List<MenuBo> mapList = BeanMapper.mapList(menus, MenuBo.class);
		return mapList;
	}

	public List<MenuBo> findChildMenusByParentId(String parentId) {
		List<MenuPo> childMenus = this.getSlaveMapper().findChildMenusByParentId(parentId);
		List<MenuBo> mapList = BeanMapper.mapList(childMenus, MenuBo.class);
		return mapList;
	}
}
