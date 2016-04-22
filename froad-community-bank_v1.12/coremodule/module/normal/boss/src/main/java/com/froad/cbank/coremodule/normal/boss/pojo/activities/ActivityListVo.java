package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ActivityListVo extends Page implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 活动名称 */
	private String activitiesName;
	
	/** 客户端id */
	private String clientId;
	
	/** 活动状态 */
	private String status;
	
	/** 活动类型 */
	private String activitiesType;

	public String getActivitiesName() {
		return activitiesName;
	}

	public void setActivitiesName(String activitiesName) {
		this.activitiesName = activitiesName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActivitiesType() {
		return activitiesType;
	}

	public void setActivitiesType(String activitiesType) {
		this.activitiesType = activitiesType;
	}

}
