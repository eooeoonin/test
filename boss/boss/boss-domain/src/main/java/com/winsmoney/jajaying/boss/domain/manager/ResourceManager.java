/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.ResourceMapper;
import com.winsmoney.jajaying.boss.dao.po.ResourceFlagPo;
import com.winsmoney.jajaying.boss.dao.po.ResourcePo;
import com.winsmoney.jajaying.boss.domain.model.Resource;

/**
 * Description: 管理器 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@Component
public class ResourceManager extends BaseManager<Resource, ResourceMapper, ResourcePo> {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ResourceManager.class);

	public List<Resource> findResourcesByRoleId(String roleId) {
		List<ResourcePo> resources = this.getSlaveMapper().findResourcesByRoleId(roleId);
		List<Resource> mapList = BeanMapper.mapList(resources, Resource.class);
		return mapList;
	}

	public List<Resource> getPermissonByRoleId(String roleId) {
		List<ResourcePo> permissions = this.getSlaveMapper().getPermissonByRoleId(roleId);
		List<Resource> mapList = BeanMapper.mapList(permissions, Resource.class);
		return mapList;
	}

	public int deleteResourcesByRoleId(String roleId) {
		return this.getMasterMapper().deleteResourcesByRoleId(roleId);
	}

	public List<Resource> getPermissonFlagByRoleId(String roleId) {
		List<ResourceFlagPo> permissions = this.getSlaveMapper().getPermissonFlagByRoleId(roleId);
		List<Resource> mapList = BeanMapper.mapList(permissions, Resource.class);
		return mapList;
	}
}
