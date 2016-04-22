package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 预售商品信息列表显示数据
 * @author Administrator
 *
 */
public class GroupProductVoRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;

	private String productId;	   //主键id
	private String orgName;	      //所属网点
	private String merchantName;  //商户
	private String productName;	  //商品名称
	private String consumeStartDate;//开始消费日期
	private String consumeEndDate;//最后消费日期
	private String auditState;	  //审核状态
	private Long auditTime;	  //审核时间
	private String auditStateName;      //审核状态
	private String isMarketable;     //上下架状态
	private String isMarketableName;     //上下架状态
	private String store;// 库存数量
	private String categoryName; // 商品名称
	private String price; // 团购价
	
	
	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getConsumeStartDate() {
		return consumeStartDate;
	}
	public void setConsumeStartDate(String consumeStartDate) {
		this.consumeStartDate = consumeStartDate;
	}
	public String getConsumeEndDate() {
		return consumeEndDate;
	}
	public void setConsumeEndDate(String consumeEndDate) {
		this.consumeEndDate = consumeEndDate;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getAuditStateName() {
		return auditStateName;
	}
	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}
	public String getIsMarketableName() {
		return isMarketableName;
	}
	public void setIsMarketableName(String isMarketableName) {
		this.isMarketableName = isMarketableName;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
