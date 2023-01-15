/**
 * Project:boss
 * File:Resource.java
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
public class ResourceResVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* 
	*/
	private String id;

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
