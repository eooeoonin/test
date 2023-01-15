/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.jajaying.boss.dao.po.StaffPo;
import com.winsmoney.platform.framework.core.model.BaseMapper;

/**
 * Description: DAO接口 mapper date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public interface StaffMapper extends BaseMapper<StaffPo> {

	StaffPo getStaffByStaffName(String staffName);

	StaffPo getStaffByIdCard(String idCard);

}
