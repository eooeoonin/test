/**
 * Project:boss
 * File:UserRole.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 返回结果 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public class UserRoleResVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private String id;

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

	/**
	 * 状态预留
	 */
	private String status;

	/**
	 * 最后编辑人
	 */
	private String editedBy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
