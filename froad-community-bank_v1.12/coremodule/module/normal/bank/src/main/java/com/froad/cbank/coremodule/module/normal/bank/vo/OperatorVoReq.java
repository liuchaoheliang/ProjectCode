package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 银行管理员
 * @author yfy
 *
 */
public class OperatorVoReq extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;

	private Long operatorId;       //银行管理员ID
	private String loginName;      //用户名
	private String name;		   //姓名
	private String type; 		   //用户类型：1、平台用户 2、银行用户
	private String orgCode;        //所属机构
	private Boolean state;         //禁/启用状态
	private String userPassword;   //用户密码
	private List<String> orgCodeList;    //机构号
	private String prefix;  //登录名前缀
	private String remark;  //备注 add by bruceliu 20151012
	
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

