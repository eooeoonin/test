package com.winsmoney.jajaying.boss.domain.exception;


public enum BossErrorCode {
	SUCCESS("000000", "成功"), 
	PAGE_ERROR("900001", "分页发送短信时某一分页出错"), 
	SHARD_REDIS_ERROR("900002", "批量发送短信时分片参数次数扣减时异常"),
	PARAM_ERROR("999998", "参数错误"),
	ERROR("999999", "系统异常");
	private BossErrorCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
