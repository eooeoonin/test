package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.trade.service.enums.LoanType;
import com.winsmoney.jajaying.trade.service.request.LoanExtendReqVo;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wei on 2016/8/24.
 */
public class BatchProject implements Serializable {

    private static final long serialVersionUID = 7325997888143363200L;
    /**
     * 项目名称
     */
    private String title ;
    /**
     * 项目类型
     */
    private LoanType loanType;
    /**
     * 项目利率
     */
    private BigDecimal yearRate;
    /**
     * 浮动利率
     */
    private BigDecimal floatRate;
    /**
     * 投资密码
     */
    private String investInvetCode;
    /**
     * 项目总额
     */
    private Money amount;
    /**
     * 提前还款
     */
    private Boolean earlyRepayment;
    /**
     * 开始募集时间
     */
    private Date openTime ;
    /**
     * 起息日
     */
    private Date interestStartDate ;
    /**
     * 最后结息日
     */
    private Date interestEndDate ;
    /**
     * 购买权限
     */
    private String investGroupCode ;
    /**
    *扩展字段
    */
   /* private List<LoanExtendReqVo> loanExtendList;

    public List<LoanExtendReqVo> getLoanExtendList() {
		return loanExtendList;
	}

	public void setLoanExtendList(List<LoanExtendReqVo> loanExtendList) {
		this.loanExtendList = loanExtendList;
	}*/

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getYearRate() {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate) {
        this.yearRate = yearRate;
    }

    public String getInvestInvetCode() {
        return investInvetCode;
    }

    public void setInvestInvetCode(String investInvetCode) {
        this.investInvetCode = investInvetCode;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Boolean getEarlyRepayment() {
        return earlyRepayment;
    }

    public void setEarlyRepayment(Boolean earlyRepayment) {
        this.earlyRepayment = earlyRepayment;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
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

    public String getInvestGroupCode() {
        return investGroupCode;
    }

    public void setInvestGroupCode(String investGroupCode) {
        this.investGroupCode = investGroupCode;
    }

	public BigDecimal getFloatRate() {
		return floatRate;
	}

	public void setFloatRate(BigDecimal floatRate) {
		this.floatRate = floatRate;
	}
    
}