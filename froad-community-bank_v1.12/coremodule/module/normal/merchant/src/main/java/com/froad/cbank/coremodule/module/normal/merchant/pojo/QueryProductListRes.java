package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * @author Administrator
 *
 */
public class QueryProductListRes {
	private String fullName;// 商品全称
	private Integer store;// 库存
	private Double marketPrice;
	private Double price;// 销售价
	private Double cost; // 成本价
	private Long startTime;// 特惠开始时间
	private Long endTime;// 特惠结束数据
	private Long expireEndTime;// 有效期
	private String auditState;// 审核状态
	private String auditStateName;// 审核状态相对应的名称
	private String isMarketable;// 上下架
	private String productId;
    private String merchantId;
    private String type;
    private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
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

	public Long getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditStateName() {
		return auditStateName;
	}

	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
