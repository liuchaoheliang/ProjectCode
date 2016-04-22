/**
 * Project Name:coremodule-bank
 * File Name:MerchantTaskRes.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015-8-14下午02:40:04
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 
 * ClassName: AuditTaskRes
 * Function: 审核任务订单实体
 * date: 2015-8-19 下午01:15:04
 *
 * @author wufei
 * @version
 */
public class AuditProcessRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572950407310333622L;
	
	private String taskId;   //任务流水号
	private Long createTime;	  //创建时间
	private Long auditTime;	  //审核时间
	private String auditOrgName;  //审核机构
	private String auditor;		  //审核人
	private String auditStatus;	  //审核状态
	private String comment;		  //审核备注
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditOrgName() {
		return auditOrgName;
	}
	public void setAuditOrgName(String auditOrgName) {
		this.auditOrgName = auditOrgName;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
