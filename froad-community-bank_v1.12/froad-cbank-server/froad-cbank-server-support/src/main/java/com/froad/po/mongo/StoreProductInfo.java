package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class StoreProductInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1415013610276300123L;
	
	private Long productId;
	private String productName;
	private String productImgge;
	
	public StoreProductInfo(){
		super();
	}

	public StoreProductInfo(Long productId, String productName,
			String productImgge) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productImgge = productImgge;
	}

	@JSONField(name = "product_id", serialize = true, deserialize = true)
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@JSONField(name = "product_name", serialize = true, deserialize = true)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@JSONField(name = "product_imgge", serialize = true, deserialize = true)
	public String getProductImgge() {
		return productImgge;
	}

	public void setProductImgge(String productImgge) {
		this.productImgge = productImgge;
	}
	
}
