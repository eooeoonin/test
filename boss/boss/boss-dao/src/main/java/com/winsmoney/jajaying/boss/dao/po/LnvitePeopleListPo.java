package com.winsmoney.jajaying.boss.dao.po;

import java.util.Date;

import com.winsmoney.platform.framework.core.util.Money;

public class LnvitePeopleListPo {
	
	 /**
		* 注册手机
		*/
	private String registerMobile ;
	/**
	 * 微信昵称
	 */
	private String nickName ;
	 /**
		* 认证状态：INIT 未认证 IDCARD 实名认证 BANKCARD 绑定银行卡
		*/
	private String lnvitePeopleStatus;
	
	/**
	* 邀请收益
	*/
	private Money inviteAmount ;
	private Date createTime;
	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLnvitePeopleStatus() {
		return lnvitePeopleStatus;
	}
	public void setLnvitePeopleStatus(String lnvitePeopleStatus) {
		this.lnvitePeopleStatus = lnvitePeopleStatus;
	}
	public String getRegisterMobile() {
		return registerMobile;
	}
	public void setRegisterMobile(String registerMobile) {
		this.registerMobile = registerMobile;
	}
	public Money getInviteAmount() {
		return inviteAmount;
	}
	public void setInviteAmount(Money inviteAmount) {
		this.inviteAmount = inviteAmount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
