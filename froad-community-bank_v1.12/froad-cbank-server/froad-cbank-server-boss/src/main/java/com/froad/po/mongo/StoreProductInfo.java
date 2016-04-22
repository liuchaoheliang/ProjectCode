package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class StoreProductInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1415013610276300123L;
	
	@JSONField(name = "product_id", serialize = true, deserialize = true)
	private String productId;
	@JSONField(name = "product_name", serialize = true, deserialize = true)
	private String productName;
	@JSONField(name = "product_image", serialize = true, deserialize = true)
	private String productImage;
	@JSONField(name = "merchant_id", serialize = true, deserialize = true)
	private String merchantId;
	
	public StoreProductInfo(){
		super();
	}

	public StoreProductInfo(String productId, String productName,
			String productImage) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productImage = productImage;
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

	
	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
