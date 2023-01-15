/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: resource Description: Service请求参数 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public class ResourceReqVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 功能名称
	 */
	private String text;

	/**
	 * 权限和user_role字段的id联系
	 */
	private String roleId;

	/**
     * 
     */
	private String url;

	/**
	 * 备注
	 */
	private String remark;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
