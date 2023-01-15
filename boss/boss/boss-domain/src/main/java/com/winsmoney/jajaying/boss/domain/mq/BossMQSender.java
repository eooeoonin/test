package com.winsmoney.jajaying.boss.domain.mq;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.mq.WinsMoneySendMessage;
import com.winsmoney.platform.framework.mq.WinsMoneySendResult;
import com.winsmoney.platform.framework.mq.producer.WinsMoneyMQProducer;

@Component
public class BossMQSender {
	private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(BossMQSender.class);

	@Resource(name = "defaultWinsMoneyMQProducer")
	private WinsMoneyMQProducer winsMoneyMQProducer;

	public WinsMoneyMQProducer getWinsMoneyMQProducer() {
		return winsMoneyMQProducer;
	}

	public void setWinsMoneyMQProducer(WinsMoneyMQProducer winsMoneyMQProducer) {
		this.winsMoneyMQProducer = winsMoneyMQProducer;
	}

	public boolean sendMQ(MsgMQTopic mqTopic, Map data) {
		try {
			WinsMoneySendMessage message = new WinsMoneySendMessage();
			message.setBizValue(data);
			WinsMoneySendResult result = winsMoneyMQProducer.sendMessage(mqTopic.getTopic(), mqTopic.getTag(), message);
			logger.info(">>>>mq发送结果：" + result.getMqMsg() + " msgId=" + result.getMsgId());
			if(result.getSendStatus().toString().equals("SEND_OK")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("MQ发送异常", e);
			return false;

		}
	}

 }
