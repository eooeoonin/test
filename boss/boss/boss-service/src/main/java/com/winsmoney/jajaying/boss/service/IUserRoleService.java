/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.UserRoleReqVo;
import com.winsmoney.jajaying.boss.service.response.UserRoleResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 服务接口 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public interface IUserRoleService {
	/**
	 * 单笔查询
	 * 
	 * @param userRoleReqVo
	 * @return UserRoleResVo
	 */
	BossCommonResult<UserRoleResVo> getUserRole(UserRoleReqVo userRoleReqVo);

	/**
	 * 列表
	 * 
	 * @param userRoleReqVo
	 * @return List<UserRoleResVo>
	 */
	BossCommonResult<List<UserRoleResVo>> listUserRole(UserRoleReqVo userRoleReqVo);

	/**
	 * 统计
	 * 
	 * @param userRoleReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> countUserRole(UserRoleReqVo userRoleReqVo);

	/**
	 * 分页列表
	 * 
	 * @param userRoleReqVo
	 *            查询条件
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	BossCommonResult<PageInfo<UserRoleResVo>> listUserRole(UserRoleReqVo userRoleReqVo, int pageNo, int pageSize);

	/**
	 * 添加
	 * 
	 * @param userRoleReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> insertUserRole(UserRoleReqVo userRoleReqVo);

	/**
	 * 更新
	 * 
	 * @param userRoleReqVo
	 * @return
	 */
	BossCommonResult<Integer> updateUserRole(UserRoleReqVo userRoleReqVo);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	BossCommonResult<Integer> deleteUserRole(String id);

}
