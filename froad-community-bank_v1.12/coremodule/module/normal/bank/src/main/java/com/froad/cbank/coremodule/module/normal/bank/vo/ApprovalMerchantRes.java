package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 待审核商户列表
 * @author wufei
 *
 */
public class ApprovalMerchantRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantId;  //商户id
	private String auditId;  //审核流水号
	private String merchantName; // 商户名称
	private String type; // 商户类型
	private Long createTime;	//创建时间
	private String orgName; // 所属机构
	/** 0-新增；1-更新  */
	private String auditType; // 审核类型       
	private String merchantUserId;//商户用户id
	private String taskId;// 审核流水号
	
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	
	
}
