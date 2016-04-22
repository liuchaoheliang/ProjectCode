package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Fallow_Execute_Base_Data_Req {
	private String outletId;
	private String merchantId;
	private String merchantName;
	private String outletName;
	private String outletFullName;
	private Long createTime;
	private String productName;
	private String productFullName;
	
	
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
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
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getOutletFullName() {
		return outletFullName;
	}
	public void setOutletFullName(String outletFullName) {
		this.outletFullName = outletFullName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMerchantFullName() {
		return merchantFullName;
	}
	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}
	public String getCategoryInfo() {
		return categoryInfo;
	}
	public void setCategoryInfo(String categoryInfo) {
		this.categoryInfo = categoryInfo;
	}
	public List<Long> getTypeInfo() {
		return typeInfo;
	}
	public void setTypeInfo(List<Long> typeInfo) {
		this.typeInfo = typeInfo;
	}
	public String getUserOrgCode() {
		return userOrgCode;
	}
	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
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
	private String productId;
	
	private String merchantFullName;
	

	private String categoryInfo;//商品分类
	private List<Long> typeInfo; // 商户类型id
	private String userOrgCode; // 当前机构号

	private Long startTime;// 团购创建时间
	private Long endTime;// 团购结束时间

	
}
