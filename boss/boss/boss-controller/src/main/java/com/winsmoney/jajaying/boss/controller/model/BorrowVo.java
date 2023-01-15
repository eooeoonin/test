package com.winsmoney.jajaying.boss.controller.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.winsmoney.jajaying.trade.service.enums.BorrowRepayType;
import com.winsmoney.jajaying.trade.service.enums.BorrowStatus;
import com.winsmoney.jajaying.trade.service.enums.BorrowType;
import com.winsmoney.jajaying.trade.service.enums.BorrowerType;
import com.winsmoney.jajaying.trade.service.enums.TermType;
import com.winsmoney.jajaying.trade.service.response.BorrowExtendResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowFileResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowRepayPlanResVo;
import com.winsmoney.jajaying.trade.service.response.LoanRepayRecordResVo;
import com.winsmoney.platform.framework.core.util.Money;

public class BorrowVo {
	/**
	 * 批贷期限
	 */
	private Integer approveTerm;
	/**
	 * OA流程编号
	 */
	private String oaFlowCode;
	/**
	 * 申请金额
	 */
	private Money applyAmount;

	public Money getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * 借款人类型
	 */
	private BorrowerType borrowerType;
	public BorrowerType getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(BorrowerType borrowerType) {
		this.borrowerType = borrowerType;
	}

	public Integer getBorrowTerm() {
		return borrowTerm;
	}

	public void setBorrowTerm(Integer borrowTerm) {
		this.borrowTerm = borrowTerm;
	}

	public TermType getTermType() {
		return termType;
	}

	public void setTermType(TermType termType) {
		this.termType = termType;
	}
	/**
	 * 担保人ID
	 */
	private String guaranteeUserId;
	/**
	 * 担保人Name
	 */
	private String guaranteeUserName;
	/**
	 * 担保人CARD
	 */
	private String guaranteeUserCard;
	/**
	 * 借款期限
	 */
	private Integer borrowTerm;
	/**
	 * 期限类型 MONTH DAY
	 */
	private TermType termType;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * 借款编号
	 */
	private String id;
	/**
	 * 借款用户编号
	 */
	private String borrowUserCode;
	/**
	 * 最后放款时间
	 */
	private Date lastReleaseTime;
	/**
	 * 最后还款时间
	 */
	private Date lastRepayTime;
	/**
	 * 还款类型
	 */
	private BorrowRepayType repayType;
	/**
	 * 借款金额
	 */
	private Money borrowAmount;
	/**
	 * 已经借款金额
	 */
	private Money borrowedAmount;
	/**
	 * 实际募集金额
	 */
	private Money collectedAmount;
	/**
	 * 可用金额
	 */
	private Money remainAmount;
	/**
	 * 借款名称
	 */
	private String borrowTitle;
	/**
	 * 手续费
	 */
	private Money borrowFee;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 逻辑删除
	 */
	private String deleted;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 借款类型
	 */
	private String borrowType;
	/**
	 * 借款利率
	 */
	private BigDecimal borrowRate;
	/**
	 * 阶段、期数
	 */
	private Integer phase;
	/**
	 * 借款还款 阶段、期数
	 */
	private Integer basePhase;
	/**
	 * 修改人
	 */
	private String editedBy;
	/**
	 * 状态
	 */
	private BorrowStatus status;

	/**
	 * 借款扩展
	 */
	private List<BorrowExtendResVo> borrowExtendList;
	/**
	 * 借款文件
	 */
	private List<BorrowFileResVo> borrowFileList;

	/**
	 * 借款人名称
	 */
	private String borrowUserName;

	/**
	 * 借款时间
	 */
	private String borrowDates;

	/**
	 * 下期还款期数
	 */
	private String nextPhase;

	/**
	 * 借款总期数
	 */
	private String totalPhase;

	/**
	 * 下期还款金额
	 */
	private Money nextReapyAmount;

	/**
	 * 下期还款日期
	 */
	private Date nextRepayTime;

	/**
	 * 借款人账户余额
	 */
	private Money balance;

	private List<InterestPlans> interestPlans;
	
	private List<LoanRepayRecordResVo> loanRepayRecordResVo;
	
	public List<LoanRepayRecordResVo> getLoanRepayRecordResVo() {
		return loanRepayRecordResVo;
	}

	public void setLoanRepayRecordResVo(List<LoanRepayRecordResVo> loanRepayRecordResVo) {
		this.loanRepayRecordResVo = loanRepayRecordResVo;
	}

	private List<BorrowRepayPlanResVo> borrowRepayPlans;
	/**
	 * 剩余天数
	 */
	private String remainingDays;

	// 系统当前时间，用于"提前还款"、"还款"、"逾期还款"的按钮显示
	private Date nowDate;
	//投资周期
	private String investCycle;
	
	/**
     * 借款合同ID
     */
    private String protocolId;
    

	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBorrowUserCode() {
		return borrowUserCode;
	}

	public void setBorrowUserCode(String borrowUserCode) {
		this.borrowUserCode = borrowUserCode;
	}

	public Date getLastReleaseTime() {
		return lastReleaseTime;
	}

	public void setLastReleaseTime(Date lastReleaseTime) {
		this.lastReleaseTime = lastReleaseTime;
	}

	public Date getLastRepayTime() {
		return lastRepayTime;
	}

	public void setLastRepayTime(Date lastRepayTime) {
		this.lastRepayTime = lastRepayTime;
	}

	public BorrowRepayType getRepayType() {
		return repayType;
	}

	public void setRepayType(BorrowRepayType repayType) {
		this.repayType = repayType;
	}

	public Money getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Money borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Money getBorrowedAmount() {
		return borrowedAmount;
	}

	public void setBorrowedAmount(Money borrowedAmount) {
		this.borrowedAmount = borrowedAmount;
	}

	public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public Money getBorrowFee() {
		return borrowFee;
	}

	public void setBorrowFee(Money borrowFee) {
		this.borrowFee = borrowFee;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = BorrowType.getType(borrowType).getName();
	}

	public BigDecimal getBorrowRate() {
		if (null != borrowRate) {
			return borrowRate.divide(new BigDecimal(100));
		}else {
			return borrowRate;
		}
	}

	public void setBorrowRate(BigDecimal borrowRate) {
		this.borrowRate = borrowRate;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public BorrowStatus getStatus() {
		return status;
	}

	public void setStatus(BorrowStatus status) {
		this.status = status;
	}

	public List<BorrowExtendResVo> getBorrowExtendList() {
		return borrowExtendList;
	}

	public void setBorrowExtendList(List<BorrowExtendResVo> borrowExtendList) {
		this.borrowExtendList = borrowExtendList;
	}

	public List<BorrowFileResVo> getBorrowFileList() {
		return borrowFileList;
	}

	public void setBorrowFileList(List<BorrowFileResVo> borrowFileList) {
		this.borrowFileList = borrowFileList;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getBorrowDates() {
		return borrowDates;
	}

	public void setBorrowDates(String borrowDates) {
		this.borrowDates = borrowDates;
	}

	public String getNextPhase() {
		return nextPhase;
	}

	public void setNextPhase(String nextPhase) {
		this.nextPhase = nextPhase;
	}

	public String getTotalPhase() {
		return totalPhase;
	}

	public void setTotalPhase(String totalPhase) {
		this.totalPhase = totalPhase;
	}

	public Money getNextReapyAmount() {
		return nextReapyAmount;
	}

	public void setNextReapyAmount(Money nextReapyAmount) {
		this.nextReapyAmount = nextReapyAmount;
	}

	public Date getNextRepayTime() {
		return nextRepayTime;
	}

	public void setNextRepayTime(Date nextRepayTime) {
		this.nextRepayTime = nextRepayTime;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
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

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Integer getBasePhase() {
		return basePhase;
	}

	public void setBasePhase(Integer basePhase) {
		this.basePhase = basePhase;
	}

	public List<BorrowRepayPlanResVo> getBorrowRepayPlans() {
		return borrowRepayPlans;
	}

	public void setBorrowRepayPlans(List<BorrowRepayPlanResVo> borrowRepayPlans) {
		this.borrowRepayPlans = borrowRepayPlans;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public String getInvestCycle() {
		return investCycle;
	}

	public void setInvestCycle(String investCycle) {
		this.investCycle = investCycle;
	}

	public Money getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(Money collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public Money getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Money remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getGuaranteeUserId() {
		return guaranteeUserId;
	}

	public void setGuaranteeUserId(String guaranteeUserId) {
		this.guaranteeUserId = guaranteeUserId;
	}

	public String getGuaranteeUserName() {
		return guaranteeUserName;
	}

	public void setGuaranteeUserName(String guaranteeUserName) {
		this.guaranteeUserName = guaranteeUserName;
	}

	public String getGuaranteeUserCard() {
		return guaranteeUserCard;
	}

	public void setGuaranteeUserCard(String guaranteeUserCard) {
		this.guaranteeUserCard = guaranteeUserCard;
	}

	public String getOaFlowCode() {
		return oaFlowCode;
	}

	public void setOaFlowCode(String oaFlowCode) {
		this.oaFlowCode = oaFlowCode;
	}

	public Integer getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}

}
