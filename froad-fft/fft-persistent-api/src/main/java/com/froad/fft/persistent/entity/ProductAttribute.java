package com.froad.fft.persistent.entity;

/**
 * 商品属性
 * 
 * @author FQ
 * 
 */
public class ProductAttribute extends BaseEntity {

	// 属性类型：
	public enum AttributeType {
		text, number
	}

	private String name;// 属性名称
	private AttributeType attributeType;// 属性类型
	private Boolean isRequired;// 是否必填
	private Boolean isEnabled;// 是否启用
	private Integer orderValue;// 排序
	private Long productTypeId;//商品类型ID
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AttributeType getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
}
