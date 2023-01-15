package com.winsmoney.jajaying.boss.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 交易流水申请
 *
 * @author howard he
 * @create 2018/8/29 10:10
 */
@Data
public class TradeStreamApplyReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
     * 用户 id
     */
    private String userId;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 交易流水号列表：最多支持传入 100 条
     */
    private String sequenceIds;
}
