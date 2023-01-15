package com.winsmoney.jajaying.boss.service.exception;


public enum BossErrorCode {
	SUCCESS("000000", "成功"),
	PARAM_ERROR("999998", "参数错误"),
	ERROR("999999", "系统异常");
	BossErrorCode(String code, String desc) {
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
