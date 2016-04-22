package com.froad.fft.persistent.entity;

/**
 * 商品属性值
 * @author FQ
 *
 */
public class ProductAttributeValue {
	
	private Long productId;//商品ID
	private Long productAttributeId;//商品属性ID
	private String value;//值
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductAttributeId() {
		return productAttributeId;
	}
	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
