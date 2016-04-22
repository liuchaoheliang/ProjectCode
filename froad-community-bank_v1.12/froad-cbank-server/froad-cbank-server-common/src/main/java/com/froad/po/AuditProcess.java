package com.froad.po;

import java.util.Date;

/**
 * 审核任务表 po
 * @author ll 20150814 add
 *
 */
public class AuditProcess  implements java.io.Serializable {

	private Long id;						//主键Id
	private Date createTime;				//创建时间
	private String clientId;				//客户端Id
	private String auditId;					//审核流水号 (yyyymmd+4位数字时间流水号)
	private String taskId;					//任务流水号 (yyyymmd+4位数字时间流水号)
	private String orgCode;					//审核人所属机构
	private String orgName;					//审核人所属机构名称
	private String auditStage;				//审核步骤(0-初审/1-复审)
	private Date auditTime;					//审核时间
	private String auditComment;			//审核备注
	private String auditStaff;				//审核人员
	private String reviewStaff;				//复核人员
	private String auditState;				//审核状态：0-待审核  1-审核通过 2-审核未通过
	
	public AuditProcess(){
		
	}
	
	public AuditProcess(Date createTime, String clientId, String auditId,String taskId,
			String orgCode, String orgName, String auditStage, Date auditTime,
			String auditComment, String auditStaff, String reviewStaff,
			String auditState) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.auditId = auditId;
		this.taskId = taskId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.auditStage = auditStage;
		this.auditTime = auditTime;
		this.auditComment = auditComment;
		this.auditStaff = auditStaff;
		this.reviewStaff = reviewStaff;
		this.auditState = auditState;
	}

	public AuditProcess(Date createTime, String clientId, String auditId,String taskId,
			String orgCode, String orgName) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.auditId = auditId;
		this.taskId = taskId;
		this.orgCode = orgCode;
		this.orgName = orgName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public String getReviewStaff() {
		return reviewStaff;
	}

	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	
		
	

}
