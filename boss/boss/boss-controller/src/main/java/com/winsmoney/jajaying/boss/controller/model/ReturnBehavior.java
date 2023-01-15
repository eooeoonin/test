package com.winsmoney.jajaying.boss.controller.model;

import java.util.List;

import com.winsmoney.jajaying.award.service.request.AwardActRequestReqVo;

public class ReturnBehavior extends AwardActRequestReqVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BehaviorInfo>  behaviorInfo;

	public List<BehaviorInfo> getBehaviorInfo(){
		return behaviorInfo;
	}

	public void setBehaviorInfo(List<BehaviorInfo> behaviorInfo) {
		this.behaviorInfo = behaviorInfo;
	}
}
