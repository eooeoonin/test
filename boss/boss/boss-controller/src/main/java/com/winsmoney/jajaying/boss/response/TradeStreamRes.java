package com.winsmoney.jajaying.boss.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author howard he
 * @create 2018/8/29 10:05
 */
@Data
public class TradeStreamRes implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 请求 id
	 */
	private String requestId;
	/**
	 * 上送流水id
	 */
	private String sequenceId;
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
	 * 请求时间
	 */
	private Date requestTime;
	/**
	 * 响应时间
	 */
	private Date responseTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 备注
	 */
	private String remark;

}
