package com.winsmoney.jajaying.boss.controller.model;

public class BorrowExtends {

	/**
	 * 风控措施Id
	 */
	private String riskId;
	/**
	 * 风控措施
	 */
	private String risk;
	/**
	 * 安全保障
	 */
	private String purpose;
	/**
	 * 安全保障ID
	 */
	private String purposeId;
	/**
	 * 审批内容
	 */
	private String approveContent;
	/**
	 * 审批人
	 */
	private String approver;
	/**
	 * 审批结果
	 */
	private String approveResult;
	public String getRiskId() {
		return riskId;
	}
	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
	}
	public String getApproveContent() {
		return approveContent;
	}
	public void setApproveContent(String approveContent) {
		this.approveContent = approveContent;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	
	
}
