package com.froad.cbank.coremodule.module.normal.user.pojo;

public class AddProductPojo {

	private String merchantId;
	private String productId;
	private Integer quantity;
	private String orgCode;
	private String orgName;
	private String deliveryType;
	private String type;
	private Integer vipQuantity; 
	private String activeId;
	private String giveActiveId;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getGiveActiveId() {
		return giveActiveId;
	}
	public void setGiveActiveId(String giveActiveId) {
		this.giveActiveId = giveActiveId;
	}
	
}
