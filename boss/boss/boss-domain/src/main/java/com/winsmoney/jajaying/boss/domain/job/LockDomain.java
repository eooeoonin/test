package com.winsmoney.jajaying.boss.domain.job;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.basedata.service.client.SystemConfigClient;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.SystemConfigResVo;
import com.winsmoney.jajaying.boss.domain.exception.BossErrorCode;
import com.winsmoney.jajaying.boss.domain.exception.BusinessException;
import com.winsmoney.platform.framework.redis.api.LockManager;
import com.winsmoney.platform.framework.redis.lock.WinsMoneyLock;
import com.winsmoney.platform.framework.redis.map.MapManager;
import com.winsmoney.platform.framework.redis.support.IAtomic;

@Component
public class LockDomain {
	@Autowired
	private IAtomic<String> redisAtomicManager;
	@Autowired
	private MapManager mapManager;
	@Autowired
	private SystemConfigClient systemConfigClient;
	@Autowired
	private WinsMoneyLock winsMoneyLock;

	@Resource(name = "lockManager")
	LockManager lockManager;

	public boolean redisAutomicCanSend(String redisKey) {
		boolean redisCanSend = true;
		if (redisAtomicManager.isExist(redisKey)) {
			Long count = redisAtomicManager.getLong(redisKey);
			if (count <= 0) {
				redisCanSend = false;
			}
		}

		return redisCanSend;
	}

	public void decrementCount(String batchCountkey) {
		try {
			if (!redisAtomicManager.isExist(batchCountkey)) {
				redisAtomicManager.set(batchCountkey, 1, 1, TimeUnit.DAYS);
			}
			redisAtomicManager.decrementAndGet(batchCountkey, 1);
		} catch (Exception e) {
			throw new BusinessException(BossErrorCode.SHARD_REDIS_ERROR);
		}

	}

	@Deprecated
	public boolean lockFirst(String key, long waitTime, long leaseTime, TimeUnit unit) {
		return winsMoneyLock.lock(key, waitTime, leaseTime, unit);
	}
	@Deprecated
	public boolean unLockFirst(String key) {
		return winsMoneyLock.unlock(key);
	}

	public boolean canSend(String key) {
		String smsBatchSpecial = getFromBaseData("SmsBatchSpecial");
		boolean canSend = redisAutomicCanSend(key);
		if (canSend || "Y".equals(smsBatchSpecial)) {
			return true;
		} else {
			return false;
		}
	}

	public String getFromBaseData(String key) {
		BasedataCommonResult<SystemConfigResVo> systemConfig = systemConfigClient.getSystemConfig(key);
		SystemConfigResVo businessObject = systemConfig.getBusinessObject();
		return businessObject.getCodeValue();
	}

}
