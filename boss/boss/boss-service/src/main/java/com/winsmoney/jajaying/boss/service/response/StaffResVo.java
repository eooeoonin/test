/**
 * Project:boss
 * File:Staff.java
 * Date:2016-08-03
 * Copyright (c) 2016 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.service.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 返回结果 date: 2016-08-03 06:03:18
 * 
 * @author: CodeCreator
 */
public class StaffResVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 员工id
	 */
	private String id;

	/**
	 * 部门id
	 */
	private String departmentId;

	/**
	 * 用户角色id
	 */
	private String roleId;

	/**
	 * 用户uid
	 */
	private String userId;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 员工名称
	 */
	private String staffName;

	/**
	 * 0 男 1 女
	 */
	private String sex;

	/**
	* 
	*/
	private String realName;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 移动电话
	 */
	private String mobile;

	/**
	 * 固定电话
	 */
	private String phone;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * QQ号码
	 */
	private String qq;

	/**
	 * 最低审批额度
	 */
	private Integer lowest;

	/**
	 * 最高审批额度
	 */
	private Integer highest;

	/**
	 * 当前初审任务数
	 */
	private Integer firstTaskNo = 0;

	/**
	 * 当前复审任务数
	 */
	private Integer secondTaskNo = 0;

	/**
	 * X.509 数字证书使用者密钥标识符
	 */
	private String x509skeyId;

	/**
	 * 状态预留
	 */
	private String status;

	/**
	 * 最后编辑人
	 */
	private String editedBy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getLowest() {
		return lowest;
	}

	public void setLowest(Integer lowest) {
		this.lowest = lowest;
	}

	public Integer getHighest() {
		return highest;
	}

	public void setHighest(Integer highest) {
		this.highest = highest;
	}

	public Integer getFirstTaskNo() {
		return firstTaskNo;
	}

	public void setFirstTaskNo(Integer firstTaskNo) {
		this.firstTaskNo = firstTaskNo;
	}

	public Integer getSecondTaskNo() {
		return secondTaskNo;
	}

	public void setSecondTaskNo(Integer secondTaskNo) {
		this.secondTaskNo = secondTaskNo;
	}

	public String getX509skeyId() {
		return x509skeyId;
	}

	public void setX509skeyId(String x509skeyId) {
		this.x509skeyId = x509skeyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
