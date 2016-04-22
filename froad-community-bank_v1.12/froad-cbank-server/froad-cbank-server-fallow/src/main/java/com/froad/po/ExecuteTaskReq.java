/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:ExecuteTaskReq.java
 * Package Name:com.froad.po
 * Date:2015年10月16日下午4:18:52
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.po;

/**
 * ClassName:ExecuteTaskReq Reason: TODO ADD REASON. Date: 2015年10月16日 下午4:18:52
 * 
 * @author vania
 * @version
 * @see
 */
public class ExecuteTaskReq {
	private Origin origin; // required
	private String instanceId; // required
	private String taskId; // required
	private String bessId; // required
	private String auditState; // required
	private String orgCode;
	private String remark; // optional

	public ExecuteTaskReq() {
		super();
	}

	public ExecuteTaskReq(Origin origin, String instanceId, String taskId, String bessId, String auditState, String orgCode, String remark) {
		super();
		this.origin = origin;
		this.instanceId = instanceId;
		this.taskId = taskId;
		this.bessId = bessId;
		this.auditState = auditState;
		this.orgCode = orgCode;
		this.remark = remark;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
