package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class MerchantWeightActivityTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3305172567612238972L;
	
	// id
	private Long id;

	// 客户端号
	private String clientId;

	// 活动ID
	private Long activityId;
	
	// 活动 编号
	private String activityNo;

	// 商户ID/门店ID/商品ID
	private String elementId;

	// 权重
	private String weight;

	// 活动类型: 商户活动1; 门店活动2; 商品活动3
	private String activityType;

	// 创建时间
	private Date createTime;

	// 更新时间
	private Date updateTime;
	
	// 商户名称
	private String merchantName;
	
	// 营业执照
	private String license;
	
	// 操作人
	private String operator;
	
	// 所在地区
	private String areaName;

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

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}
