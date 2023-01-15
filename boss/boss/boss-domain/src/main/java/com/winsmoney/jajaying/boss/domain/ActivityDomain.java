/**
* Project:boss
* File:Activity.java
* Date:2017-09-04
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.manager.ActivityManager;
import com.winsmoney.jajaying.boss.domain.manager.AwardDetailManager;
import com.winsmoney.jajaying.boss.domain.model.Activity;

import com.winsmoney.jajaying.boss.domain.model.AwardDetail;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;


/**
* ClassName: activity
* Description: 活动表 实现
* date: 2017-09-04 05:21:24
* @author: CodeCreator
*/
@Service
public class ActivityDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityDomain.class);

    @Autowired
    private ActivityManager activityManager;
    @Autowired
    private AwardDetailManager awardDetailManager;
    @Autowired
    private TransactionTemplate transactionTemplateMaster;
    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Activity getById(String id)
    {
        Activity r=activityManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Activity get(Activity condition)
    {
        Activity result= activityManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(Activity condition)
    {
        int result = activityManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Activity> list(Activity condition)
    {
        List<Activity> result = activityManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Activity> list(Activity condition, int pageNo, int pageSize)
    {
        PageInfo<Activity> result = activityManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert( final Activity activity)
    {
        return transactionTemplateMaster.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    int result = activityManager.insert( activity );
                    List<AwardDetail> awardDetails = new ArrayList<>();
                    if(  null != activity.getStartNum() && null != activity.getEndNum() ) {
                        //创建抽奖号
                        for (long i = activity.getStartNum(); i <= activity.getEndNum(); i++) {
                            AwardDetail awardDetail = new AwardDetail();
                            awardDetail.setAwardStatus("INIT");
                            awardDetail.setCode(Long.toString(i));
                            awardDetail.setActivityId(activity.getId());
                            awardDetail.setCreatedBy(activity.getCreatedBy());
                            awardDetails.add(awardDetail);
                        }
                        List<AwardDetail> count = awardDetailManager.insertList(awardDetails);
                        if (count.size() == 0) {
                            throw new BusinessException(BossErrorCode.ERROR);
                        }
                    }
                    return result;
                } catch (RuntimeException e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                } catch (Exception e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                }
            }
        });

    }

    public int editActivity(final Activity activity)
    {
        return transactionTemplateMaster.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    Activity activityPo = activityManager.getById( activity.getId() );
                    int result = activityManager.update( activity );
                    if( null != activity.getStartNum() && null != activity.getEndNum() ) {
                        if (!activity.getStartNum().equals(activityPo.getStartNum()) || !activity.getEndNum().equals(activityPo.getEndNum())){
                            int del = awardDetailManager.deleteByActivityCode(activity.getId());

                            List<String> useList = awardDetailManager.listByActivityCode(activity.getId());
                            List<AwardDetail> awardDetails = new ArrayList<>();
                            //创建抽奖号
                            for (long i = activity.getStartNum(); i <= activity.getEndNum(); i++) {
                                if(!useList.contains( Long.toString(i) )) {
                                    AwardDetail awardDetail = new AwardDetail();
                                    awardDetail.setAwardStatus("INIT");
                                    awardDetail.setCode(Long.toString(i));
                                    awardDetail.setActivityId(activity.getId());
                                    awardDetail.setCreatedBy(activity.getCreatedBy());
                                    awardDetails.add(awardDetail);
                                }
                            }
                            List<AwardDetail> count = awardDetailManager.insertList(awardDetails);
                            if (count.size() == 0) {
                                throw new BusinessException(BossErrorCode.ERROR);
                            }
                        }
                    }
                    return result;
                } catch (RuntimeException e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                } catch (Exception e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                }
            }
        });


    }
    /**
     * 更新
     */
    public int update(final Activity activity)
    {
        return transactionTemplateMaster.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    int result = activityManager.update( activity );
                    return result;
                } catch (RuntimeException e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                } catch (Exception e) {
                    logger.error(" 异常 ", e);
                    transactionStatus.setRollbackOnly();
                    return 0;
                }
            }
        });


    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = activityManager.delete(id);
        return result;
    }

}
