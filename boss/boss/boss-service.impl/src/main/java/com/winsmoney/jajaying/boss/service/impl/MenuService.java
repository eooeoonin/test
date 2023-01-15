/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.impl;

import java.util.List;

import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.MenuDomain;
import com.winsmoney.jajaying.boss.service.IMenuService;
import com.winsmoney.jajaying.boss.service.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.service.request.MenuReqVo;
import com.winsmoney.jajaying.boss.service.response.MenuResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.jajaying.basedata.service.client.ErrorCodeClient;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.model.ErrorCode;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.framework.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 服务实现 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
@Service(value = "menuService")
public class MenuService implements IMenuService {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuDomain menuDomain;

	@Autowired
	private ErrorCodeClient errorCodeClient;

	/**
	 * 单笔查询
	 * 
	 * @param menuReqVo
	 * @return MenuResVo
	 */
	public BossCommonResult<MenuResVo> getMenu(MenuReqVo menuReqVo) {
		BossCommonResult<MenuResVo> result;
		try {
			Menu condition = BeanMapper.map(menuReqVo, Menu.class);
			Menu menu = menuDomain.getMenu(condition);
			MenuResVo menuResVo = BeanMapper.map(menu, MenuResVo.class);
			ErrorCode outCode = errorCodeClient.changeCode(BossErrorCode.SUCCESS.getCode()); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), menuResVo);
		} catch (Exception e) {
			logger.error("取得Menu异常：" + e.getMessage(), e);
			ErrorCode outCode = errorCodeClient.changeCode(BossErrorCode.ERROR.getCode()); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

	/**
	 * 列表
	 * 
	 * @param menuReqVo
	 * @return List<MenuResVo>
	 */
	public BossCommonResult<List<MenuResVo>> listMenu(MenuReqVo menuReqVo) {
		BossCommonResult<List<MenuResVo>> result;
		try {
			Menu condition = BeanMapper.map(menuReqVo, Menu.class);
			List<Menu> list = menuDomain.listMenu(condition);
			List<MenuResVo> listMenuResVo = BeanMapper.mapList(list, MenuResVo.class);
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), listMenuResVo);
		} catch (Exception e) {
			logger.error("列表Menu异常：" + e.getMessage(), e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

	/**
	 * 统计
	 * 
	 * @param menuReqVo
	 * @return Integer
	 */
	public BossCommonResult<Integer> countMenu(MenuReqVo menuReqVo) {
		BossCommonResult<Integer> result;
		try {
			Menu condition = BeanMapper.map(menuReqVo, Menu.class);
			Integer count = menuDomain.countMenu(condition);
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), count);
		} catch (Exception e) {
			logger.error("统计Menu异常", e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

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
	public BossCommonResult<PageInfo<MenuResVo>> listMenu(MenuReqVo menuReqVo, int pageNo, int pageSize) {
		BossCommonResult<PageInfo<MenuResVo>> result;
		try {
			Menu condition = BeanMapper.map(menuReqVo, Menu.class);
			PageInfo<Menu> list = menuDomain.listMenu(condition, pageNo, pageSize);
			PageInfo<MenuResVo> pageListMenuResVo = BeanMapper.map(list, PageInfo.class);
			pageListMenuResVo.setList(BeanMapper.mapList(list.getList(), MenuResVo.class));
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), pageListMenuResVo);
		} catch (Exception e) {
			logger.error("分页列表Menu异常", e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param menuReqVo
	 * @return Integer
	 */
	public BossCommonResult<Integer> insertMenu(MenuReqVo menuReqVo) {
		BossCommonResult<Integer> result;
		try {
			int count = menuDomain.insertMenu(BeanMapper.map(menuReqVo, Menu.class));
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), count);
		} catch (Exception e) {
			logger.error("添加Menu异常", e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param menuReqVo
	 * @return Integer
	 */
	public BossCommonResult updateMenu(MenuReqVo menuReqVo) {
		BossCommonResult<Integer> result;
		try {
			Menu condition = BeanMapper.map(menuReqVo, Menu.class);
			int count = menuDomain.updateMenu(condition);
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), count);
		} catch (Exception e) {
			logger.error("更新Menu异常", e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return Integer
	 */
	public BossCommonResult deleteMenu(String id) {
		BossCommonResult<Integer> result;
		try {
			int count = menuDomain.deleteMenu(id);
			ErrorCode outCode = errorCodeClient.changeCode("1"); // 转外部错误码(1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), count);
		} catch (Exception e) {
			logger.error("删除Menu异常", e);
			ErrorCode outCode = errorCodeClient.changeCode("-1"); // 转外部错误码(-1为自定义内码)
			result = new BossCommonResult(outCode.getCode(), outCode.getMessage(), null);
		}
		return result;
	}
}
