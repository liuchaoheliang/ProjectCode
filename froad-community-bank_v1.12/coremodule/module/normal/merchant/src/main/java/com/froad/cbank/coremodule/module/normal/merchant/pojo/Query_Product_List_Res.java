package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Product_List_Res {
	
	/**
	 * 商品id
	 */
	private String productId; 
	/**
	 * 是否上架
	 */
	private String isMarketable; 
	/**
	 * 上架时间
	 */
	private Long rackTime; 
	/**
	 * 商品类型
	 */
	private String type; 
	/**
	 * 1配送,2自提3配送或自提二者皆
	 */
	private String deliveryOption; 
	/**
	 * 商品名
	 */
	private String name; 
	/**
	 * 销售价
	 */
	private Double price; 
	/**
	 * 成本价
	 */
	private Double cost; 
	/**
	 * 市场价
	 */
	private Double marketPrice; 
	/**
	 * 商品库存数量
	 */
	private Integer store; 
	/**
	 * 商品审核状态
	 */
	private String auditState; 
	/**
	 * 团购或预售有效期开始
	 */
	private Long startTime; 
	/**
	 * 团购或预售有效期结束
	 */
	private Long endTime; 
	/**
	 * 团购或预售券有效起始日
	 */
	private Long expireStartTime; 
	/**
	 * 团购或预售券有效结束日
	 */
	private Long expireEndTime; 
	/**
	 * 提货-开始
	 */
	private Long deliveryStartTime; 
	/**
	 * 提货-结束
	 */
	private Long deliveryEndTime; 
	private String orgCode; 
	private String orgName;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Long getRackTime() {
		return rackTime;
	}
	public void setRackTime(Long rackTime) {
		this.rackTime = rackTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(Long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public Long getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public Long getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(Long deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}
	public Long getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(Long deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	} 
	
	
}
