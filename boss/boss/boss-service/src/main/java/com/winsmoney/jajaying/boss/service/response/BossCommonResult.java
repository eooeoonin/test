package com.winsmoney.jajaying.boss.service.response;

import com.winsmoney.platform.framework.core.runtime.CommonResult;

/**
 * Description: boss 通用返回结果 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public class BossCommonResult<B> extends CommonResult<B> {
	public BossCommonResult(String resultCode, String resultCodeMsg, B businessObject) {
		super(resultCode, resultCodeMsg, businessObject);
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	@Override
	public boolean isSuccess() {
		return this.getResultCode().equals("1");
	}
}
