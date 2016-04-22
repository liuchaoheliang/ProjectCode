package com.froad.po;

import java.util.Date;

public class WeightActivityTag {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端ID
	 */	
	private String clientId;
	/**
	 * 活动ID
	 */		
	private String activityId;
	/**
	 * 商户ID/门店ID/商品ID
	 */			
	private String elementId;
	/**
	 * 权重
	 */		
	private Long weight;
	/**
	 * 活动类型：1商户活动；2门店活动；3商品活动
	 */		
	private String activityType;
	/**
	 * 操作员
	 */		
	private String operator;
	/**
	 * 创建时间
	 */		
	private Date createTime;
	/**
	 * 更新时间
	 */		
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
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

}
