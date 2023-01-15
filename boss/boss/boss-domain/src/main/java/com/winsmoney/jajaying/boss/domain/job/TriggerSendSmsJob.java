package com.winsmoney.jajaying.boss.domain.job;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.lite.lifecycle.api.JobAPIFactory;
import com.dangdang.ddframe.job.lite.lifecycle.api.JobOperateAPI;
import com.google.common.base.Optional;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

@Component
public class TriggerSendSmsJob {
	private final static WinsMoneyLogger LOG = WinsMoneyLoggerFactory.getLogger(TriggerSendSmsJob.class);

	public void trigger(String content) {
		String jobName = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("batchSms.jobName");
		String zkNamespace = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("batchSms.zkNamespace");
		String zkIp = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("boss.dubbo.registry_quartz");
		JobOperateAPI jobOperateAPI = JobAPIFactory.createJobOperateAPI(zkIp, zkNamespace, Optional.<String> absent());
		LOG.info(String.format("准备激活定时任务jobName = %s, zkNamespace = %s, zkIp = %s", jobName, zkNamespace, zkIp));
		jobOperateAPI.trigger(Optional.of(jobName), Optional.<String> absent());
		LOG.info(String.format("激活定时任务完成jobName = %s, zkNamespace = %s, zkIp = %s", jobName, zkNamespace, zkIp));
	}
}
