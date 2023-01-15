/**
 * Project:boss
 * File:Link.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.LinkReqVo;
import com.winsmoney.jajaying.boss.service.response.LinkResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 链接表 服务接口
 * date: 2017-08-31 05:16:26
 * @author: CodeCreator
 */
public interface ILinkService
{

    /**
    * 根据id查询 链接表
    * @param linkReqVo
    * @return LinkResVo
    */
    BossCommonResult<LinkResVo> getById(String id);

    /**
     * 单笔查询 链接表
     * @param linkReqVo
     * @return LinkResVo
     */
    BossCommonResult<LinkResVo> get(LinkReqVo linkReqVo);

    /**
     * 统计 链接表
     * @param linkReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(LinkReqVo linkReqVo);

    /**
     * 分页列表链接表
     * @param linkReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<LinkResVo>> list(LinkReqVo linkReqVo,int pageNo, int pageSize);

    /**
     * 添加 链接表
     * @param linkReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(LinkReqVo linkReqVo);

    /**
     * 更新 链接表
     * @param linkReqVo
     * @return
     */
    BossCommonResult<Integer> update(LinkReqVo linkReqVo);

    /**
     * 删除 链接表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

}



