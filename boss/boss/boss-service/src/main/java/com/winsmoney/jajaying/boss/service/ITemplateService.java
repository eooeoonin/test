/**
 * Project:boss
 * File:Template.java
 * Date:2017-09-04
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service;

import java.util.List;

import com.winsmoney.jajaying.boss.service.request.TemplateReqVo;
import com.winsmoney.jajaying.boss.service.response.TemplateResVo;
import com.winsmoney.jajaying.boss.service.response.BossCommonResult;

import com.winsmoney.framework.pagehelper.PageInfo;

/**
 * Description: 模板表 服务接口
 * date: 2017-09-04 04:11:05
 * @author: CodeCreator
 */
public interface ITemplateService
{

    /**
    * 根据id查询 模板表
    * @param templateReqVo
    * @return TemplateResVo
    */
    BossCommonResult<TemplateResVo> getById(String id);

    /**
     * 单笔查询 模板表
     * @param templateReqVo
     * @return TemplateResVo
     */
    BossCommonResult<TemplateResVo> get(TemplateReqVo templateReqVo);

    /**
     * 统计 模板表
     * @param templateReqVo
     * @return Integer
     */
    BossCommonResult<Integer> count(TemplateReqVo templateReqVo);

    /**
     * 分页列表模板表
     * @param templateReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
    BossCommonResult<PageInfo<TemplateResVo>> list(TemplateReqVo templateReqVo,int pageNo, int pageSize);

    /**
     * 添加 模板表
     * @param templateReqVo
     * @return Integer
     */
    BossCommonResult<Integer> insert(TemplateReqVo templateReqVo);

    /**
     * 更新 模板表
     * @param templateReqVo
     * @return
     */
    BossCommonResult<Integer> update(TemplateReqVo templateReqVo);

    /**
     * 删除 模板表
     * @param id
     * @return
     */
    BossCommonResult<Integer> delete(String id);

}



