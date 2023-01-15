/**
 * Project:boss
 * File:Channel.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.ChannelReqVo;
import com.winsmoney.jajaying.boss.service.response.ChannelResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 渠道表 服务接口
 * date: 2017-08-31 05:12:49
 * @author: CodeCreator
 */
public interface IChannelService
{

    /**
    * 根据id查询 渠道表
    * @param channelReqVo
    * @return ChannelResVo
    */
    BossCommonResult<ChannelResVo> getById(String id);

    /**
     * 单笔查询 渠道表
     * @param channelReqVo
     * @return ChannelResVo
     */
    BossCommonResult<ChannelResVo> get(ChannelReqVo channelReqVo);

    /**
     * 统计 渠道表
     * @param channelReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(ChannelReqVo channelReqVo);

    /**
     * 分页列表渠道表
     * @param channelReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<ChannelResVo>> list(ChannelReqVo channelReqVo,int pageNo, int pageSize);

    /**
     * 添加 渠道表
     * @param channelReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(ChannelReqVo channelReqVo);

    /**
     * 更新 渠道表
     * @param channelReqVo
     * @return
     */
    BossCommonResult<Integer> update(ChannelReqVo channelReqVo);

    /**
     * 删除 渠道表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

}



