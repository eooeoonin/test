/**
 * Project:boss
 * File:Resource.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.po;

import java.io.Serializable;

/**
 * Description: Po定义 date: 2016-08-03 06:03:17
 * 
 * @author: CodeCreator
 */
public class ResourceFlagPo extends ResourcePo implements Serializable {
	private static final long serialVersionUID = 1L;

	// 权限标志
	private String permissionFlag;

	public String getPermissionFlag() {
		return permissionFlag;
	}

	public void setPermissionFlag(String permissionFlag) {
		this.permissionFlag = permissionFlag;
	}

}