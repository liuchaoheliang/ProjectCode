package com.froad.cbank.coremodule.module.normal.user.pojo;

public class OrderProductSummaryPojo {

	/**
	   * 商品ID
	   */
	  public String productId; // required
	  /**
	   * 商品名称
	   */
	  public String productName; // required
	  /**
	   * 商品图片
	   */
	  public String productImage; // required
	  /**
	   * 商户名称
	   */
	  public String merchantName; // required
	  /**
	   * 商品类型
	   */
	  public String productType; // required
	  
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
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	 
	  
}
