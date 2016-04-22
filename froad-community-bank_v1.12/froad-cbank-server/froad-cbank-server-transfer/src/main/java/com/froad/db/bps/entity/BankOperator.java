package com.froad.db.bps.entity;

import java.util.Date;

public class BankOperator {
	
	private Long operatorId;		//操作员id
	private Long bankId;			//银行id号
	private String loginName;		//登陆名
	private String orgCode;			//机构编号
	private String mobile;			//手机号
	private String email;			//邮箱
	private String operatorName;	//操作员姓名
	private String loginPasswd;		//登陆密码
	private String payPasswd;		//支付密码
	private String department;		//部门
	private String position;		//职位
	private String status;			//状态0不可用1可用
	private String remark;			//备注
	private String type;			//类型1.管理员2.操作员
	private Date createTime;		//创建时间
	private Date updateTime;		//更新时间
	private Integer failures;		//错误次数
	private String isReset;			//是否重置 1 是 0否
	private String isMain;			//表示如果是管理员时是否是A岗管理员，1：A岗管理员、0：B岗管理员，如果是操作员则null
	private String isRes;			//是否面签操作员, 1:是，2：否
	private Date lastFixTime;		//最后一次密码错误锁定时间
	
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getLoginPasswd() {
		return loginPasswd;
	}
	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}
	public String getPayPasswd() {
		return payPasswd;
	}
	public void setPayPasswd(String payPasswd) {
		this.payPasswd = payPasswd;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getFailures() {
		return failures;
	}
	public void setFailures(Integer failures) {
		this.failures = failures;
	}
	public String getIsReset() {
		return isReset;
	}
	public void setIsReset(String isReset) {
		this.isReset = isReset;
	}
	public String getIsMain() {
		return isMain;
	}
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	public String getIsRes() {
		return isRes;
	}
	public void setIsRes(String isRes) {
		this.isRes = isRes;
	}
	public Date getLastFixTime() {
		return lastFixTime;
	}
	public void setLastFixTime(Date lastFixTime) {
		this.lastFixTime = lastFixTime;
	}
	
	
}
