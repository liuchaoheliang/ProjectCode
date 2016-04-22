package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ActivityVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id */
	private Long id;
	/** 活动名称 */
	@NotEmpty(value = "活动名称不能为空!")
	private String activitiesName;
	/** 客户端id */
	@NotEmpty(value = "客户端id不能为空!")
	private String clientId;
	/** 积分值 */
	@NotEmpty(value = "积分值不能为空!")
	private String points;
	/** 创建时间(预留) */
	private String createTime;
	/** 活动状态(0:未生效1:生效2:已作废) */
	private Integer status;
	/** 活动类型 */
	private String activitiesType;

	/** VIP限购 */
	private Integer vipLimit;
	/** 限购规则 */
	private Integer limitNum;
	// /** 是否启用 (被Status代替) */
	// private Boolean isEnable;
	
	
	private long begDate; 
	private long startDate; 
	private long endDate; 
	private Long beginTime;
	private Long endTime;
	
	
	public long getBegDate() {
		return begDate;
	}
	public void setBegDate(long begDate) {
		this.begDate = begDate;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
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
	public String getActivitiesType() {
		return activitiesType;
	}
	public void setActivitiesType(String activitiesType) {
		this.activitiesType = activitiesType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
