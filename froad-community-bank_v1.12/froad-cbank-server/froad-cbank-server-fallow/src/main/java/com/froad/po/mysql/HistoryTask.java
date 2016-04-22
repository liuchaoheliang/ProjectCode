package com.froad.po.mysql;

import java.util.Date;

/**
 * CbHistoryTask entity.
 */

public class HistoryTask implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String instanceId;
	private String nodeId;
	private String taskId;
	private String parentTaskId;
	private String name;
	private String displayName;
	private String performType;
	private String taskType;
	private String taskState;
	private String operator;
	private Date finishTime;
	private String auditState;
	private String remark;
	private String orgId;

	// Constructors

	/** default constructor */
	public HistoryTask() {
	}

	public HistoryTask(Date createTime, Date updateTime, String clientId, String instanceId, String nodeId, String taskId, String parentTaskId, String name, String displayName, String performType, String taskType, String taskState, String operator, Date finishTime, String auditState, String remark,String orgId) {
		super();
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.nodeId = nodeId;
		this.taskId = taskId;
		this.parentTaskId = parentTaskId;
		this.name = name;
		this.displayName = displayName;
		this.performType = performType;
		this.taskType = taskType;
		this.taskState = taskState;
		this.operator = operator;
		this.finishTime = finishTime;
		this.auditState = auditState;
		this.remark = remark;
		this.orgId = orgId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPerformType() {
		return performType;
	}

	public void setPerformType(String performType) {
		this.performType = performType;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



}