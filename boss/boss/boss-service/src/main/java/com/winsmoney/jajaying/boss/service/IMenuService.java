/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.MenuReqVo;
import com.winsmoney.jajaying.boss.service.response.MenuResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 服务接口 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public interface IMenuService {
	/**
	 * 单笔查询
	 * 
	 * @param menuReqVo
	 * @return MenuResVo
	 */
	BossCommonResult<MenuResVo> getMenu(MenuReqVo menuReqVo);

	/**
	 * 列表
	 * 
	 * @param menuReqVo
	 * @return List<MenuResVo>
	 */
	BossCommonResult<List<MenuResVo>> listMenu(MenuReqVo menuReqVo);

	/**
	 * 统计
	 * 
	 * @param menuReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> countMenu(MenuReqVo menuReqVo);

	/**
	 * 分页列表
	 * 
	 * @param menuReqVo
	 *            查询条件
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	BossCommonResult<PageInfo<MenuResVo>> listMenu(MenuReqVo menuReqVo, int pageNo, int pageSize);

	/**
	 * 添加
	 * 
	 * @param menuReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> insertMenu(MenuReqVo menuReqVo);

	/**
	 * 更新
	 * 
	 * @param menuReqVo
	 * @return
	 */
	BossCommonResult<Integer> updateMenu(MenuReqVo menuReqVo);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	BossCommonResult<Integer> deleteMenu(String id);

}
