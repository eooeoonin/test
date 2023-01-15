/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.DepartmentPo;

/**
 * Description: DAO接口 mapper date: 2016-08-03 06:03:16
 * 
 * @author: CodeCreator
 */
public interface DepartmentMapper extends BaseMapper<DepartmentPo> {

	DepartmentPo getDepartmentByName(String departmentName);

	DepartmentPo getDepartmentByCode(String departmentCode);

}
