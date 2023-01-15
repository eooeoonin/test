package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.trade.service.response.InterestPlansResVo;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wei on 2016/8/22.
 */
public class RepaymentForm implements Serializable {
    private static final long serialVersionUID = 7537916167345855249L;
    /**
     * 项目名称
     */
    private String loanTitle;
    /**
     * 关联借款
     */
    private String borrowTile;
    /**
     * 借款企业
     */
    private String enterpriseUserName;
    /**
     * 账户余额
     */
    private Money enterpriseUserBalance;
    /**
     * 借款利率
     */
    private BigDecimal borrowRate;
    /**
     * 项目利率
     */
    private BigDecimal loanRate;

    private List<InterestPlansResVo> interestPlansResVos;

    public String getLoanTitle() {
        return loanTitle;
    }

    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    public String getBorrowTile() {
        return borrowTile;
    }

    public void setBorrowTile(String borrowTile) {
        this.borrowTile = borrowTile;
    }

    public String getEnterpriseUserName() {
        return enterpriseUserName;
    }

    public void setEnterpriseUserName(String enterpriseUserName) {
        this.enterpriseUserName = enterpriseUserName;
    }

    public Money getEnterpriseUserBalance() {
        return enterpriseUserBalance;
    }

    public void setEnterpriseUserBalance(Money enterpriseUserBalance) {
        this.enterpriseUserBalance = enterpriseUserBalance;
    }

    public BigDecimal getBorrowRate() {
        return borrowRate;
    }

    public void setBorrowRate(BigDecimal borrowRate) {
        this.borrowRate = borrowRate;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public List<InterestPlansResVo> getInterestPlansResVos() {
        return interestPlansResVos;
    }

    public void setInterestPlansResVos(List<InterestPlansResVo> interestPlansResVos) {
        this.interestPlansResVos = interestPlansResVos;
    }
}
