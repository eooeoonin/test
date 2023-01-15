/**
 * Project:boss
 * File:Menu.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: menu Description: date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public class Menu extends BaseModel {

	private static final long serialVersionUID = -1L;

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

}