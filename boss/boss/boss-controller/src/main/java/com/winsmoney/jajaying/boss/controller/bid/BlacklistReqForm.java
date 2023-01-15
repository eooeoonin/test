package com.winsmoney.jajaying.boss.controller.bid;

import java.io.Serializable;
import java.util.Date;


public class BlacklistReqForm implements Serializable{
	/*
	 * 手机号
	 */
	private String registerMobile;
	
	/*
	 * 黑名单创建时间
	 */
	private Date blackCreateTime;

	public String getRegisterMobile() {
		return registerMobile;
	}
	  //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //总记录数
    private long total;
    //总页数
    private int pages;
    
    
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setRegisterMobile(String registerMobile) {
		this.registerMobile = registerMobile;
	}

	public Date getBlackCreateTime() {
		return blackCreateTime;
	}

	public void setBlackCreateTime(Date blackCreateTime) {
		this.blackCreateTime = blackCreateTime;
	}

	
	


}
