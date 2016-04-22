/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductWeightActivityTag.java
 * Package Name:com.froad.po
 * Date:2015年10月28日上午11:29:23
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:ProductWeightActivityTag
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 上午11:29:23
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProductWeightActivityTag implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 7650622428323182742L;
	
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

	// 商品名称
	private String productName;
	
	// 所属商户ID
	private String merchantId;
	
	// 所属商户
	private String merchantName;

	// 操作人
	private String operator;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
