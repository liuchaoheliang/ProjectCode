package com.froad.po;

import java.util.Date;

public class ProductTemp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8156460449162569538L;
	
	private Long id;
	private String clientId;
	private String merchantId;
	private String orgCode;
	private String productId;
	private String isMarketable;
	private Date createTime;
	private Date rackTime;
	private Date downTime;
	private Date startTime;
	private Date endTime;
	private String type;
	private String deliveryOption;
	private String name;
	private String fullName;
	private Integer price;
	private Integer cost;
	private Integer marketPrice;
	private Integer store;
	private Boolean isLimit;
	private Integer point;
	private String briefIntroduction;
	private String introduction;
	private String buyKnow;
	private Integer sellCount;
	private Integer deliveryMoney;
	private String isSeckill;// 0非秒杀,1秒杀,2秒杀未上架
	private String merchantName;// 商户名称
	private String categoryTreePath;// 商品分类TreePath
	private String platType;// 新加商品的平台
	private Boolean isPoint;// 是否参与送积分活动
	private Integer vipPrice;
	private String codeUrl; // 二维码code
	private Date expireStartTime;
	private Date expireEndTime;
	private Date deliveryTime;// 预计发货时间
	private String seoKeyWords;// 搜索关键词
	private String afterShop;// 售后说明
	private String goodFlag;// 荐 热 新
	private String primeval;// 原商品信息
	private String photoList;// 相册列表
	private String buyLimit;// 购买限制信息
	private String auditId;// 审核流水号

	private Integer orderValue;

	private String proOrgCode;// 一级机构
	private String cityOrgCode;// 二级机构
	private String countryOrgCode;// 三级机构
	private String productSupplier;
	private Integer maxPerOutlet;
	private Date deliveryStartTime;
	private Date deliveryEndTime;
	private Integer trueBuyerNumber;
	private Integer virtualBuyerNumber;
	private Boolean clusterState;
	private Boolean clusterType;

	private String weight;
	private String weightUnit;
	private Boolean isBest;

	private String auditState;
	private String auditOrgCode;// 起始审核机构编号
	private String auditStartOrgCode;
	private String auditEndOrgCode;// 最终审核机编号
	private String auditStage;
	private Date auditTime;
	private String auditComment;
	private String reviewStaff;
	private String auditStaff;
	private Boolean isHistory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRackTime() {
		return rackTime;
	}

	public void setRackTime(Date rackTime) {
		this.rackTime = rackTime;
	}

	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Boolean getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
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

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Integer getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Integer deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getIsSeckill() {
		return isSeckill;
	}

	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCategoryTreePath() {
		return categoryTreePath;
	}

	public void setCategoryTreePath(String categoryTreePath) {
		this.categoryTreePath = categoryTreePath;
	}

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public Boolean getIsPoint() {
		return isPoint;
	}

	public void setIsPoint(Boolean isPoint) {
		this.isPoint = isPoint;
	}

	public Integer getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public Date getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(Date expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public Date getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Date expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSeoKeyWords() {
		return seoKeyWords;
	}

	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}

	public String getAfterShop() {
		return afterShop;
	}

	public void setAfterShop(String afterShop) {
		this.afterShop = afterShop;
	}

	public String getGoodFlag() {
		return goodFlag;
	}

	public void setGoodFlag(String goodFlag) {
		this.goodFlag = goodFlag;
	}

	public String getPrimeval() {
		return primeval;
	}

	public void setPrimeval(String primeval) {
		this.primeval = primeval;
	}

	public String getPhotoList() {
		return photoList;
	}

	public void setPhotoList(String photoList) {
		this.photoList = photoList;
	}

	public String getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(String buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getProOrgCode() {
		return proOrgCode;
	}

	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
	}

	public String getCityOrgCode() {
		return cityOrgCode;
	}

	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}

	public String getCountryOrgCode() {
		return countryOrgCode;
	}

	public void setCountryOrgCode(String countryOrgCode) {
		this.countryOrgCode = countryOrgCode;
	}

	public String getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(String productSupplier) {
		this.productSupplier = productSupplier;
	}

	public Integer getMaxPerOutlet() {
		return maxPerOutlet;
	}

	public void setMaxPerOutlet(Integer maxPerOutlet) {
		this.maxPerOutlet = maxPerOutlet;
	}

	public Date getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(Date deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public Integer getTrueBuyerNumber() {
		return trueBuyerNumber;
	}

	public void setTrueBuyerNumber(Integer trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}

	public Integer getVirtualBuyerNumber() {
		return virtualBuyerNumber;
	}

	public void setVirtualBuyerNumber(Integer virtualBuyerNumber) {
		this.virtualBuyerNumber = virtualBuyerNumber;
	}

	public Boolean getClusterState() {
		return clusterState;
	}

	public void setClusterState(Boolean clusterState) {
		this.clusterState = clusterState;
	}

	public Boolean getClusterType() {
		return clusterType;
	}

	public void setClusterType(Boolean clusterType) {
		this.clusterType = clusterType;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}

	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}

	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}

	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getReviewStaff() {
		return reviewStaff;
	}

	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public Boolean getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Boolean isHistory) {
		this.isHistory = isHistory;
	}
	
}
