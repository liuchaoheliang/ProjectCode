package com.froad.po.mysql;

import java.util.Date;


/**
 * Process entity.
 */

public class Process implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String processId;
	private String parentProcessId;
	private String name;
	private String displayName;
	private String type;
	private String typeDetail;
	private String status;
	private Integer version;
	private String creator;

	// Constructors

	/** default constructor */
	public Process() {
	}

	/** minimal constructor */
	public Process(Date createTime, Date updateTime, String clientId, String processId, String name, String status, Integer version) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.processId = processId;
		this.name = name;
		this.status = status;
		this.version = version;
	}

	/** full constructor */
	public Process(Date createTime, Date updateTime, String clientId, String processId, String parentProcessId, String name, String displayName, String type, String typeDetail, String status, Integer version, String creator) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.processId = processId;
		this.parentProcessId = parentProcessId;
		this.name = name;
		this.displayName = displayName;
		this.type = type;
		this.typeDetail = typeDetail;
		this.status = status;
		this.version = version;
		this.creator = creator;
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

	
	public String getParentProcessId() {
		return this.parentProcessId;
	}

	public void setParentProcessId(String parentProcessId) {
		this.parentProcessId = parentProcessId;
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

	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getTypeDetail() {
		return this.typeDetail;
	}

	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}