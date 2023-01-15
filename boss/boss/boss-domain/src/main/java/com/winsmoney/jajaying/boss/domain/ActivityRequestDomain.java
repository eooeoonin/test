/**
* Project:boss
* File:ActivityRequest.java
* Date:2017-11-23
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.enums.AwardStatus;
import com.winsmoney.jajaying.boss.domain.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.jajaying.boss.domain.manager.ActivityManager;
import com.winsmoney.jajaying.boss.domain.manager.ActivityRequestManager;
import com.winsmoney.jajaying.boss.domain.manager.AwardDetailManager;
import com.winsmoney.jajaying.boss.domain.model.Activity;
import com.winsmoney.jajaying.boss.domain.model.ActivityRequest;

import com.winsmoney.jajaying.boss.domain.model.AwardDetail;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.platform.framework.core.util.Money;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;


/**
* ClassName: activityRequest
* Description: 活动请求 实现
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
@Service
public class ActivityRequestDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityRequestDomain.class);

    @Autowired
    private ActivityRequestManager activityRequestManager;
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
    public ActivityRequest getById(String id)
    {
        ActivityRequest r=activityRequestManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public ActivityRequest get(ActivityRequest condition)
    {
        ActivityRequest result= activityRequestManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(ActivityRequest condition)
    {
        int result = activityRequestManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<ActivityRequest> list(ActivityRequest condition)
    {
        List<ActivityRequest> result = activityRequestManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<ActivityRequest> list(ActivityRequest condition, int pageNo, int pageSize)
    {
        PageInfo<ActivityRequest> result = activityRequestManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(ActivityRequest value)
    {
        int result = activityRequestManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(ActivityRequest value)
    {
        int result = activityRequestManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id)
    {
        int result = activityRequestManager.delete(id);
        return result;
    }

    /**
     * 或懂请求处理
     */
    public ActivityRequest activityRequest( final ActivityRequest activityRequest){
        logger.info( " 活动扩展信息未设置 " + JSONObject.toJSONString( activityRequest ));
        //取得设置分割点 //现在时间可参加的活动
        final List<Activity> activityList = activityManager.activityList( activityRequest.getTradeTime() );
        if( !activityList.isEmpty() ){
            transactionTemplateMaster.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                   try{
                       for ( Activity activity : activityList ) { //目前只能支持一个活动
                           ActivityRequest activityRequestpo = activityRequest ;
                           BigDecimal standardAmount = activity.getStandardAmount(); // 根据金额
                           BigDecimal transValue = activityRequestpo.getTransValue();
                           BigDecimal portion = transValue.divideToIntegralValue(standardAmount);
                           if (portion.compareTo(BigDecimal.ONE) > -1) {
                               activityRequestpo.setActivityId( activity.getId() ); //活动编号
                               activityRequestpo.setHandleStatus("END");
                               activityRequestpo.setId(null);
                               //记录请求数据
                               int requestCount = activityRequestManager.insert( activityRequestpo );
                               if( requestCount == 0 ){
                                   throw new BusinessException(BossErrorCode.ERROR);
                               }
//                               Integer start = 0 ;

                               for (int i = 1; i <= portion.intValue(); i++) {
                                   boolean flag = true ;
                                   //下发用户抽奖号
                                   AwardDetail queryAwardDetail = new AwardDetail();
                                   queryAwardDetail.setActivityId( activity.getId() );
                                   queryAwardDetail.setAwardStatus( AwardStatus.INIT.name() );
                                   PageInfo<AwardDetail> listAward =awardDetailManager.listForMastor( queryAwardDetail , 1 , 1000 );
                                   for(int j = 0 ; j < listAward.getList().size();j++ ){
                                       AwardDetail awardDetail = listAward.getList().get( j );
                                       awardDetail.setUserId( activityRequestpo.getUserId() ); //设置用户编号
                                       awardDetail.setAwardStatus( AwardStatus.USE.name() );//设置券状态
                                       int detailCount = awardDetailManager.updateUserIdNull( awardDetail );
                                       if( detailCount > 0 ){
                                           flag = false ;
                                           break;
                                       }else{
                                           continue;
                                       }
                                   }
                                   if( flag ){
                                       throw new BusinessException(BossErrorCode.ERROR);
                                   }
                               }
                           }
                       }
                       return activityRequest;
                   }catch (BusinessException e){
                       logger.error( " 异常 " , e );
                       transactionStatus.setRollbackOnly();
                       return activityRequest;
                   }catch (Exception e){
                       logger.error( " 异常 " , e );
                       transactionStatus.setRollbackOnly();
                       return activityRequest;
                   }
                }
            });
        }else{
            logger.info( " 活动扩展信息未设置 " + JSONObject.toJSONString( activityRequest ));
        }
        return activityRequest;
    }

    /**
     * 根据项目编号查询活动奖池金额
     * @param activityId
     * @return
     */
    public Money getSum(String activityId) {
        Activity activity = new Activity();
        activity.setCode( Integer.parseInt( activityId ));
        activity = activityManager.get( activity );
        if(StringUtils.isBlank( activity.getId() )){
            return new Money();
        }
        //满足活动投资金额
        BigDecimal invistAmount = activityRequestManager.getSum( activity.getId() );
        BigDecimal totalAmount = invistAmount.multiply( activity.getConversionRatio() ).divide( new BigDecimal( 100 )) ; // 投资总金额 * 比率 / 100
        if( totalAmount.compareTo( activity.getTotalAmount())  == 1 ){
            return new Money( activity.getTotalAmount() );
        }else{
            return new Money( totalAmount );
        }
    }

}
