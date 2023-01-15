package com.winsmoney.jajaying.boss.controller.model;

import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.InvestorStatisticsResVo;
import com.winsmoney.platform.framework.core.util.Money;

public class InvestorStatisticsExcelPlans  {
	private static final long serialVersionUID = 1L;

		//新增注册用户数
		private Integer addRegisteredUsers;
		//累计注册用户数
		private Integer cumulativeRegisteredUsers;
		//新增绑卡用户数
		private Integer addBindUsers;
	    //累计绑卡用户数
		private Integer cumulativeBindUsers;
	    //平均用户注册成本   
		private Money averageUserCost;
		
	
	public Integer getAddRegisteredUsers() {
			return addRegisteredUsers;
		}

		public void setAddRegisteredUsers(Integer addRegisteredUsers) {
			this.addRegisteredUsers = addRegisteredUsers;
		}

		public Integer getCumulativeRegisteredUsers() {
			return cumulativeRegisteredUsers;
		}

		public void setCumulativeRegisteredUsers(Integer cumulativeRegisteredUsers) {
			this.cumulativeRegisteredUsers = cumulativeRegisteredUsers;
		}

		public Integer getAddBindUsers() {
			return addBindUsers;
		}

		public void setAddBindUsers(Integer addBindUsers) {
			this.addBindUsers = addBindUsers;
		}

		public Integer getCumulativeBindUsers() {
			return cumulativeBindUsers;
		}

		public void setCumulativeBindUsers(Integer cumulativeBindUsers) {
			this.cumulativeBindUsers = cumulativeBindUsers;
		}

		

	public Money getAverageUserCost() {
			return averageUserCost;
		}

		public void setAverageUserCost(Money averageUserCost) {
			this.averageUserCost = averageUserCost;
		}



	/**
     * 投资金额
     */
    private Money investAmount;

    /**
     * 投资笔数
     */
    private Integer investCount;
    /**
     * 投资人数
     */
    private Integer investNumber;
    /**
     * 最大单笔投资金额
     */
    private Money maxInvestAmount;
    /**
     * 平均投资金额
     */
    private Money averageInvestAmount;
    
    /**
     * 平均每人投资金额
     */
    private Money averagePersonInvestAmount;
    
    /**
     * 红包使用金额
     */
    private Money totalRedMoneyAmount;
    
    /**
     * 单人最大投资金额
     */
    private Money maxPersonInvestAmount;
    
    /**
     * 平均用户投资成本
     */
    private Money averageInvestCost;
    
    /**
     * 充值金额
     */
    private Money rechargeAmount;
    
    /**
     * 提现金额
     */
    private Money withdrawAmount;
    /**
     * 回款金额
     */
    private Money repayAmount;
    

	public Money getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Money repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Money getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Money investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public Integer getInvestNumber() {
		return investNumber;
	}

	public void setInvestNumber(Integer investNumber) {
		this.investNumber = investNumber;
	}

	public Money getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(Money maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}

	public Money getAverageInvestAmount() {
		return averageInvestAmount;
	}

	public void setAverageInvestAmount(Money averageInvestAmount) {
		this.averageInvestAmount = averageInvestAmount;
	}

	public Money getAveragePersonInvestAmount() {
		return averagePersonInvestAmount;
	}

	public void setAveragePersonInvestAmount(Money averagePersonInvestAmount) {
		this.averagePersonInvestAmount = averagePersonInvestAmount;
	}

	public Money getTotalRedMoneyAmount() {
		return totalRedMoneyAmount;
	}

	public void setTotalRedMoneyAmount(Money totalRedMoneyAmount) {
		this.totalRedMoneyAmount = totalRedMoneyAmount;
	}

	public Money getMaxPersonInvestAmount() {
		return maxPersonInvestAmount;
	}

	public void setMaxPersonInvestAmount(Money maxPersonInvestAmount) {
		this.maxPersonInvestAmount = maxPersonInvestAmount;
	}

	public Money getAverageInvestCost() {
		return averageInvestCost;
	}

	public void setAverageInvestCost(Money averageInvestCost) {
		this.averageInvestCost = averageInvestCost;
	}

	public Money getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Money rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Money getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Money withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    

}
