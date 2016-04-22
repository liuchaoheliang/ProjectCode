package com.froad.po.mysql;

import java.util.Date;

/**
 * CbProcessNode entity.
 */

public class ProcessNode implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String processId;
	private String nodeId;
	private String name;
	private String preNodeId;
	private String nextNodeId;
	private String type;
	private String nodeLogic;
	private String runnerFlag;
	private String nextRunnerOrg;
	private String status;
	private Long runnerUserId;
	private Long runnerPostId;
	private Long runnerDepartId;
	private Long runnerUsergroupId;
	private String runnerOrgLevel;
	private Boolean isMultiselect;
	private Boolean isOtherMan;
	private Boolean isAssignMan;
	private Boolean isRevoke;
	private Boolean isFallback;

	// Constructors

	/** default constructor */
	public ProcessNode() {
	}

	/** minimal constructor */
	public ProcessNode(Date createTime, Date updateTime, String clientId, String processId, String nodeId, String type, String status, Boolean isMultiselect, Boolean isOtherMan, Boolean isAssignMan, Boolean isRevoke, Boolean isFallback) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.processId = processId;
		this.nodeId = nodeId;
		this.type = type;
		this.status = status;
		this.isMultiselect = isMultiselect;
		this.isOtherMan = isOtherMan;
		this.isAssignMan = isAssignMan;
		this.isRevoke = isRevoke;
		this.isFallback = isFallback;
	}

	/** full constructor */
	public ProcessNode(Date createTime, Date updateTime, String clientId, String processId, String nodeId, String name, String preNodeId, String nextNodeId, String type, String nodeLogic, String runnerFlag, String nextRunnerOrg, String status, Long runnerUserId, Long runnerPostId, Long runnerDepartId, Long runnerUsergroupId, String runnerOrgLevel, Boolean isMultiselect, Boolean isOtherMan, Boolean isAssignMan, Boolean isRevoke, Boolean isFallback) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.processId = processId;
		this.nodeId = nodeId;
		this.name = name;
		this.preNodeId = preNodeId;
		this.nextNodeId = nextNodeId;
		this.type = type;
		this.nodeLogic = nodeLogic;
		this.runnerFlag = runnerFlag;
		this.nextRunnerOrg = nextRunnerOrg;
		this.status = status;
		this.runnerUserId = runnerUserId;
		this.runnerPostId = runnerPostId;
		this.runnerDepartId = runnerDepartId;
		this.runnerUsergroupId = runnerUsergroupId;
		this.runnerOrgLevel = runnerOrgLevel;
		this.isMultiselect = isMultiselect;
		this.isOtherMan = isOtherMan;
		this.isAssignMan = isAssignMan;
		this.isRevoke = isRevoke;
		this.isFallback = isFallback;
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

	
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getPreNodeId() {
		return this.preNodeId;
	}

	public void setPreNodeId(String preNodeId) {
		this.preNodeId = preNodeId;
	}

	
	public String getNextNodeId() {
		return this.nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getNodeLogic() {
		return this.nodeLogic;
	}

	public void setNodeLogic(String nodeLogic) {
		this.nodeLogic = nodeLogic;
	}

	
	public String getRunnerFlag() {
		return this.runnerFlag;
	}

	public void setRunnerFlag(String runnerFlag) {
		this.runnerFlag = runnerFlag;
	}

	
	public String getNextRunnerOrg() {
		return this.nextRunnerOrg;
	}

	public void setNextRunnerOrg(String nextRunnerOrg) {
		this.nextRunnerOrg = nextRunnerOrg;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Long getRunnerUserId() {
		return this.runnerUserId;
	}

	public void setRunnerUserId(Long runnerUserId) {
		this.runnerUserId = runnerUserId;
	}

	
	public Long getRunnerPostId() {
		return this.runnerPostId;
	}

	public void setRunnerPostId(Long runnerPostId) {
		this.runnerPostId = runnerPostId;
	}

	
	public Long getRunnerDepartId() {
		return this.runnerDepartId;
	}

	public void setRunnerDepartId(Long runnerDepartId) {
		this.runnerDepartId = runnerDepartId;
	}

	
	public Long getRunnerUsergroupId() {
		return this.runnerUsergroupId;
	}

	public void setRunnerUsergroupId(Long runnerUsergroupId) {
		this.runnerUsergroupId = runnerUsergroupId;
	}

	
	public String getRunnerOrgLevel() {
		return this.runnerOrgLevel;
	}

	public void setRunnerOrgLevel(String runnerOrgLevel) {
		this.runnerOrgLevel = runnerOrgLevel;
	}

	
	public Boolean getIsMultiselect() {
		return this.isMultiselect;
	}

	public void setIsMultiselect(Boolean isMultiselect) {
		this.isMultiselect = isMultiselect;
	}

	
	public Boolean getIsOtherMan() {
		return this.isOtherMan;
	}

	public void setIsOtherMan(Boolean isOtherMan) {
		this.isOtherMan = isOtherMan;
	}

	
	public Boolean getIsAssignMan() {
		return this.isAssignMan;
	}

	public void setIsAssignMan(Boolean isAssignMan) {
		this.isAssignMan = isAssignMan;
	}

	
	public Boolean getIsRevoke() {
		return this.isRevoke;
	}

	public void setIsRevoke(Boolean isRevoke) {
		this.isRevoke = isRevoke;
	}

	
	public Boolean getIsFallback() {
		return this.isFallback;
	}

	public void setIsFallback(Boolean isFallback) {
		this.isFallback = isFallback;
	}

}