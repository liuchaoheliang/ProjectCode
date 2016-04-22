package com.froad.db.bps.entity;

import java.util.Date;

public class OperatorLog {
	
	private Long id;				//id	
	private String operateType;		//操作类型
	private String operator;		//操作员ID
	private String operateStatus;	//操作结果 1-成功 2-失败
	private String backup;			//备用字段
	private Date createTime;		//创建时间
	private Date updateTime;		//更新时间
	private String remark;			//备注
	private String orgCode;			//操作员所在银行机构号
	private String orgName;			//银行机构名
	private String bankCode;		//操作员所属一级银行编号
	private String loginIp;			//操作员登陆IP
	private String roleName;		//操作员所属角色名称
	private String loginAccount;	//操作员登陆账号
	private String bankUnionCode;	//成员行号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperateStatus() {
		return operateStatus;
	}
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	public String getBackup() {
		return backup;
	}
	public void setBackup(String backup) {
		this.backup = backup;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getBankUnionCode() {
		return bankUnionCode;
	}
	public void setBankUnionCode(String bankUnionCode) {
		this.bankUnionCode = bankUnionCode;
	}
	
	
}
