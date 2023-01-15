/**
 * Project:boss
 * File:Activity.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain.model;

import com.winsmoney.platform.framework.core.model.BaseModel;
import com.winsmoney.platform.framework.core.util.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* ClassName: activity
* Description: 活动表
* date: 2017-11-23 06:20:56
* @author: CodeCreator
*/
public class Activity extends BaseModel
{

	private static final long serialVersionUID = -1L;

	/**
	* 
	*/
	private String createdBy;
	/**
	* 
	*/
	private String name;
	/**
	* 
	*/
	private String body;
	/**
	* 
	*/
	private String desc;
	/**
	* 
	*/
	private Date startTime;
	/**
	* 
	*/
	private Date endTime;
	/**
	* 
	*/
	private String templateId;
	/**
	* 
	*/
	private String pic1;
	/**
	* 
	*/
	private String pic2;
	/**
	* 
	*/
	private String pic3;
	/**
	* 
	*/
	private String pic4;
	/**
	* 
	*/
	private String pic5;
	/**
	* 
	*/
	private String rule1;
	/**
	* 
	*/
	private String rule2;
	/**
	* 
	*/
	private String rule3;
	/**
	* 
	*/
	private String wxName;
	/**
	* 
	*/
	private String wxDesc;
	/**
	* 
	*/
	private String wxPic;
	/**
	* 
	*/
	private Integer code;
	/**
	 * 开奖时间
	 */
	private Date awardTime;
	/**
	* 拆分标准金额
	*/
	private BigDecimal standardAmount;
	/**
	* 投资转化比例
	*/
	private BigDecimal conversionRatio;
	/**
	* 集采总额上限
	*/
	private BigDecimal totalAmount;
	/**
	* 起始号段
	*/
	private Long startNum;
	/**
	* 结束号段
	*/
	private Long endNum;
	/**
	 * 活动开始时间
	 */
	private Date activityStartTime ;
	/**
	 * 活动结束时间
	 */
	private Date activityEndTime;
	/**
	 * 获奖值
	 */
	private BigDecimal awardValue;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public String getPic5() {
		return pic5;
	}

	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}

	public String getRule1() {
		return rule1;
	}

	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	public String getRule2() {
		return rule2;
	}

	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	public String getRule3() {
		return rule3;
	}

	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getWxDesc() {
		return wxDesc;
	}

	public void setWxDesc(String wxDesc) {
		this.wxDesc = wxDesc;
	}

	public String getWxPic() {
		return wxPic;
	}

	public void setWxPic(String wxPic) {
		this.wxPic = wxPic;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public BigDecimal getStandardAmount() {
		return standardAmount;
	}

	public void setStandardAmount(BigDecimal standardAmount) {
		this.standardAmount = standardAmount;
	}

	public BigDecimal getConversionRatio() {
		return conversionRatio;
	}

	public void setConversionRatio(BigDecimal conversionRatio) {
		this.conversionRatio = conversionRatio;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public Long getEndNum() {
		return endNum;
	}

	public void setEndNum(Long endNum) {
		this.endNum = endNum;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public BigDecimal getAwardValue() {
		return awardValue;
	}

	public void setAwardValue(BigDecimal awardValue) {
		this.awardValue = awardValue;
	}
}