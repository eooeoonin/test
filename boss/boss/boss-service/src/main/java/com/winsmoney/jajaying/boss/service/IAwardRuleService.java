/**
 * Project:boss
 * File:AwardRule.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.service.request.AwardRuleReqVo;
import com.winsmoney.jajaying.boss.service.response.AwardRuleResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

/**
 * Description: 活动表扩展表设置(集采类) 服务接口
 * date: 2017-11-23 10:02:27
 * @author: CodeCreator
 */
public interface IAwardRuleService
{
    /**
     * 根据id查询 活动表
     * @param id
     * @return AwardRuleResVo
     */
    BossCommonResult<AwardRuleResVo> getById(String id);

    /**
     * 单笔查询 活动表
     * @param awardRuleReqVo
     * @return ActivityExtendResVo
     */
    BossCommonResult<AwardRuleResVo> get(AwardRuleReqVo awardRuleReqVo);

    /**
     * 统计 活动表
     * @param awardRuleReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(AwardRuleReqVo awardRuleReqVo);

    /**
     * 分页列表活动表
     * @param awardRuleReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<AwardRuleResVo>> list(AwardRuleReqVo awardRuleReqVo, int pageNo, int pageSize);

    /**
     * 添加 活动表
     * @param awardRuleReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(AwardRuleReqVo awardRuleReqVo);

    /**
     * 更新 活动表
     * @param awardRuleReqVo
     * @return
     */
    BossCommonResult<Integer> update(AwardRuleReqVo awardRuleReqVo);

    /**
     * 删除 活动表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);
}



