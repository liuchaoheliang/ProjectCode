package com.froad.po.mq;

import com.froad.po.Result;

/**
 * 审核MQ对象
 * @author ll 20151014 
 *
 */
public class AuditMQ {
	private Result result;			/** 返回结果:0000-成功,反之则失败 */
	private Long time;				/** 执行时间  */ 
	private String clientId;		/** 客户端id  */ 
	private String businessId;		/** 业务Id(商户Id、门店Id、商品Id)  */
	private String auditId;			/** 审核流水号，即关联审核流水号  */ 
	private String auditState;		/** 审核状态(0-待审核,1审核通过,2审核未通过)*/
	private String auditStaff;		/** 审核人username  */
//	private String reviewStaff;		/** 复核人username  */ 
	private String auditComment;	/** 审核备注  */ 
	private Boolean isFinalAudit; 	/** 本次执行的审核任务是否流程定义的最后一次审核(中途审核不通过则不算终审)  */ 
	private Boolean isArchive; 		/** 本次执行完审核任务是否归档(需要归档的情况:审核不通过,强行终止审核任务,最后一级审核) */
	private Long finishTime; 		/** 归档时间 */
	public AuditMQ() {
		super();
	}

	public AuditMQ(Result result, Long time, String clientId, String businessId, String auditId, String auditState,
			String auditStaff, String auditComment, Boolean isFinalAudit, Boolean isArchive,Long finishTime) {
		super();
		this.result = result;
		this.time = time;
		this.clientId = clientId;
		this.businessId = businessId;
		this.auditId = auditId;
		this.auditState = auditState;
		this.auditStaff = auditStaff;
		this.auditComment = auditComment;
		this.isFinalAudit = isFinalAudit;
		this.isArchive = isArchive;
		this.finishTime = finishTime;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public Boolean getIsFinalAudit() {
		return isFinalAudit;
	}

	public void setIsFinalAudit(Boolean isFinalAudit) {
		this.isFinalAudit = isFinalAudit;
	}

	public Boolean getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(Boolean isArchive) {
		this.isArchive = isArchive;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
