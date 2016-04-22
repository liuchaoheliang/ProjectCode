package com.froad.po.mysql;

import java.util.Date;


/**
 * CbInstance entity.
 */

public class Instance implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String instanceId;
	private String processId;
	private String creator;
	private Date expireTime;
	private String lastUpdator;
	private Integer orderValue;
	private Integer version;

	// Constructors

	/** default constructor */
	public Instance() {
	}

	/** minimal constructor */
	public Instance(Date createTime, Date updateTime, String clientId, String instanceId, String processId, Date expireTime) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.processId = processId;
		this.expireTime = expireTime;
	}

	/** full constructor */
	public Instance(Date createTime, Date updateTime, String clientId, String instanceId, String processId, String creator, Date expireTime, String lastUpdator, Integer orderValue, Integer version) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.processId = processId;
		this.creator = creator;
		this.expireTime = expireTime;
		this.lastUpdator = lastUpdator;
		this.orderValue = orderValue;
		this.version = version;
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

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	// Property accessors

	
}