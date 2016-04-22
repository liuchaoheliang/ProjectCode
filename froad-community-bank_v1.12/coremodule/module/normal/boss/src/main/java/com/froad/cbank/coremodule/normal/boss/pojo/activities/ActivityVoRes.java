package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

public class ActivityVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id */
	private Long id;
	/** 活动名称 */
	private String activitiesName;
	/** 客户端id */
	private String clientId;
	/** 客户端名称 */
	private String clientName;
	/** 积分 */
	private String points;
	/** 活动开始时间 */
	private String beginTime;
	/** 活动结束时间 */
	private String endTime;
	/** 活动创建时间 */
	private String createTime;
	/** 活动状态 */
	private Integer status;
	/** VIP限购 */
	private Integer vipLimit;
	/** 限购规则 */
	private Integer limitNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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


	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getVipLimit() {
		return vipLimit;
	}

	public void setVipLimit(Integer vipLimit) {
		this.vipLimit = vipLimit;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

}
