package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 普通商品列表响应
 * @author wangzhangxu
 * @date 2015-5-6 上午10:51:06
 */
public class OrdinaryProductVoRes implements Serializable {
	
	private static final long serialVersionUID = -3566083167150848231L;

	private String clientId;
	private String merchantId;
	private String merchantName;
	private String orgCode;
	private String orgName;
	private String productId;
	private String productName;
	private String productType;
	private String price;
	private String marketPrice;
	private String store;
	private String isMarketable;
	private String isMarketableName;
	
	public OrdinaryProductVoRes(){}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getIsMarketableName() {
		return isMarketableName;
	}

	public void setIsMarketableName(String isMarketableName) {
		this.isMarketableName = isMarketableName;
	}
	
	
}
