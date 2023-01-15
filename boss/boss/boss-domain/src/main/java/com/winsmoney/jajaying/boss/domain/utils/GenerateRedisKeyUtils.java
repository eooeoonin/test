package com.winsmoney.jajaying.boss.domain.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.winsmoney.platform.framework.core.util.DateUtil;

@Component
public class GenerateRedisKeyUtils {

	private static String systemCode;// ="L02";

	@Value("${systemCode}")
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public static String getSystemCode() {
		return systemCode;
	}

	public static String getMenusLoginedKey(String roleId) {
		StringBuffer sb = new StringBuffer();
		return sb.append(systemCode).append(":").append("menus").append(":").append(roleId).toString();
	}
	
	public static String getSmsBatchContentKey() {
		StringBuffer sb = new StringBuffer();
		return sb.append(systemCode).append(":").append("smsbatchContent").toString();
	}
	public static String getFirstSmsBatchLockKey() {
		StringBuffer sb = new StringBuffer();
		return sb.append(systemCode).append(":").append("smsbatchFirstKey").toString();
	}

	
	public static String getSmsBatchCountKey() {
		StringBuffer sb = new StringBuffer();
		String dateStr = DateUtil.formatToYYYYMMDDSlash(new Date());
		return sb.append(systemCode).append(":").append("smsbatchCount").append(":").append(dateStr).toString();
	}

	public static String getFirstShardingCountKey(String shardingParameter) {
		StringBuffer sb = new StringBuffer();
		return sb.append(systemCode).append(":").append("smsbatchFirstShardingKey").append(shardingParameter).toString();
	}
	public static String getShardingCountKey(String shardingParameter) {
		StringBuffer sb = new StringBuffer();
		String dateStr = DateUtil.formatToYYYYMMDDSlash(new Date());
		return sb.append(systemCode).append(":").append("shardingCount").append(":").append(dateStr).append(shardingParameter).toString();
	}

	
	
}
