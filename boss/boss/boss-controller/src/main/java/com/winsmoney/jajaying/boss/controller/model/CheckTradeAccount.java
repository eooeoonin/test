package com.winsmoney.jajaying.boss.controller.model;

import java.io.Serializable;
import java.util.Date;

public class CheckTradeAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date tradeCheckDate;
	
	private String id;

	public Date getTradeCheckDate() {
		return tradeCheckDate;
	}

	public void setTradeCheckDate(Date tradeCheckDate) {
		this.tradeCheckDate = tradeCheckDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
