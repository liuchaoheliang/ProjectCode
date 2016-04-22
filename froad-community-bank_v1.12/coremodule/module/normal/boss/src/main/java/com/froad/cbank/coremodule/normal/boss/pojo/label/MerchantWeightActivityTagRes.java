package com.froad.cbank.coremodule.normal.boss.pojo.label;

import java.io.Serializable;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月23日 下午1:52:40
 * @desc 返回商户活动信息
 */
public class MerchantWeightActivityTagRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1081349665427523988L;
	/**
	   * id *
	   */
	  private long id; // optional
	  /**
	   * 客户端ID *
	   */
	  private String clientId; // optional
	  /**
	   * 活动编号 *
	   */
	  private String activityNo; // optional
	  /**
	   * 商户ID/门店ID/商品ID *
	   */
	  private String elementId; // optional
	  /**
	   * 权重 *
	   */
	  private String weight; // optional
	  /**
	   * 活动类型: 商户活动1; 门店活动2; 商品活动3 *
	   */
	  private String activityType; // optional
	  /**
	   * 创建时间 *
	   */
	  private String createTime; // optional
	  /**
	   * 更新时间 *
	   */
	  private String updateTime; // optional
	  /**
	   * 商户名称 *
	   */
	  private String merchantName; // optional
	  /**
	   * 营业执照 *
	   */
	  private String license; // optional
	  /**
	   * 操作人 *
	   */
	  private String operator; // optional
	  /**
	   * 活动id
	   */
	  private long activityId; // optional
	  /**
	   * 地区
	   */
	  private String areaName;
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	  
}
