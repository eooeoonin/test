/**
 * Project:boss
 * File:Department.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 返回结果 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public class DepartmentResVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
	private String id;

	/**
	 * 部门类型
	 */
	private String departmentType;

	/**
	 * 上级部门id
	 */
	private String parentDepartmentId;

	/**
	* 
	*/
	private String isInherit;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 部门编码
	 */
	private String departmentCode;

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

	public String getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	public String getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public String getIsInherit() {
		return isInherit;
	}

	public void setIsInherit(String isInherit) {
		this.isInherit = isInherit;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
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
