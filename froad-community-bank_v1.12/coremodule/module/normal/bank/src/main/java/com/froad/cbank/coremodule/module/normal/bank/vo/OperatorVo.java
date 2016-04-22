package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 银行管理员
 * @author yfy
 *
 */
public class OperatorVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	
	private Long operatorId;       //银行管理员ID
	private String loginName;      //用户名
	private String userPassword;   //用户密码
	private String operatorName;   //姓名
	private String orgCode;        //所属机构
	private String orgCodeName;    //所属机构名称
	private String partenOrgCode; //上级机构
	private String partenOrgCodeName;//上级机构名称
	private String orgLevel;      //机构等级
	private String department;     //部门
	private Long roleId;         //角色
	private String position;       //职务
	private Boolean state;     //是否有效
	private String prefix;     //用户名前缀
	private String clientId;   //客户端ID
	private String remark;     //备注  add by bruce 20151012
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getPartenOrgCode() {
		return partenOrgCode;
	}
	public void setPartenOrgCode(String partenOrgCode) {
		this.partenOrgCode = partenOrgCode;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgCodeName() {
		return orgCodeName;
	}
	public void setOrgCodeName(String orgCodeName) {
		this.orgCodeName = orgCodeName;
	}
	public String getPartenOrgCodeName() {
		return partenOrgCodeName;
	}
	public void setPartenOrgCodeName(String partenOrgCodeName) {
		this.partenOrgCodeName = partenOrgCodeName;
	}
	
}
