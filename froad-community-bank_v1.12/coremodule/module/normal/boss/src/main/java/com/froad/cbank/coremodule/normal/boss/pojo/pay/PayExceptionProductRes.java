package com.froad.cbank.coremodule.normal.boss.pojo.pay;

public class PayExceptionProductRes {
	
	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 数量
	 */
	private Integer productCount;
	
	/**
	 * 商户名
	 */
	private String merchantName;
	
	/**
	 * 门店名
	 */
	private String outletName;
	
	/**
	 * 金额
	 */
	private Double money;
	
	/**
	 * 单价 
	 */
	private Double price;
	
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	
}
