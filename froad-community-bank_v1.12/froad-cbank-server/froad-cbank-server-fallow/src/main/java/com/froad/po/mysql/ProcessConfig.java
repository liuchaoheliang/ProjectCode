package com.froad.po.mysql;

import java.util.Date;

/**
 * CbProcessConfig entity. 
 */


public class ProcessConfig implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String orgCodeLeve;
	private String type;
	private String typeDetail;
	private String processId;
	private String creator;

	// Constructors

	/** default constructor */
	public ProcessConfig() {
	}

	/** minimal constructor */
	public ProcessConfig(Date createTime, Date updateTime, String clientId, String orgCodeLeve, String processId) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.orgCodeLeve = orgCodeLeve;
		this.processId = processId;
	}

	/** full constructor */
	public ProcessConfig(Date createTime, Date updateTime, String clientId, String orgCodeLeve, String type, String typeDetail, String processId, String creator) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.orgCodeLeve = orgCodeLeve;
		this.type = type;
		this.typeDetail = typeDetail;
		this.processId = processId;
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

	
	public String getOrgCodeLeve() {
		return this.orgCodeLeve;
	}

	public void setOrgCodeLeve(String orgCodeLeve) {
		this.orgCodeLeve = orgCodeLeve;
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

	
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}