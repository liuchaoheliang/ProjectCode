package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Member_Product_VerifyInfo_Res {
	/**
	 * 商品id
	 */
	private String productId; // required
	/**
	 * 商品名称
	 */
	private String productName; // required
	/**
	 * 本次验证成功商品数量
	 */
	private int validProductsCount; // required
	/**
	 * 未验证商品数量(key为商品id value为数量)
	 */
	private int noValidProductsCount; // required

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

	public int getValidProductsCount() {
		return validProductsCount;
	}

	public void setValidProductsCount(int validProductsCount) {
		this.validProductsCount = validProductsCount;
	}

	public int getNoValidProductsCount() {
		return noValidProductsCount;
	}

	public void setNoValidProductsCount(int noValidProductsCount) {
		this.noValidProductsCount = noValidProductsCount;
	}

}
