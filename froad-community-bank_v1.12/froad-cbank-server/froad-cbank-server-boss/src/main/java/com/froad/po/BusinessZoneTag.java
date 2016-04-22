/**
 * Project Name:froad-cbank-server-boss
 * File Name:BusinessZoneTag.java
 * Package Name:com.froad.po
 * Date:2015年10月22日下午3:32:38
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Date;

/**
 * ClassName:BusinessZoneTag
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 下午3:32:38
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BusinessZoneTag {
	
	private Long id;
	private String clientId; 
	//商圈标签名称
	private String zoneName;
	//序号
	private Long serialNumber;
	//一级区域ID
	private Long fareaId;
	//二级区域ID
	private Long sareaId;
	//三级区域ID
	private Long tareaId;
	//四级区域ID
	private Long oareaId;
	//描述
	private String desc;
	//状态
	private String status;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getFareaId() {
		return fareaId;
	}

	public void setFareaId(Long fareaId) {
		this.fareaId = fareaId;
	}

	public Long getSareaId() {
		return sareaId;
	}

	public void setSareaId(Long sareaId) {
		this.sareaId = sareaId;
	}

	public Long getTareaId() {
		return tareaId;
	}

	public void setTareaId(Long tareaId) {
		this.tareaId = tareaId;
	}

	public Long getOareaId() {
		return oareaId;
	}

	public void setOareaId(Long oareaId) {
		this.oareaId = oareaId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	
}
