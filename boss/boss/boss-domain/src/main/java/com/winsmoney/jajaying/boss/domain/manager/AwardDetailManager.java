/**
* Project:boss
* File:AwardDetail.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.ArrayList;
import java.util.List;

import com.winsmoney.framework.pagehelper.PageHelper;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.AwardDetailMapper;
import com.winsmoney.jajaying.boss.dao.po.AwardDetailPo;
import com.winsmoney.jajaying.boss.domain.model.AwardDetail;

/**
* Description: 奖品详情(抽奖号码) 管理器
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
@Component
public class AwardDetailManager extends BaseManager<AwardDetail,AwardDetailMapper,AwardDetailPo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AwardDetailManager.class);


    public int deleteByActivityCode(String activityCode) {
        return this.getMasterMapper().deleteByActivityCode( activityCode );
    }

    public List<String> listByActivityCode(String activityCode) {
        return this.getMasterMapper().listByActivityCode( activityCode );
    }

    public List<AwardDetail> getSunShines(AwardDetail awardDetail) {
        List<AwardDetail> result = new ArrayList<>();
        List<AwardDetailPo> list = this.getMasterMapper().getSunShines(BeanMapper.map( awardDetail , AwardDetailPo.class ) );
        result = BeanMapper.mapList(list,resultClass);
        return  result ;
    }


    public Integer getCount(String activityId){
        return this.getSlaveMapper().getCount( activityId );
    }

    public PageInfo<AwardDetail> listForMastor(AwardDetail awardDetail , int pageNum , int pageSize){
        if(null == awardDetail)
        {
            throw new IllegalArgumentException("condition is null");
        }
        PageInfo<AwardDetail> pageList;
        try
        {
            AwardDetailPo param=BeanMapper.map(awardDetail,AwardDetailPo.class);
            PageHelper.startPage(pageNum, pageSize,true);
            List<AwardDetailPo> list = this.getMasterMapper().listForMastor(param);
            List<AwardDetail> resultList = BeanMapper.mapList(list,AwardDetail.class);
            pageList = new PageInfo(list);
            pageList.setList(resultList);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return pageList;
    }

    public int updateUserIdNull(AwardDetail awardDetail) {
        return this.getMasterMapper().updateUserIdNull( BeanMapper.map( awardDetail , AwardDetailPo.class ) );
    }


    /**
     * 批量插入
     * @param listObject
     * @return
     */
    public List<AwardDetail> insertList(List<AwardDetail> listObject)
    {
        if(listObject == null || listObject.isEmpty())
        {
            throw new IllegalArgumentException("value is null");
        }
        int result;
        try
        {
            List<AwardDetailPo> list = new ArrayList<>();
            for(AwardDetail t: listObject)
            {
                if(StringUtils.isEmpty(t.getId()))  //添加id
                {
                    String newId=sequenceGenerator.getUUID();
                    t.setId(newId);
                }
                t.setDeleted(false); //默认值
                AwardDetailPo data=BeanMapper.map(t,poClass);
                list.add( data );
            }
            result = this.getMasterMapper().insertList( list );
            if( result == 0) {
                return new ArrayList<>();
            }
            return listObject;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}


