package com.winsmoney.jajaying.boss.service.impl.mq;

import com.winsmoney.jajaying.boss.domain.utils.MqConsumerTimeInternalsUtil;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.mq.WinsMoneyReceiveMessage;
import com.winsmoney.platform.framework.mq.consumer.AbstractWinsMoneyMQSyncConsumerConcurrently;
import com.winsmoney.platform.framework.mq.enums.WinsMoneyMQConsumeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by fengwei on 2017/3/31.
 */
public abstract class ActivityMQConsumer extends AbstractWinsMoneyMQSyncConsumerConcurrently {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityMQConsumer.class);

    private static int[] DELAY_LEVELS = null;
    @Value("${metaqRetryTimes}")
    private int metaqRetryTimes;
    @Autowired
    public MqConsumerTimeInternalsUtil mqConsumerTimeInternalsUtil;

    /**
     * 根据重试次数设置重新消费延迟时间 1s 10s 30s 2m 10m 30m 1h 2h 12h 1d
     *
     * @param reconsumeTimes
     * @return
     */
    public int getDelayLevelWhenNextConsume(int reconsumeTimes) {
        DELAY_LEVELS = this.getDelay_levels();
        if (reconsumeTimes >= DELAY_LEVELS.length) {
            return DELAY_LEVELS[DELAY_LEVELS.length - 1];
        }
        return DELAY_LEVELS[reconsumeTimes];
    }

    /**
     * 超过重试次数，消费该消息，并记录数据。 Description:
     *
     * @param bizNo
     * @param reconsumeTimes
     * @return
     */
    public WinsMoneyMQConsumeStatus doRetry(String bizNo,  int reconsumeTimes , String bizName) {
        logger.info("***" + bizName + "开始 doRetry 第" + reconsumeTimes + "次***" + bizNo);
        if (reconsumeTimes >= metaqRetryTimes) {
            logger.info("达到最大重试次数=" + metaqRetryTimes);
            // 落异常单
            return WinsMoneyMQConsumeStatus.SUCCESS;
        }
        return WinsMoneyMQConsumeStatus.LATER;
    }

    /**
     * 获取异常消费的时间间隔
     *
     * @return
     */
    private int[] getDelay_levels() {
        if (null == DELAY_LEVELS) {
            DELAY_LEVELS = mqConsumerTimeInternalsUtil.getExceptionTimeIntervals();
        }
        return DELAY_LEVELS;
    }

    @Override
    public void handleMQException(WinsMoneyReceiveMessage msg) {
        logger.info("*********handleMQException***************");
        logger.info(msg.toString());
        logger.info("*********handleMQException***************");
    }
}
