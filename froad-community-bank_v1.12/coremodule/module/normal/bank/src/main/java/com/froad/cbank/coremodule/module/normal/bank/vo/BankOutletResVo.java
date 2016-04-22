package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankOutletResVo implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -7003346647174013954L;
	
	private String auditNumber;//审核流水号
	private String taskId;// 任务id
	private String outletId;//门店编号
	private String outletName;//门店名称
	private String merchantName;//商户名称
	private long createTime;//创建时间 	
	private String auditType;//审核类型

	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
