package com.winsmoney.jajaying.boss.domain.model;

import java.io.Serializable;
import java.util.List;

import com.winsmoney.jajaying.user.service.response.EnterpriseEditLogResVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserAccountInfoResVo;
import com.winsmoney.platform.framework.core.util.Money;

public class EnterpriseUserResBo extends EnterpriseUserResVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int borrowCount;

	private List<EnterpriseEditLogResVo> logs;

	private UserAccountInfoResVo userAccountInfoResVo;

	// 红包余额
	private Money redMoney;

	public int getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(int borrowCount) {
		this.borrowCount = borrowCount;
	}

	public List<EnterpriseEditLogResVo> getLogs() {
		return logs;
	}

	public void setLogs(List<EnterpriseEditLogResVo> logs) {
		this.logs = logs;
	}

	public UserAccountInfoResVo getUserAccountInfoResVo() {
		return userAccountInfoResVo;
	}

	public void setUserAccountInfoResVo(UserAccountInfoResVo userAccountInfoResVo) {
		this.userAccountInfoResVo = userAccountInfoResVo;
	}

	public Money getRedMoney() {
		return redMoney;
	}

	public void setRedMoney(Money redMoney) {
		this.redMoney = redMoney;
	}

}
