/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.service.request.ActivityRequestReqVo;
import com.winsmoney.jajaying.boss.service.response.ActivityRequestResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;
import com.winsmoney.platform.framework.core.util.Money;

/**
 * Description: 活动请求 服务接口
 * date: 2017-11-23 02:43:12
 * @author: CodeCreator
 */
public interface IActivityRequestService
{
    /**
     * 根据id查询 活动表
     * @param id
     * @return ActivityResVo
     */
    BossCommonResult<ActivityRequestResVo> getById(String id);

    /**
     * 单笔查询 活动表
     * @param activityRequestReqVo
     * @return ActivityRequestResVo
     */
    BossCommonResult<ActivityRequestResVo> get(ActivityRequestReqVo activityRequestReqVo);

    /**
     * 统计 活动表
     * @param activityRequestReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(ActivityRequestReqVo activityRequestReqVo);

    /**
     * 分页列表活动表
     * @param activityRequestReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<ActivityRequestResVo>> list(ActivityRequestReqVo activityRequestReqVo, int pageNo, int pageSize);

    /**
     * 添加 活动表
     * @param activityRequestReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(ActivityRequestReqVo activityRequestReqVo);

    /**
     * 更新 活动表
     * @param activityRequestReqVo
     * @return
     */
    BossCommonResult<Integer> update(ActivityRequestReqVo activityRequestReqVo);

    /**
     * 删除 活动表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

    /**
     * 根据项目编号查询活动奖池金额
     * @param activityId
     * @return
     */
    BossCommonResult<Money> getSum( String activityId);
}



