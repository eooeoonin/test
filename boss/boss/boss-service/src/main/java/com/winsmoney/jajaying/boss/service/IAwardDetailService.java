/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.service.request.AwardDetailReqVo;
import com.winsmoney.jajaying.boss.service.request.AwardDetailReqVo;
import com.winsmoney.jajaying.boss.service.response.AwardDetailResVo;
import com.winsmoney.jajaying.boss.service.response.AwardDetailResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

/**
 * Description: 奖品详情(抽奖号码) 服务接口
 * date: 2017-11-23 02:43:12
 * @author: CodeCreator
 */
public interface IAwardDetailService
{
    /**
     * 根据id查询 活动表
     * @param id
     * @return AwardDetailResVo
     */
    BossCommonResult<AwardDetailResVo> getById(String id);

    /**
     * 单笔查询 活动表
     * @param awardDetailReqVo
     * @return AwardDetailResVo
     */
    BossCommonResult<AwardDetailResVo> get(AwardDetailReqVo awardDetailReqVo);

    /**
     * 统计 活动表
     * @param awardDetailReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(AwardDetailReqVo awardDetailReqVo);

    /**
     * 分页列表活动表
     * @param awardDetailReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<AwardDetailResVo>> list(AwardDetailReqVo awardDetailReqVo, int pageNo, int pageSize);

    /**
     * 添加 活动表
     * @param awardDetailReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(AwardDetailReqVo awardDetailReqVo);

    /**
     * 更新 活动表
     * @param awardDetailReqVo
     * @return
     */
    BossCommonResult<Integer> update(AwardDetailReqVo awardDetailReqVo);

    /**
     * 删除 活动表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

    /**
     * 已经下发抽奖号码
     * @param activityId
     * @return
     */
    BossCommonResult<Integer> getCount(String activityId);
}



