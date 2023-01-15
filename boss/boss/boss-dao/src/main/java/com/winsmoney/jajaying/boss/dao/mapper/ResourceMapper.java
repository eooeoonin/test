/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import java.util.List;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.ResourceFlagPo;
import com.winsmoney.jajaying.boss.dao.po.ResourcePo;

/**
 * Description: DAO接口 mapper date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public interface ResourceMapper extends BaseMapper<ResourcePo> {
	List<ResourcePo> findResourcesByRoleId(String roleId);

	List<ResourcePo> getPermissonByRoleId(String roleId);

	int deleteResourcesByRoleId(String roleId);

	List<ResourceFlagPo> getPermissonFlagByRoleId(String roleId);
}
