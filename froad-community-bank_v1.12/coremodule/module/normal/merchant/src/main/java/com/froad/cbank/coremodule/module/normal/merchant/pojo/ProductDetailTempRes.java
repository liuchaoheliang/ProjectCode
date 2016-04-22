package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class ProductDetailTempRes {
	private String clientId;
	private String merchantId;
	private String merchantName;
	private String orgCode;
	private String orgName;
	private String productId;
	private String isMarketable;
	private String type;
	private String name;
	private String fullName;
	private Double price;
	private Double marketPrice;
	private Double cost;
	private Integer store;
	private Integer sellCount;
	// private Integer productCategoryId;
	// private String productCategoryName;
	// private String categoryTreePath;
	// private String categoryTreePathName;
	private String categoryId;
	private String categoryName;
	private String treePath;
	private List<String> categoryNameList;
	private String briefIntroduction;
	private String introduction;
	private String buyKnow;
	private Integer max;
	private Long startTime;
	private Long endTime;
	private String auditComment;
	private Long expireEndTime;
	private List<Image_Info_Res> imgList;
	private String auditState;// 审核状态
	private String auditStateName;// 审核状态相对应的名称
	private String codeUrl;
	
	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getAuditStateName() {
		return auditStateName;
	}

	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}

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

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public List<String> getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList(List<String> categoryNameList) {
		this.categoryNameList = categoryNameList;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getBuyKnow() {
		return buyKnow;
	}

	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
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

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public Long getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public List<Image_Info_Res> getImgList() {
		return imgList;
	}

	public void setImgList(List<Image_Info_Res> imgList) {
		this.imgList = imgList;
	}

}
