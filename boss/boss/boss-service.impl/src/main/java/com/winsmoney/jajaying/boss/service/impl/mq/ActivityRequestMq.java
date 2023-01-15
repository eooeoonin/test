package com.winsmoney.jajaying.boss.service.impl.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.domain.ActivityRequestDomain;
import com.winsmoney.jajaying.boss.domain.model.ActivityRequest;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.mq.WinsMoneyMQRuntimeException;
import com.winsmoney.platform.framework.mq.WinsMoneyReceiveMessage;
import com.winsmoney.platform.framework.mq.WinsMoneySendMessage;
import com.winsmoney.platform.framework.mq.WinsMoneyTopicTagAssemble;
import com.winsmoney.platform.framework.mq.enums.WinsMoneyMQConsumeStatus;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.winsmoney.jajaying.boss.domain.mq.MsgMQTopic.ACTIVITY_REQUEST;
import static com.winsmoney.jajaying.boss.domain.utils.MqConsumerTimeInternalsUtil.getDelayLevel;

/**
 * 调增并冻结-冻结
 * Created by fengwei on 2017/3/30.
 */
public class ActivityRequestMq extends ActivityMQConsumer {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityRequestMq.class);

    @Autowired
    private ActivityRequestDomain activityRequestDomain;
    @Override
    public WinsMoneyMQConsumeStatus doConsume(WinsMoneyReceiveMessage msg) throws WinsMoneyMQRuntimeException {
        Map<Object, Object> bizMap = null;
        int  reconsumeTimes = msg.getReconsumeTimes();
        try {
            WinsMoneySendMessage sendMsg = (WinsMoneySendMessage) msg.getBizValue();
            bizMap = (Map<Object, Object>) sendMsg.getBizValue();
            this.setDelayLevel(getDelayLevel(reconsumeTimes));
            logger.info( " 活动请求 ===== >>>>>>>> : " + JSONObject.toJSONString( bizMap ));
            ActivityRequest activityRequest = new ActivityRequest();
            activityRequest.setRequestId( (String) bizMap.get("requestId"));
            activityRequest.setTradeTime( (Date) bizMap.get("tradeTime"));
            activityRequest.setUserId( (String) bizMap.get("userId"));
            activityRequest.setProjectId( bizMap.get( "projectId" ) == null ? null : (String) bizMap.get( "projectId" ) );
            activityRequest.setActivityId( bizMap.get( "activityCode" ) == null ? null : (String) bizMap.get( "activityCode" )  );
            Object transValue = bizMap.get("TRANSVALUE");
            if(Money.class.isInstance(transValue)){
                Money money = (Money)transValue;
                bizMap.put("TRANSVALUE", money.getAmount().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
            }
            activityRequest.setTransValue(new BigDecimal((String) bizMap.get("TRANSVALUE")));
            if( null == activityRequest.getTradeTime()){
                activityRequest.setTradeTime( new Date());
            }
            activityRequest = activityRequestDomain.activityRequest( activityRequest );
            logger.info(JSON.toJSONString(activityRequest));
            return WinsMoneyMQConsumeStatus.SUCCESS;
        }catch (Exception e){
        	logger.error(e.getMessage(), e);
            return doRetry(JSONObject.toJSONString(bizMap), reconsumeTimes ,ACTIVITY_REQUEST.getMessage());
        }
    }

    @Override
    public WinsMoneyTopicTagAssemble getTopicAndTagExpress() {
        WinsMoneyTopicTagAssemble ttAssemble = new WinsMoneyTopicTagAssemble();
        ttAssemble.setTopic( ACTIVITY_REQUEST.getTopic() );
        ttAssemble.setTag( ACTIVITY_REQUEST.getTag() );
        return ttAssemble;
    }
}
