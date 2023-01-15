/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.request;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: userRole Description: Service请求参数 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public class UserRoleReqVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 待扩展角色归属
	 */
	private String extendRole;

	/**
	 * 角色名称id
	 */
	private String roleName;

	/**
	 * 0=业务类型,1=管理类型
	 */
	private String roleType;

	/**
	 * 备注
	 */
	private String remark;

	public String getExtendRole() {
		return extendRole;
	}

	public void setExtendRole(String extendRole) {
		this.extendRole = extendRole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
