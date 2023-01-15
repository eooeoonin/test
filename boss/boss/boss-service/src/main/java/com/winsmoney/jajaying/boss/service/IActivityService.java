/**
 * Project:boss
 * File:Activity.java
 * Date:2017-09-04
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.ActivityReqVo;
import com.winsmoney.jajaying.boss.service.response.ActivityResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 活动表 服务接口
 * date: 2017-09-04 05:21:24
 * @author: CodeCreator
 */
public interface IActivityService
{

    /**
    * 根据id查询 活动表
    * @param activityReqVo
    * @return ActivityResVo
    */
    BossCommonResult<ActivityResVo> getById(String id);

    /**
     * 单笔查询 活动表
     * @param activityReqVo
     * @return ActivityResVo
     */
    BossCommonResult<ActivityResVo> get(ActivityReqVo activityReqVo);

    /**
     * 统计 活动表
     * @param activityReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(ActivityReqVo activityReqVo);

    /**
     * 分页列表活动表
     * @param activityReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<ActivityResVo>> list(ActivityReqVo activityReqVo,int pageNo, int pageSize);

    /**
     * 添加 活动表
     * @param activityReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(ActivityReqVo activityReqVo);

    /**
     * 更新 活动表
     * @param activityReqVo
     * @return
     */
    BossCommonResult<Integer> update(ActivityReqVo activityReqVo);

    /**
     * 删除 活动表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

}



