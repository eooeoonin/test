/**
 * Project:trade
 * File:Loan.java
 * Date:2016-08-01
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.controller.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.trade.service.enums.BorrowRepayType;
import com.winsmoney.jajaying.trade.service.enums.LoanType;
import com.winsmoney.platform.framework.core.util.Money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Description: 标的 loan 返回结果
* date: 2016-08-01 10:15:09
* @author: CodeCreator
*/
public class LoanVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
	* 标的编码
	*/
	private String id ;
    /**
	* 标的状态
	*/
	private String status ;
    /**
	* 逻辑删除位
	*/
	private Boolean deleted ;
    /**
	* 创建时间
	*/
	private Date createTime ;
    /**
	* 修改时间
	*/
	private Date modifyTime ;
    /**
	* 修改人
	*/
	private String editedBy ;
	
	/**
	 * 起息日
	 */
	private Date interestStartDate ;
	/**
	 * 结息日
	 */
	private Date interestEndDate ;

	/**
	 * 借款编码
	 */
	private String borrowerCode;
	/**
	 * 合同编码
	 */
	private String contractNo;
	/**
	 * 标题名称
	 */
	private String title;
	/**
	 * 标的利率
	 */
	private BigDecimal yearRate;
	/**
	 * 标的浮动利率
	 */
	private BigDecimal floatRate;
	/**
	 * 标的金额
	 */
	private Money amount;
	/**
	 * 已投金额
	 */
	private Money biddingAmount;
	/**
	 * 发生逾期次数
	 */
	private Integer overDued;
	/**
	 * 推荐等级
	 */
	private String recommendOrder;
	/**
	 * 标的类型
	 */
	private LoanType loanType;
	/**
	 * 是否可用红包
	 */
	private Boolean useRedPacket;
	/**
	 * 是否提前还款
	 */
	private Boolean earlyRepayment;
	/**
	 * 起投金额（单位：分）
	 */
	private Money beginInvest;
	/**
	 * 投资步长（单位：分）
	 */
	private Money stepInvest;
	/**
	 * 最大投资额度（单位：分）
	 */
	private Money maxInvest;
	/**
	 * 开标时间
	 */
	private Date openTime;
	/**
	 * 结束募集时间
	 */
	private Date openEndTime;
	/**
	 * 满标时间
	 */
	private Date fullTime;
	/**
	 * 放标时间
	 */
	private Date releaseTime;
	/**
	 * 完标时间
	 */
	private Date endTime;
	/**
	 * 投资邀请码
	 */
	private String investInvetCode;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 投资用户组
	 */
	private String investGroupCode;
	
	/**
	* 还款类型
	*/
	private BorrowRepayType repayType ;
	
	private List<InterestPlans> interestPlans; 
	
	private PageInfo<List<InterestPlans>> interestPlansPages; 
	
	/**
	 * 剩余天数
	 */
	private String remainingDays;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getInterestStartDate() {
		return interestStartDate;
	}

	public void setInterestStartDate(Date interestStartDate) {
		this.interestStartDate = interestStartDate;
	}

	public Date getInterestEndDate() {
		return interestEndDate;
	}

	public void setInterestEndDate(Date interestEndDate) {
		this.interestEndDate = interestEndDate;
	}

	public String getBorrowerCode() {
		return borrowerCode;
	}

	public void setBorrowerCode(String borrowerCode) {
		this.borrowerCode = borrowerCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}

	public BigDecimal getFloatRate() {
		return floatRate;
	}

	public void setFloatRate(BigDecimal floatRate) {
		this.floatRate = floatRate;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public Money getBiddingAmount() {
		return biddingAmount;
	}

	public void setBiddingAmount(Money biddingAmount) {
		this.biddingAmount = biddingAmount;
	}

	public Integer getOverDued() {
		return overDued;
	}

	public void setOverDued(Integer overDued) {
		this.overDued = overDued;
	}

	public String getRecommendOrder() {
		return recommendOrder;
	}

	public void setRecommendOrder(String recommendOrder) {
		this.recommendOrder = recommendOrder;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Boolean getUseRedPacket() {
		return useRedPacket;
	}

	public void setUseRedPacket(Boolean useRedPacket) {
		this.useRedPacket = useRedPacket;
	}

	public Boolean getEarlyRepayment() {
		return earlyRepayment;
	}

	public void setEarlyRepayment(Boolean earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}

	public Money getBeginInvest() {
		return beginInvest;
	}

	public void setBeginInvest(Money beginInvest) {
		this.beginInvest = beginInvest;
	}

	public Money getStepInvest() {
		return stepInvest;
	}

	public void setStepInvest(Money stepInvest) {
		this.stepInvest = stepInvest;
	}

	public Money getMaxInvest() {
		return maxInvest;
	}

	public void setMaxInvest(Money maxInvest) {
		this.maxInvest = maxInvest;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getOpenEndTime() {
		return openEndTime;
	}

	public void setOpenEndTime(Date openEndTime) {
		this.openEndTime = openEndTime;
	}

	public Date getFullTime() {
		return fullTime;
	}

	public void setFullTime(Date fullTime) {
		this.fullTime = fullTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInvestInvetCode() {
		return investInvetCode;
	}

	public void setInvestInvetCode(String investInvetCode) {
		this.investInvetCode = investInvetCode;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getInvestGroupCode() {
		return investGroupCode;
	}

	public void setInvestGroupCode(String investGroupCode) {
		this.investGroupCode = investGroupCode;
	}

	public BorrowRepayType getRepayType() {
		return repayType;
	}

	public void setRepayType(BorrowRepayType repayType) {
		this.repayType = repayType;
	}

	public List<InterestPlans> getInterestPlans() {
		return interestPlans;
	}

	public void setInterestPlans(List<InterestPlans> interestPlans) {
		this.interestPlans = interestPlans;
	}

	public String getRemainingDays() {
		return remainingDays;
	}

	public void setRemainingDays(String remainingDays) {
		this.remainingDays = remainingDays;
	}

	public PageInfo<List<InterestPlans>> getInterestPlansPages() {
		return interestPlansPages;
	}

	public void setInterestPlansPages(PageInfo<List<InterestPlans>> interestPlansPages) {
		this.interestPlansPages = interestPlansPages;
	}
}