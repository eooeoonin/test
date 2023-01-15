package com.winsmoney.jajaying.boss.controller.model;

import java.io.Serializable;

import com.winsmoney.jajaying.user.service.enums.AuthState;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.platform.framework.core.util.Money;

public class AccountBalance extends EnterpriseUserReqVo implements Serializable{

	/**
	 * 余额
	 */
	private Money balance;

	/**
	 * 冻结金额
	 */
	private Money freezeAmount;
	
	/**
     * 认证状态： U 未认证 C 公安部什么认证 B 银行卡认证
     */
    private AuthState authState;

    /**
     * 用户激活状态。Normal-正常；Frozen冻结；
     */
//    private UserState userState ;
    
    /**
     * 绑定状态：0=未绑定 、1=已绑定 2=绑定中 -1=绑定失败 -2=解绑
     */
//    private BindStatus bindState ;
    
    

	public AuthState getAuthState() {
		return authState;
	}

	public void setAuthState(AuthState authState) {
		this.authState = authState;
	}

//	public UserState getUserState() {
//		return userState;
//	}
//
//	public void setUserState(UserState userState) {
//		this.userState = userState;
//	}

//	public BindStatus getBindState() {
//		return bindState;
//	}
//
//	public void setBindState(BindStatus bindState) {
//		this.bindState = bindState;
//	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public Money getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Money freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	
	
	
	
	
}
