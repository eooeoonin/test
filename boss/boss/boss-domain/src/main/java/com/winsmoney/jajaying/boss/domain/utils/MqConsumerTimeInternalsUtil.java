package com.winsmoney.jajaying.boss.domain.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqConsumerTimeInternalsUtil {

	public static final String EXCEPTION_TIME_INTERNAL = "Exception_Time_Internal";

	@Value("${timeIntervals:3|5|9}")
	private String timeIntervals;


	public int[] getExceptionTimeIntervals() {
		int[] exceptionTimeIntervals = null;
		if (StringUtils.isBlank(timeIntervals)) {
			throw new IllegalArgumentException("MQ时间间隔没有配置");
		}
		if (timeIntervals.indexOf("|") == -1) {
			throw new IllegalArgumentException("异常时间间隔格式不对，应该为xx|xx|xx");
		}
		String[] timeStrs = timeIntervals.split("\\|");
		exceptionTimeIntervals = new int[timeStrs.length];
		for (int i = 0; i < timeStrs.length; i++) {
			exceptionTimeIntervals[i] = Integer.parseInt(timeStrs[i]);
		}
		return exceptionTimeIntervals;
	}

	// * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
	private static int ZERO_DELAY=2;//5s
	private static int FIRST_DELAY = 6;// 2m
	private static int SECOND_DELAY = 14;// 10m
	private static int THIRD_DELAY = 18;// 2h

	/**
	 * 重试三次，第一次间隔2分钟；第二次间隔10分钟；第三次间隔2小时
	 * 
	 * @param mqReceiveTime 接受次数
	 * @return 延迟级别
	 */
	public static int getDelayLevel(int mqReceiveTime) {
		if(mqReceiveTime == 0){
			return ZERO_DELAY;
		}
		if (mqReceiveTime == 1) {
			return FIRST_DELAY;
		}
		if (mqReceiveTime == 2) {
			return SECOND_DELAY;
		}
		if (mqReceiveTime == 3) {
			return THIRD_DELAY;
		}
		return THIRD_DELAY;
	}

}
