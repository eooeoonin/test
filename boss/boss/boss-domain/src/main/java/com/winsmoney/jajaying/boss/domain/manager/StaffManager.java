/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.manager;

import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.StaffMapper;
import com.winsmoney.jajaying.boss.dao.po.StaffPo;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

/**
 * Description: 管理器 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@Component
public class StaffManager extends BaseManager<Staff, StaffMapper, StaffPo> {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(StaffManager.class);

	public Staff getStaffByStaffName(String staffName) {
		StaffPo staffByStaffName = this.getSlaveMapper().getStaffByStaffName(staffName);
		Staff staff = BeanMapper.map(staffByStaffName, Staff.class);
		return staff;
	}

	public Staff getStaffByIdCard(String idCard) {
		StaffPo staffByIdCard = this.getSlaveMapper().getStaffByIdCard(idCard);
		Staff staff = BeanMapper.map(staffByIdCard, Staff.class);
		return staff;
	}

}
