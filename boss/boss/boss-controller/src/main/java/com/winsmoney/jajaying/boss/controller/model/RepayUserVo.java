package com.winsmoney.jajaying.boss.controller.model;

import java.io.Serializable;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepayUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;

	/**
	 * 借款ID
	 */
	private String borrowId;

	/**
	 * 借款名称
	 */
	private String borrowName;

	/**
	 * 三方代偿用户ID
	 */
	private String otherRepayUserId;

	/**
	 * 三方代偿用户名称
	 */
	private String otherRepayUserName;

	/**
	 * 
	 */
	private Date createTime;

	/**
	 * 
	 */
	private Date modifyTime;

	/**
	 * OA编号
	 */
	private String oaFlowCode;

	/**
	 * 
	 */
	private String status;

	/**
	 * 
	 */
	private String editedBy;

	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 企业名称
	 */
	private String enterpriseName;
	/**
	 * 证件号
	 */
	private String certId;
}
