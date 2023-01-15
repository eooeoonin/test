package com.winsmoney.jajaying.boss.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MenuBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 排序
	 */
	private Integer displayOrder;
	/**
	 * 优先级
	 */
	private Integer level = 0;
	/**
	 * 类型 1 功能导航 2 功能菜单
	 */
	private String style;
	/**
	 * 名称
	 */
	private String text;
	/**
	 * url
	 */
	private String url;
	// 权限标识
	private String permissionFlag;
	/**
	 * 父级
	 */
	private String parentId;

	/**
	 * 删除标识
	 */
	private Boolean deleted;

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
	 * 最后编辑时间
	 */
	private Date modifyTime;

	/**
	 * 子菜单
	 */
	private List<MenuBo> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermissionFlag() {
		return permissionFlag;
	}

	public void setPermissionFlag(String permissionFlag) {
		this.permissionFlag = permissionFlag;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public List<MenuBo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBo> children) {
		this.children = children;
	}

}
