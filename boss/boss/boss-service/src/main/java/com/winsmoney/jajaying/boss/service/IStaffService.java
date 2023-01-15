/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.StaffReqVo;
import com.winsmoney.jajaying.boss.service.response.StaffResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 服务接口 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public interface IStaffService {
	/**
	 * 单笔查询
	 * 
	 * @param staffReqVo
	 * @return StaffResVo
	 */
	BossCommonResult<StaffResVo> getStaff(StaffReqVo staffReqVo);

	/**
	 * 列表
	 * 
	 * @param staffReqVo
	 * @return List<StaffResVo>
	 */
	BossCommonResult<List<StaffResVo>> listStaff(StaffReqVo staffReqVo);

	/**
	 * 统计
	 * 
	 * @param staffReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> countStaff(StaffReqVo staffReqVo);

	/**
	 * 分页列表
	 * 
	 * @param staffReqVo
	 *            查询条件
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	BossCommonResult<PageInfo<StaffResVo>> listStaff(StaffReqVo staffReqVo, int pageNo, int pageSize);

	/**
	 * 添加
	 * 
	 * @param staffReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> insertStaff(StaffReqVo staffReqVo);

	/**
	 * 更新
	 * 
	 * @param staffReqVo
	 * @return
	 */
	BossCommonResult<Integer> updateStaff(StaffReqVo staffReqVo);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	BossCommonResult<Integer> deleteStaff(String id);

}
