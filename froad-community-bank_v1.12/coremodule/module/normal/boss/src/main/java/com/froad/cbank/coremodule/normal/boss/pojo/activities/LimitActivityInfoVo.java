package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

/**
 * 类描述：限购活动信息实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-23下午1:19:21 
 */
public class LimitActivityInfoVo  implements Serializable{
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1520438127940148857L;
	
	private String id;
	private String actStatu;
	private String actName;
	private String clientId;
	private String createTime;
	private String limitNum;
	private String clientName;
	private String activitiesType;
	private String points;
	private String remark;
	private String VIPprice;
	private String VIPlimit;
	public String getActStatu() {
		return actStatu;
	}
	public void setActStatu(String actStatu) {
		this.actStatu = actStatu;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getVIPlimit() {
		return VIPlimit;
	}
	public void setVIPlimit(String vIPlimit) {
		VIPlimit = vIPlimit;
	}
	
}
