/**
 * Project:boss
 * File:ChannelType.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.ChannelTypeReqVo;
import com.winsmoney.jajaying.boss.service.response.ChannelTypeResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 渠道类型 服务接口
 * date: 2017-08-31 05:16:25
 * @author: CodeCreator
 */
public interface IChannelTypeService
{

    /**
    * 根据id查询 渠道类型
    * @param channelTypeReqVo
    * @return ChannelTypeResVo
    */
    BossCommonResult<ChannelTypeResVo> getById(String id);

    /**
     * 单笔查询 渠道类型
     * @param channelTypeReqVo
     * @return ChannelTypeResVo
     */
    BossCommonResult<ChannelTypeResVo> get(ChannelTypeReqVo channelTypeReqVo);

    /**
     * 统计 渠道类型
     * @param channelTypeReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(ChannelTypeReqVo channelTypeReqVo);

    /**
     * 分页列表渠道类型
     * @param channelTypeReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<ChannelTypeResVo>> list(ChannelTypeReqVo channelTypeReqVo,int pageNo, int pageSize);

    /**
     * 添加 渠道类型
     * @param channelTypeReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(ChannelTypeReqVo channelTypeReqVo);

    /**
     * 更新 渠道类型
     * @param channelTypeReqVo
     * @return
     */
    BossCommonResult<Integer> update(ChannelTypeReqVo channelTypeReqVo);

    /**
     * 删除 渠道类型
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

}



