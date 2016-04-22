package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class WeightActivityTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// id
	private Long id;
	
	// 客户端号
	private String clientId;
	
	// 活动标签表id
	private Long activityId;
	
	// 活动编号
	private String activityNo;
	
	// 商户ID/门店ID/商品ID
	private String elementId;
	
	// 权重
	private String weight;
	
	// 活动类型: 商户活动1; 门店活动2; 商品活动3
	private String activityType;
	
	// 操作员
	private String operator;
	
	// 创建时间
	private Date createTime;

	// 更新时间
	private Date updateTime;

	// 商户名称/门店名称/商品名称
	private String elementName;

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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	
}
