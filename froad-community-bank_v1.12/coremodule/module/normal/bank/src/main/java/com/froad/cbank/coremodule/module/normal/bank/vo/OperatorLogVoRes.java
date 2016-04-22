package com.froad.cbank.coremodule.module.normal.bank.vo;

public class OperatorLogVoRes {

	private Long operatorLogId;
	private String loginName;	//用户名
	private String orgCode;	//机构号
	private String roleName; //角色
	private String createTime;//操作日期
	private String orgName;	//所属机构
	private String remark;	//操作内容
	
	public Long getOperatorLogId() {
		return operatorLogId;
	}
	public void setOperatorLogId(Long operatorLogId) {
		this.operatorLogId = operatorLogId;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
