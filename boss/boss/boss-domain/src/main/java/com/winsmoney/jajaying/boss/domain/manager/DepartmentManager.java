/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.manager;

import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.DepartmentMapper;
import com.winsmoney.jajaying.boss.dao.po.DepartmentPo;
import com.winsmoney.jajaying.boss.domain.model.Department;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

/**
 * Description: 管理器 date: 2016-08-03 06:03:16
 * 
 * @author: CodeCreator
 */
@Component
public class DepartmentManager extends BaseManager<Department, DepartmentMapper, DepartmentPo> {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(DepartmentManager.class);

	public Department getDepartmentByName(String departmentName) {
		DepartmentPo departmentPo = this.getSlaveMapper().getDepartmentByName(departmentName);
		Department department = BeanMapper.map(departmentPo, Department.class);
		return department;
	}

	public Department getDepartmentByCode(String departmentCode) {
		DepartmentPo departmentPo = this.getSlaveMapper().getDepartmentByCode(departmentCode);
		Department department = BeanMapper.map(departmentPo, Department.class);
		return department;
	}

}
