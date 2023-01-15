package com.winsmoney.jajaying.boss.service.exception;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private BossErrorCode errorCode;

	public BusinessException(BossErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BossErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(BossErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "BusinessException [errorCode=" + errorCode.getCode() + " errorDesc=" +errorCode.getDesc()+ "]";
	}
	
}