package com.winsmoney.jajaying.boss.domain.utils;

/**
 * @author ChenKai
 * @date 2016年6月22日
 */
public class Result {
	// 错误代码：0:表示成功，-1:表示错误
	private int resCode;
	private String msg;
	private Object data;

	public Result(int resCode, String msg, Object data) {
		this.resCode = resCode;
		this.msg = msg;
		this.data = data;
	}

	public static Result success(Object data) {
		Result result = new Result(0, "success", data);
		return result;
	}
	public static Result success(String msg,Object data) {
		Result result = new Result(0, msg, data);
		return result;
	}

	public static Result error(String msg) {
		Result result = new Result(-1, msg, null);
		return result;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [resCode=" + resCode + ", msg=" + msg + ", data=" + data + "]";
	}
}