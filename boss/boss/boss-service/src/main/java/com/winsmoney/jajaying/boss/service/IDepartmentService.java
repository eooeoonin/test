/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.DepartmentReqVo;
import com.winsmoney.jajaying.boss.service.response.DepartmentResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 服务接口 date: 2016-08-03 06:03:16
 * 
 * @author: CodeCreator
 */
public interface IDepartmentService {
	/**
	 * 单笔查询
	 * 
	 * @param departmentReqVo
	 * @return DepartmentResVo
	 */
	BossCommonResult<DepartmentResVo> getDepartment(DepartmentReqVo departmentReqVo);

	/**
	 * 列表
	 * 
	 * @param departmentReqVo
	 * @return List<DepartmentResVo>
	 */
	BossCommonResult<List<DepartmentResVo>> listDepartment(DepartmentReqVo departmentReqVo);

	/**
	 * 统计
	 * 
	 * @param departmentReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> countDepartment(DepartmentReqVo departmentReqVo);

	/**
	 * 分页列表
	 * 
	 * @param departmentReqVo
	 *            查询条件
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	BossCommonResult<PageInfo<DepartmentResVo>> listDepartment(DepartmentReqVo departmentReqVo, int pageNo, int pageSize);

	/**
	 * 添加
	 * 
	 * @param departmentReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> insertDepartment(DepartmentReqVo departmentReqVo);

	/**
	 * 更新
	 * 
	 * @param departmentReqVo
	 * @return
	 */
	BossCommonResult<Integer> updateDepartment(DepartmentReqVo departmentReqVo);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	BossCommonResult<Integer> deleteDepartment(String id);

}
