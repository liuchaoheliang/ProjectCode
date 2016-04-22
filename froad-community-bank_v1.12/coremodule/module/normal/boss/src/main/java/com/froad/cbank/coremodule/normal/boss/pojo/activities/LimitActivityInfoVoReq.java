package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 类描述：限购活动查询请求实体类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-5-23下午1:16:48
 */
public class LimitActivityInfoVoReq extends Page implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7523548355863389524L;
	private String id;
	@NotEmpty(value = "客户端id不能为空!")
	private String clientId;
	private String actStatus;
	@NotEmpty(value = "活动名称不能为空!")
	private String actName;
	@NotEmpty(value = "活动类型不能为空!")
	private String activitiesType;// vip("0","VIP价"),
									// point("1","赠送积分"),limit("2","限购");
	@NotEmpty(value = "限购数量不能为空!")
	private String limitNum;
	// @NotEmpty(value = "积分值不能为空!")
	private String points;
	private String remark;
	private String VIPprice;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getActStatus() {
		return actStatus;
	}

	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivitiesType() {
		return activitiesType;
	}

	public void setActivitiesType(String activitiesType) {
		this.activitiesType = activitiesType;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVIPprice() {
		return VIPprice;
	}

	public void setVIPprice(String vIPprice) {
		VIPprice = vIPprice;
	}

}
