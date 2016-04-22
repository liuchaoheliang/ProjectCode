/**
 * Project Name:coremodule-bank
 * File Name:AuditReq.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015-10-22下午04:10:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:AuditReq
 * Reason:	 审核请求实体
 * Date:     2015-10-22 下午04:10:36
 * @author   wufei
 * @version  
 * @see 	 
 */
public class AuditReq implements Serializable{

	  /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	  private String auditId; // 审核流水号
	  private String taskId; //  任务流水号
	  private String bessId; //  业务（商户、门店、商品）id
	  private String auditState; //审核状态
	  private String remark; // 备注
	  private String myOrgCode;//当前登录人机构
	  private String operator;      //当前登录人
	  
	  
	  
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMyOrgCode() {
		return myOrgCode;
	}
	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBessId() {
		return bessId;
	}
	public void setBessId(String bessId) {
		this.bessId = bessId;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  
	  
}
