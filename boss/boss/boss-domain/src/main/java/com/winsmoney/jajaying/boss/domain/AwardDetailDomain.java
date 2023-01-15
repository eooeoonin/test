/**
* Project:boss
* File:AwardDetail.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.ActivityManager;
import com.winsmoney.jajaying.boss.domain.manager.AwardDetailManager;
import com.winsmoney.jajaying.boss.domain.model.Activity;
import com.winsmoney.jajaying.boss.domain.model.AwardDetail;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.platform.framework.core.util.Money;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
* ClassName: awardDetail
* Description: 奖品详情(抽奖号码) 实现
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
@Service
public class AwardDetailDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardDetailDomain.class);

    @Autowired
    private AwardDetailManager awardDetailManager;
    @Autowired
    private ActivityManager activityManager;

    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public AwardDetail getById(String id)
    {
        AwardDetail r=awardDetailManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public AwardDetail get(AwardDetail condition)
    {
        AwardDetail result= awardDetailManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(AwardDetail condition)
    {
        int result = awardDetailManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<AwardDetail> list(AwardDetail condition)
    {
        List<AwardDetail> result = awardDetailManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<AwardDetail> list(AwardDetail condition, int pageNo, int pageSize)
    {
        Activity activity = new Activity();
        activity.setCode( Integer.parseInt( condition.getActivityId() ));
        activity = activityManager.get( activity );
        if(StringUtils.isBlank( activity.getId() )){
            return new PageInfo<AwardDetail>();
        }
        condition.setActivityId( activity.getId() );
        PageInfo<AwardDetail> result = awardDetailManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public List<AwardDetail> insertList(List<AwardDetail> awardDetails)
    {
        List<AwardDetail> result = awardDetailManager.insertList( awardDetails );
        return result;
    }

    /**
     * 插入
     */
    public int insert(AwardDetail value)
    {
        int result = awardDetailManager.insert( value );
        return result;
    }

    /**
     * 更新
     */
    public int update(AwardDetail value)
    {
        int result = awardDetailManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = awardDetailManager.delete(id);
        return result;
    }

    public List<AwardDetail> getSunShines(AwardDetail awardDetail) {
        List<AwardDetail> result = awardDetailManager.getSunShines( awardDetail );
        return result;
    }

    public Integer getCount(String activityId){
        Activity activity = new Activity();
        activity.setCode( Integer.parseInt( activityId ));
        activity = activityManager.get( activity );
        if(StringUtils.isBlank( activity.getId() )){
            return 0;
        }
        return awardDetailManager.getCount( activity.getId() );
    }
}
