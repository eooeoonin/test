package com.winsmoney.jajaying.boss.controller.model;

import com.winsmoney.jajaying.crowdfunding.service.response.SubProductInfoResVo;

public class SubProductInfoResForm extends SubProductInfoResVo {
	private static final long serialVersionUID = 1L;
	private String editFlag = "0";

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

}
