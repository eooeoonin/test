package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wei on 2016/8/20.
 */
public class LoanInvestUserForm implements Serializable{
    private static final long serialVersionUID = 3226595747354738494L;
    /**
     * 投资记录列表
     */
    private List<InvestUserForm> investUserForms;
    /**
     * 项目总额
     */
    private Money loanAmount;

    /**
     * 已投金额
     */
    private Money biddingAmount;
    /**
     * 使用红包
     */
    private Money redmoneyAmount;
    public Money getUseCashAmount() {
		return useCashAmount;
	}

	public void setUseCashAmount(Money useCashAmount) {
		this.useCashAmount = useCashAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * 实付总额
     */
    private Money cashAmount;
    /**
     * 投资笔
     */
    private Integer investCount;
	/**
	 * 使用现金劵金额
	 */
	private Money useCashAmount;

    public List<InvestUserForm> getInvestUserForms() {
        return investUserForms;
    }

    public void setInvestUserForms(List<InvestUserForm> investUserForms) {
        this.investUserForms = investUserForms;
    }

    public Money getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Money loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Money getBiddingAmount() {
        return biddingAmount;
    }

    public void setBiddingAmount(Money biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    public Money getRedmoneyAmount() {
        return redmoneyAmount;
    }

    public void setRedmoneyAmount(Money redmoneyAmount) {
        this.redmoneyAmount = redmoneyAmount;
    }

    public Money getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Money cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getInvestCount() {
        return investCount;
    }

    public void setInvestCount(Integer investCount) {
        this.investCount = investCount;
    }
}
