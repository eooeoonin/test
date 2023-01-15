package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.trade.service.enums.BorrowType;
import com.winsmoney.jajaying.trade.service.enums.LoanStatus;
import com.winsmoney.jajaying.trade.service.response.LoanResVo;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wei on 2016/8/17.
 */
public class LoanResForm extends LoanResVo {

    /**
     * 项目编号
     */
    private String loanCode;
    /**
     * 借款名称
     */
    private String borrowTitle;
    /**
     * 资产类型
     */
    private String borrowTypeName;
    /**
     * 资产类型
     */
    private BorrowType borrowType;
    /**
     * 项目类型名称
     */
    private String loanTypeName ;
    /**
     * 借款总额
     */
    private Money borrowAmount;
    /**
     * 借款剩余额度
     */
    private Money borrowSurplusAmount;
    /**
     * 已经借款金额
     */
    private Money borrowedAmount;
    /**
     * 借款周期
     */
    private String borrowCycle;
    /**
     * 借款利率
     */
    private BigDecimal borrowRate;
    /**
     * 项目描述
     */
    private String desc;
    private String descId;

    /**
     * 借款用途
     */
    private String purpose;
    private String purposeId;
    /**
     * 风控措施
     */
    private String risk;
    private String riskId;
    /**
     * 待开标操作内容
     */
    private String initoperdesc;
    private String initoperdescId;
    private Date initoperdescTime;
    private String initoperdescEditBy;
    /**
     * 未开始操作内容
     */
    private String openoperdesc;
    private String openoperdescId;
    private Date openoperdescTime;
    private String openoperdescEditBy;
    /**
     * 募集中操作内容
     */
    private String openedoperdesc;
    private String openedoperdescId;
    private Date openedoperdescTime;
    private String openedoperdescEditBy;
    /**
     * 等待放款操作内容
     */
    private String fulloperdesc;
    private String fulloperdescId;
    private Date fulloperdescTime;
    private String fulloperdescEditBy;
    /**
     * 还款中操作内容
     */
    private String disbureoperdesc;
    private String disbureoperdescId;
    private Date disbureoperdescTime;
    private String disbureoperdescEditBy;

	/**
     * 还款方式
     */
    private String repayTypeName;
    /**
     * 操作人
     */
    private String operUser;
    /**
     * 是否可用红包名称
     */
    private String useRedPacketName;
    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 投资用户组名称
     */
    private String investGroupCodeName;

    /**
     * 标的状态
     */
    private String status ;
    
    private Money redMoneyAmount;
    
    private Money profit;


    public String getLoanCode() {
        return getId();
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public String getBorrowTypeName() {
        return getBorrowType()==null?"":getBorrowType().getName();
    }

    public String getLoanTypeName() {
        return getLoanType()==null?"":getLoanType().getName();
    }

    public Money getBorrowAmount() {
        return borrowAmount;
    }

    public Money getBorrowSurplusAmount() {
        return null==borrowAmount?null:(borrowedAmount==null?null:borrowAmount.subtract( borrowedAmount ));
    }

    public Money getBorrowedAmount() {
        return borrowedAmount;
    }

    public String getBorrowCycle() {
    	String borrowTerm1 = getBorrowTerm().toString();
    	if("MONTH".equals(getTermType())) {
    		return borrowTerm1+"月";
    	}else {
    		return borrowTerm1+"天";
    	}
    }

    public String getDesc() {
        return desc;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getRisk() {
        return risk;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public void setBorrowTypeName(String borrowTypeName) {
        this.borrowTypeName = borrowTypeName;
    }

    public void setBorrowType(BorrowType borrowType) {
        this.borrowType = borrowType;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public void setBorrowAmount(Money borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public void setBorrowSurplusAmount(Money borrowSurplusAmount) {
        this.borrowSurplusAmount = borrowSurplusAmount;
    }

    public void setBorrowedAmount(Money borrowedAmount) {
        this.borrowedAmount = borrowedAmount;
    }

    public void setBorrowCycle(String borrowCycle) {
        this.borrowCycle = borrowCycle;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public BorrowType getBorrowType() {
        return borrowType;
    }

    public String getDescId() {
        return descId;
    }

    public void setDescId(String descId) {
        this.descId = descId;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId;
    }

    public BigDecimal getBorrowRate() {
        if(null == borrowRate){
            return null;
        }else {
            return borrowRate.divide( new BigDecimal( 100 ));
        }
    }

    public void setBorrowRate(BigDecimal borrowRate) {
        this.borrowRate = borrowRate;
    }

    public String getRepayTypeName() {
        return repayTypeName;
    }

    public void setRepayTypeName(String repayTypeName) {
        this.repayTypeName = repayTypeName;
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public String getUseRedPacketName() {
        return getUseRedPacket()?"是":"否";
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getInvestGroupCodeName() {
        return investGroupCodeName;
    }

    public void setInvestGroupCodeName(String investGroupCodeName) {
        this.investGroupCodeName = investGroupCodeName;
    }

    @Override
    public String getStatus() {
        if(!StringUtils.isBlank(status))
            return LoanStatus.getType( status ).getName();
        else
            return StringUtils.trimToEmpty( status );
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getInitoperdesc() {
		return initoperdesc;
	}

	public void setInitoperdesc(String initoperdesc) {
		this.initoperdesc = initoperdesc;
	}

	public String getInitoperdescId() {
		return initoperdescId;
	}

	public void setInitoperdescId(String initoperdescId) {
		this.initoperdescId = initoperdescId;
	}

	public Date getInitoperdescTime() {
		return initoperdescTime;
	}

	public void setInitoperdescTime(Date initoperdescTime) {
		this.initoperdescTime = initoperdescTime;
	}

	public String getOpenoperdesc() {
		return openoperdesc;
	}

	public void setOpenoperdesc(String openoperdesc) {
		this.openoperdesc = openoperdesc;
	}

	public String getOpenoperdescId() {
		return openoperdescId;
	}

	public void setOpenoperdescId(String openoperdescId) {
		this.openoperdescId = openoperdescId;
	}

	public Date getOpenoperdescTime() {
		return openoperdescTime;
	}

	public void setOpenoperdescTime(Date openoperdescTime) {
		this.openoperdescTime = openoperdescTime;
	}

	public String getOpenedoperdesc() {
		return openedoperdesc;
	}

	public void setOpenedoperdesc(String openedoperdesc) {
		this.openedoperdesc = openedoperdesc;
	}

	public String getOpenedoperdescId() {
		return openedoperdescId;
	}

	public void setOpenedoperdescId(String openedoperdescId) {
		this.openedoperdescId = openedoperdescId;
	}

	public Date getOpenedoperdescTime() {
		return openedoperdescTime;
	}

	public void setOpenedoperdescTime(Date openedoperdescTime) {
		this.openedoperdescTime = openedoperdescTime;
	}

	public String getFulloperdesc() {
		return fulloperdesc;
	}

	public void setFulloperdesc(String fulloperdesc) {
		this.fulloperdesc = fulloperdesc;
	}

	public String getFulloperdescId() {
		return fulloperdescId;
	}

	public void setFulloperdescId(String fulloperdescId) {
		this.fulloperdescId = fulloperdescId;
	}

	public Date getFulloperdescTime() {
		return fulloperdescTime;
	}

	public void setFulloperdescTime(Date fulloperdescTime) {
		this.fulloperdescTime = fulloperdescTime;
	}

	public String getDisbureoperdesc() {
		return disbureoperdesc;
	}

	public void setDisbureoperdesc(String disbureoperdesc) {
		this.disbureoperdesc = disbureoperdesc;
	}

	public String getDisbureoperdescId() {
		return disbureoperdescId;
	}

	public void setDisbureoperdescId(String disbureoperdescId) {
		this.disbureoperdescId = disbureoperdescId;
	}

	public Date getDisbureoperdescTime() {
		return disbureoperdescTime;
	}

	public void setDisbureoperdescTime(Date disbureoperdescTime) {
		this.disbureoperdescTime = disbureoperdescTime;
	}

	public String getInitoperdescEditBy() {
		return initoperdescEditBy;
	}

	public void setInitoperdescEditBy(String initoperdescEditBy) {
		this.initoperdescEditBy = initoperdescEditBy;
	}

	public String getOpenoperdescEditBy() {
		return openoperdescEditBy;
	}

	public void setOpenoperdescEditBy(String openoperdescEditBy) {
		this.openoperdescEditBy = openoperdescEditBy;
	}

	
	public String getOpenedoperdescEditBy() {
		return openedoperdescEditBy;
	}

	public void setOpenedoperdescEditBy(String openedoperdescEditBy) {
		this.openedoperdescEditBy = openedoperdescEditBy;
	}

	public String getFulloperdescEditBy() {
		return fulloperdescEditBy;
	}

	public void setFulloperdescEditBy(String fulloperdescEditBy) {
		this.fulloperdescEditBy = fulloperdescEditBy;
	}

	public String getDisbureoperdescEditBy() {
		return disbureoperdescEditBy;
	}

	public void setDisbureoperdescEditBy(String disbureoperdescEditBy) {
		this.disbureoperdescEditBy = disbureoperdescEditBy;
	}

	public Money getRedMoneyAmount() {
		return redMoneyAmount;
	}

	public void setRedMoneyAmount(Money redMoneyAmount) {
		this.redMoneyAmount = redMoneyAmount;
	}

	public Money getProfit() {
		return profit;
	}

	public void setProfit(Money profit) {
		this.profit = profit;
	}


    @Override
    public BigDecimal getYearRate() {
        if( null != super.getYearRate() ) {
            return super.getYearRate().divide(new BigDecimal(100));
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal getFloatRate() {
        if( null != super.getFloatRate() ){
            return super.getFloatRate().divide( new BigDecimal( 100 ));
        }else{
            return null;
        }
    }
}
