package com.froad.po.mysql;

import java.util.Date;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;

/**
 * CbTask entity.
 */
@Guarded
public class Task implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = -4773433648559038568L;
	
	private Long id;
	private Date createTime;
	private Date updateTime;
	@MaxLength(value = 32, message = "客户端ID不能超过{max}个字符")
	private String clientId;
	@MaxLength(value = 32, message = "审核流水号不能超过{max}个字符")
	private String instanceId;
	@MaxLength(value = 32, message = "流程节点ID不能超过{max}个字符")
	private String nodeId;
	@MaxLength(value = 32, message = "任务ID不能超过{max}个字符")
	private String taskId;
	@MaxLength(value = 32, message = "父任务ID不能超过{max}个字符")
	private String parentTaskId;
	@MaxLength(value = 100, message = "任务名称不能超过{max}个字符")
	private String name;
	@MaxLength(value = 255, message = "任务显示名称不能超过{max}个字符")
	private String displayName;
	@MaxLength(value = 1, message = "参与类型不能超过{max}个字符")
	private String performType;
	@MaxLength(value = 1, message = "任务类型不能超过{max}个字符")
	private String taskType;
	@MaxLength(value = 32, message = "任务处理人不能超过{max}个字符")
	private String operator;
	private Date finishTime;
	@MaxLength(value = 16, message = "机构ID不能超过{max}个字符")
	private String orgId; 

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** minimal constructor */
	public Task(Date createTime, Date updateTime, String clientId, String instanceId, String nodeId, String taskId, String name, String displayName, String performType, String taskType,Date finishTime, String orgId) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.nodeId = nodeId;
		this.taskId = taskId;
		this.name = name;
		this.displayName = displayName;
		this.performType = performType;
		this.taskType = taskType;
		this.finishTime = finishTime;
		this.orgId = orgId;
	}

	/** full constructor */
	public Task(Date createTime, Date updateTime, String clientId, String instanceId, String nodeId, String taskId, String parentTaskId, String name, String displayName, String performType, String taskType, String operator, Date finishTime,String orgId) {
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
		this.operator = operator;
		this.finishTime = finishTime;
		this.orgId = orgId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPerformType() {
		return this.performType;
	}

	public void setPerformType(String performType) {
		this.performType = performType;
	}

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}