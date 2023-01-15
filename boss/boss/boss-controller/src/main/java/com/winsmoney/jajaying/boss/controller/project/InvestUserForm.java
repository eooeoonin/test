package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.trade.service.response.LoanInvestorResVo;
import com.winsmoney.platform.framework.core.util.Money;

/**
 * Created by wei on 2016/8/20.
 */
public class InvestUserForm extends LoanInvestorResVo {
    //投资人
    private String userPhone ;
    //姓名
    private String userName;
    //用户属性
    private String userProperties;
//    /**
//     * 加息劵使用价值
//     */
//    private BigDecimal addInterestVal;
//
//    public BigDecimal getAddInterestVal() {
//		return addInterestVal;
//	}
//
//	public void setAddInterestVal(BigDecimal addInterestVal) {
//		this.addInterestVal = addInterestVal;
//	}

	public Money getUseCashVal() {
		return useCashVal;
	}

	public void setUseCashVal(Money useCashVal) {
		this.useCashVal = useCashVal;
	}

	/**
     * 现金劵使用价值
     */
    private Money useCashVal;
    /**
     * 现金金额
     */
    private Money cashMoney;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProperties() {
        return InvestGroup.investGroupName( userProperties );
    }

    public void setUserProperties(String userProperties) {
        this.userProperties = userProperties;
    }

    public Money getCashMoney() {
        return getInvestAmount().subtract( getRedMoneyAmount());
    }

    public void setCashMoney(Money cashMoney) {
        this.cashMoney = cashMoney;
    }
}
