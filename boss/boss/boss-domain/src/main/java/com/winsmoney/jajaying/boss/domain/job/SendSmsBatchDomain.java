package com.winsmoney.jajaying.boss.domain.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.domain.utils.GenerateRedisKeyUtils;
import com.winsmoney.platform.framework.redis.map.MapManager;

@Component
public class SendSmsBatchDomain {
	@Autowired
	private TriggerSendSmsJob triggerSendSmsJob;
	@Autowired
	private MapManager mapManager;
	@Autowired
	private LockDomain lockDomain;


	public void sendSmsBatch(String content, String batchCountkey) {
		saveToRedis(content);
		lockDomain.decrementCount(batchCountkey);
		triggerSendSmsJob.trigger(content);
	}
	
	public void saveToRedis(String content) {
		String key = GenerateRedisKeyUtils.getSmsBatchContentKey();
		mapManager.set(key, content);
	}
}
