package com.winsmoney.jajaying.boss.controller.project;

import com.winsmoney.jajaying.boss.controller.model.BorrowVo;
import com.winsmoney.jajaying.trade.service.enums.BorrowType;
import com.winsmoney.jajaying.trade.service.enums.BorrowerType;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wei on 2016/8/20.
 */
public class BorrowForm extends BorrowVo {
	private BorrowerType borrowerType;
    public BorrowerType getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(BorrowerType borrowerType) {
		this.borrowerType = borrowerType;
	}
	private String releaseUserCard;
	public String getReleaseUserCard() {
		return releaseUserCard;
	}

	public void setReleaseUserCard(String releaseUserCard) {
		this.releaseUserCard = releaseUserCard;
	}
	/**
     * 复审时间
     */
    private Date reviewDate;
	/**
	 * 期望放款时间
	 */
	private Date wantReleaseTime;
    /**
     * 项目信息
     */
    private String projectMessage;
    /**
     * 风控
     */
    private String risktMessage;
    /**
     * 安全
     */
    private String saveMessage;
    /**
     * 借款周期
     */
    private String borrowCycle;
    /**
     * 剩余额度
     */
    private Money borrowSurplusAmount;
    /**
     * 还款方式
     */
    private String repayTypeName;
	/**
	 * 资金接收人ID
	 */
	private String releaseUserId;

    public String getReleaseUserId() {
		return releaseUserId;
	}

	public void setReleaseUserId(String releaseUserId) {
		this.releaseUserId = releaseUserId;
	}

	public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getBorrowCycle() {
    	String days = null;
    	if(getLastReleaseTime() != null && getLastRepayTime() != null) {
    		Calendar calstart = Calendar.getInstance();
            calstart.setTime( getLastReleaseTime() );
            calstart.get(Calendar.DATE);
            Calendar calend = Calendar.getInstance();
            calend.setTime( getLastRepayTime() );
            if(calstart.getTime().equals(calend.getTime()))
            	days = "1天";
            else{
                days = DateUtil.daysInterestBetween(calend.getTime(), calstart.getTime()) + "天";
            }  
    	}
        return days;
    }

    public Money getBorrowSurplusAmount() {
        return null==getBorrowAmount()?null:(getBorrowedAmount()==null?null:getBorrowAmount().subtract( getBorrowedAmount() ));
    }

    public String getRepayTypeName() {
        return getRepayType().getName();
    }

	public String getProjectMessage() {
		return projectMessage;
	}

	public void setProjectMessage(String projectMessage) {
		this.projectMessage = projectMessage;
	}

	public String getRisktMessage() {
		return risktMessage;
	}

	public void setRisktMessage(String risktMessage) {
		this.risktMessage = risktMessage;
	}

	public String getSaveMessage() {
		return saveMessage;
	}

	public void setSaveMessage(String saveMessage) {
		this.saveMessage = saveMessage;
	}

	public void setBorrowCycle(String borrowCycle) {
		this.borrowCycle = borrowCycle;
	}

	public void setBorrowSurplusAmount(Money borrowSurplusAmount) {
		this.borrowSurplusAmount = borrowSurplusAmount;
	}

	public void setRepayTypeName(String repayTypeName) {
		this.repayTypeName = repayTypeName;
	}

	public Date getWantReleaseTime() {
		return wantReleaseTime;
	}

	public void setWantReleaseTime(Date wantReleaseTime) {
		this.wantReleaseTime = wantReleaseTime;
	}
    
}
