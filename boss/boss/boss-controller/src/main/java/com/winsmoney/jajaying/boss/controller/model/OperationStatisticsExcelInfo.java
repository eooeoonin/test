/**
 * Project:trade
 * File:OperationStatistics.java
 * Date:2018-06-19
 * Copyright (c) 2018 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.controller.model;

/**
 * ClassName: operationStatistics
 * Description: 
历史累计交易总额：平台累计交易金额；（投资成功的，投资成功之后的不算）；
历史累计交易笔数：平台累计交易笔数；（成功笔数）
借贷余额：结束时间的在途金额；（即：未还借款款用户的钱，在途金额：即用户投资在平台未还给用户的钱，本金）；
当期借贷笔数：通过审核的在资产列表里面的借款个数，开始时间至结束时间内借款个数；
累计出借人数量（累计投资人数）：从上线开始，截止上月末。平台累计投资人数；
累计借款人数量（或借款企业）：从上线开始，截止上月末。平台累计借款人数（或企业借款个数）；
当期出借人数量：开始时间至结束时间内投资人数(当期出借人数量);
当期借款人数量（或者借款企业数量）：开始时间至结束时间内借款人数。(当期出借人数量。);
前十大借款人待还金额占比：（借款金额排名前十的借款人待还总金额）/全部待还总金额（指本金），按项目后台的时间统计;（备注：最后放款时间为准。）
最大单一借款人待还金额占比：借款金额排名第一的借款人待还金额/全部待还总金额（指本金），按项目录入后台的时间统计（备注：最后放款时间为准） Service请求参数
 * date: 2018-06-19 09:11:43
 * @author: CodeCreator
 */
public class OperationStatisticsExcelInfo 
{
    /**
     * 统计月份
     */
    private String infoMonth ;
    
    /**
     * 历史累计交易总额
     */
    private String historyAmount ;
    
    /**
     * 历史累计交易笔数
     */
    private Long historyTrade ;
  
    /**
     * 借贷余额
     */
    private String borrowBalance ;
   
    /**
     * 当期借贷笔数
     */
    private Long currentBorrowTrade ;

    /**
     * 累计出借人数量
     */
    private Long historyLender ;
    
    /**
     * 累计借款人数量
     */
    private Long historyBorrowerNum ; 
    
    /**
     * 当期出借人数量
     */
    private Long currentLender ;
    
    /**
     * 当期借款人数量
     */
    private Long currentBorrowerNum ;  
    
    /**
     * 前十大借款人待还金额
     */
    private String historyTop10amountPercent;  
    
    /**
     * 最大单一借款人待还金额
     */
    private String historyTop1amountPercent ;

    
	public String getInfoMonth() {
		return infoMonth;
	}

	public void setInfoMonth(String infoMonth) {
		this.infoMonth = infoMonth;
	}

	public String getHistoryAmount() {
		return historyAmount;
	}

	public void setHistoryAmount(String historyAmount) {
		this.historyAmount = historyAmount;
	}

	public Long getHistoryTrade() {
		return historyTrade;
	}

	public void setHistoryTrade(Long historyTrade) {
		this.historyTrade = historyTrade;
	}

	public String getBorrowBalance() {
		return borrowBalance;
	}

	public void setBorrowBalance(String borrowBalance) {
		this.borrowBalance = borrowBalance;
	}

	public Long getCurrentBorrowTrade() {
		return currentBorrowTrade;
	}

	public void setCurrentBorrowTrade(Long currentBorrowTrade) {
		this.currentBorrowTrade = currentBorrowTrade;
	}

	public Long getHistoryLender() {
		return historyLender;
	}

	public void setHistoryLender(Long historyLender) {
		this.historyLender = historyLender;
	}

	public Long getHistoryBorrowerNum() {
		return historyBorrowerNum;
	}

	public void setHistoryBorrowerNum(Long historyBorrowerNum) {
		this.historyBorrowerNum = historyBorrowerNum;
	}

	public Long getCurrentLender() {
		return currentLender;
	}

	public void setCurrentLender(Long currentLender) {
		this.currentLender = currentLender;
	}

	public Long getCurrentBorrowerNum() {
		return currentBorrowerNum;
	}

	public void setCurrentBorrowerNum(Long currentBorrowerNum) {
		this.currentBorrowerNum = currentBorrowerNum;
	}

	public String getHistoryTop10amountPercent() {
		return historyTop10amountPercent;
	}

	public void setHistoryTop10amountPercent(String historyTop10amountPercent) {
		this.historyTop10amountPercent = historyTop10amountPercent;
	}

	public String getHistoryTop1amountPercent() {
		return historyTop1amountPercent;
	}

	public void setHistoryTop1amountPercent(String historyTop1amountPercent) {
		this.historyTop1amountPercent = historyTop1amountPercent;
	}

	@Override
	public String toString() {
		return "OperationStatisticsExcelInfo [infoMonth=" + infoMonth + ", historyAmount=" + historyAmount
				+ ", historyTrade=" + historyTrade + ", borrowBalance=" + borrowBalance + ", currentBorrowTrade="
				+ currentBorrowTrade + ", historyLender=" + historyLender + ", historyBorrowerNum=" + historyBorrowerNum
				+ ", currentLender=" + currentLender + ", currentBorrowerNum=" + currentBorrowerNum
				+ ", historyTop10amountPercent=" + historyTop10amountPercent + ", historyTop1amountPercent="
				+ historyTop1amountPercent + "]";
	}

}
