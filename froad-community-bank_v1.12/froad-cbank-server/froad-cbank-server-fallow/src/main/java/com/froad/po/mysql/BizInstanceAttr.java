package com.froad.po.mysql;

import java.util.Date;


/**
 * CbBizInstanceAttr entity.
 */

public class BizInstanceAttr implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	private String clientId;
	private String name;
	private String fieldName;

	// Constructors

	/** default constructor */
	public BizInstanceAttr() {
	}

	/** full constructor */
	public BizInstanceAttr(Date createTime, Date updateTime, String clientId, String name, String fieldName) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.name = name;
		this.fieldName = fieldName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


}