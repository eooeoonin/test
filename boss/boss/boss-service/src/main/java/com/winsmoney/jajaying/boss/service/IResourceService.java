/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.ResourceReqVo;
import com.winsmoney.jajaying.boss.service.response.ResourceResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 服务接口 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public interface IResourceService {
	/**
	 * 单笔查询
	 * 
	 * @param resourceReqVo
	 * @return ResourceResVo
	 */
	BossCommonResult<ResourceResVo> getResource(ResourceReqVo resourceReqVo);

	/**
	 * 列表
	 * 
	 * @param resourceReqVo
	 * @return List<ResourceResVo>
	 */
	BossCommonResult<List<ResourceResVo>> listResource(ResourceReqVo resourceReqVo);

	/**
	 * 统计
	 * 
	 * @param resourceReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> countResource(ResourceReqVo resourceReqVo);

	/**
	 * 分页列表
	 * 
	 * @param resourceReqVo
	 *            查询条件
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	BossCommonResult<PageInfo<ResourceResVo>> listResource(ResourceReqVo resourceReqVo, int pageNo, int pageSize);

	/**
	 * 添加
	 * 
	 * @param resourceReqVo
	 * @return Integer
	 */
	BossCommonResult<Integer> insertResource(ResourceReqVo resourceReqVo);

	/**
	 * 更新
	 * 
	 * @param resourceReqVo
	 * @return
	 */
	BossCommonResult<Integer> updateResource(ResourceReqVo resourceReqVo);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	BossCommonResult<Integer> deleteResource(String id);

}
