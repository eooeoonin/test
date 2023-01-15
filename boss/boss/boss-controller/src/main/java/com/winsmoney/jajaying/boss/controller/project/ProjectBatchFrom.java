package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.trade.service.enums.BorrowRepayType;
import com.winsmoney.jajaying.trade.service.enums.LoanExtendType;
import com.winsmoney.jajaying.trade.service.enums.LoanType;
import com.winsmoney.jajaying.trade.service.request.LoanExtendReqVo;
import com.winsmoney.jajaying.trade.service.validation.First;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by wei on 2016/8/24.
 */
public class ProjectBatchFrom implements Serializable {
    private static final long serialVersionUID = -4235924681153837950L;
    /**
     * 项目描述扩展类型
     */
    private LoanExtendType loanextendType;
    /**
     * 借款编号
     */
    private String borrowerCode;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 还款类型
     */
    private BorrowRepayType repayType;
    /**
     * 起投金额
     */
    private Money beginInvest ;
    /**
     * 递增金额
     */
    private Money stepInvest;
    /**
     * 是否使用红包
     */
    private Boolean useRedPacket;
    /**
     * 加息劵使用标识
     */
    private Boolean addInterestCardFlag;
    
    /**
     * 现金劵使用标识
     */
    private Boolean cashCardFlag;
    public Boolean getAddInterestCardFlag() {
		return addInterestCardFlag;
	}

	public void setAddInterestCardFlag(Boolean addInterestCardFlag) {
		this.addInterestCardFlag = addInterestCardFlag;
	}

	public Boolean getCashCardFlag() {
		return cashCardFlag;
	}

	public void setCashCardFlag(Boolean cashCardFlag) {
		this.cashCardFlag = cashCardFlag;
	}

	/**
     * 最大可投金额
     */
    private Money maxInvest ;
    /**
     * 项目描述
     */
    private String loanExtendMark;
    
    /**
     * 红包可使用比例
     */
    private String redMoneyScale;
    
    /**
     * 还款描述
     */
    private String repayDesc;
    /**
     * 自动投标最大可投金额
     */
    private Money autoInvestMaxAmount;
    
    
    

    public Money getAutoInvestMaxAmount() {
		return autoInvestMaxAmount;
	}

	public void setAutoInvestMaxAmount(Money autoInvestMaxAmount) {
		this.autoInvestMaxAmount = autoInvestMaxAmount;
	}

	public String getRedMoneyScale() {
		return redMoneyScale;
	}

	public void setRedMoneyScale(String redMoneyScale) {
		this.redMoneyScale = redMoneyScale;
	}

	public String getRepayDesc() {
		return repayDesc;
	}

	public void setRepayDesc(String repayDesc) {
		this.repayDesc = repayDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<BatchProject> list;
    
    private List<LoanExtendReqVo> loanExtendList;

    public List<LoanExtendReqVo> getLoanExtendList() {
		return loanExtendList;
	}

	public void setLoanExtendList(List<LoanExtendReqVo> loanExtendList) {
		this.loanExtendList = loanExtendList;
	}

	public LoanExtendType getLoanextendType() {
        return loanextendType;
    }

    public void setLoanextendType(LoanExtendType loanextendType) {
        this.loanextendType = loanextendType;
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

    public BorrowRepayType getRepayType() {
        return repayType;
    }

    public void setRepayType(BorrowRepayType repayType) {
        this.repayType = repayType;
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

    public Boolean getUseRedPacket() {
        return useRedPacket;
    }

    public void setUseRedPacket(Boolean useRedPacket) {
        this.useRedPacket = useRedPacket;
    }

    public String getLoanExtendMark() {
        return loanExtendMark;
    }

    public void setLoanExtendMark(String loanExtendMark) {
        this.loanExtendMark = loanExtendMark;
    }

    public List<BatchProject> getList() {
        return list;
    }

    public void setList(List<BatchProject> list) {
        this.list = list;
    }

	public Money getMaxInvest() {
		return maxInvest;
	}

	public void setMaxInvest(Money maxInvest) {
		this.maxInvest = maxInvest;
	}
}

