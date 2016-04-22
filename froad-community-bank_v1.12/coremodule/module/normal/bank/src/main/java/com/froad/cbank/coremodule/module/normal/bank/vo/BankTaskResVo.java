package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankTaskResVo implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 7638545423893161707L;
	
	private String taskId;// 任务流水号
	private long createTime;// 创建时间
	private long auditTime;// 审核时间
	private String auditOrgName;// 审核机构
	private String auditor;// 审核人
	private String auditStatus;// 状态
	private String comment;// 备注

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
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
