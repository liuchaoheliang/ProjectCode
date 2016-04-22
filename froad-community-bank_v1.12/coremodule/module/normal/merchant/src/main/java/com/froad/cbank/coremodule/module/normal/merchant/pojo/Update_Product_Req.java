package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Update_Product_Req extends BasePojo {
    private String roleId;
    private String userName;
	@NotEmpty(value = "商品ID不能为空")
	private String productId;

	/**
	 * 商品对应商户所属机构编号
	 */
	private String orgCode;
	/**
	 * 是否上架 0未上架,1已上架,2已下架,3已删除
	 */
	private String isMarketable;
	/**
	 * 上架时间
	 */
	private String rackTime;
	/**
	 * 商品类型
	 */
	private String type;
	/**
	 * 1配送,2自提3配送或自提二者皆
	 */
	private String deliveryOption;
	/**
	 * 商品名
	 */
	@NotEmpty(value = "商品简称不能为空")
	private String name;
	/**
	 * 商品全名
	 */
	@NotEmpty(value = "商品全称不能为空")
	private String fullName;
	/**
	 * 销售价
	 */
	private Double price;
	/**
	 * 成本价
	 */
	private Double cost;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 商品重量
	 */
	private String weight;
	/**
	 * 重量单位
	 */
	private String weightUnit;
	/**
	 * 商品库存数量
	 */
	private Integer store;
	/**
	 * 销售数量
	 */
	private Integer sellCount;
	/**
	 * 排序(1-10)
	 */
	private Integer orderValue;
	/**
	 * 是否为精品
	 */
	private Boolean isBest;
	/**
	 * 是否限购
	 */
	private Boolean isLimit;
	/**
	 * 简介
	 */
	private String briefIntroduction;
	/**
	 * 介绍
	 */
	private String introduction;
	/**
	 * 商品须知
	 */
	private String buyKnow;
	/**
	 * 商品审核状态
	 */
	private String auditState;

	/**
	 * 团购或预售有效期开始
	 */
	private String startTime;
	/**
	 * 团购或预售有效期结束
	 */
	private String endTime;
	/**
	 * 团购或预售券有效起始日
	 */
	private String expireStartTime;
	/**
	 * 团购或预售券有效结束日
	 */
	private String expireEndTime;
	/**
	 * 团购或预售真实购买数量
	 */
	private Integer trueBuyerNumber;
	/**
	 * 团购或预售虚拟购买数量
	 */
	private Integer virtualBuyerNumber;
	/**
	 * 供货商
	 */
	private String productSupplier;
	/**
	 * 运费
	 */
	private Double deliveryMoney;
	/**
	 * 自提商品每家门店最大支持数量
	 */
	private Integer maxPerOutlet;
	/**
	 * 提货-开始
	 */
	private String deliveryStartTime;
	/**
	 * 提货-结束
	 */
	private String deliveryEndTime;
	/**
	 * 是否成功成团
	 */
	private Boolean clusterState;
	/**
	 * 成团类型
	 */
	private Boolean clusterType;
	/**
	 * 下架时间
	 */
	private String downTime;
	/**
	 * 审核时间
	 */
	private String auditTime;
	/**
	 * 审核备注
	 */
	private String auditComment;

	/**
	 * 最小购买数量
	 */
	private Integer min;
	/**
	 * 最大购买数量
	 */
	private Integer max;
	/**
	 * vip最小购买数量
	 */
	private Integer minVip;
	/**
	 * vip最大购买数量
	 */
	private Integer maxVip;
	/**
	 * 贴膜卡最大购买数量
	 */
	private Integer maxCard;

	private String categoryId;

	private List<Image_Info_Req> imgList;

	private String outletIds;

	/**
	 * 是否审核
	 */
	private String isAudit; // 1要审核，2不需要审核

	public String getOutletIds() {
		return outletIds;
	}

	public void setOutletIds(String outletIds) {
		this.outletIds = outletIds;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getRackTime() {
		return rackTime;
	}

	public void setRackTime(String rackTime) {
		this.rackTime = rackTime;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
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

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	public Boolean getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(String expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public String getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(String expireEndTime) {
		this.expireEndTime = expireEndTime;
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

	public String getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(String productSupplier) {
		this.productSupplier = productSupplier;
	}

	public Double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public Integer getMaxPerOutlet() {
		return maxPerOutlet;
	}

	public void setMaxPerOutlet(Integer maxPerOutlet) {
		this.maxPerOutlet = maxPerOutlet;
	}

	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(String deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(String deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
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

	public String getDownTime() {
		return downTime;
	}

	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMinVip() {
		return minVip;
	}

	public void setMinVip(Integer minVip) {
		this.minVip = minVip;
	}

	public Integer getMaxVip() {
		return maxVip;
	}

	public void setMaxVip(Integer maxVip) {
		this.maxVip = maxVip;
	}

	public Integer getMaxCard() {
		return maxCard;
	}

	public void setMaxCard(Integer maxCard) {
		this.maxCard = maxCard;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Image_Info_Req> getImgList() {
		return imgList;
	}

	public void setImgList(List<Image_Info_Req> imgList) {
		this.imgList = imgList;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
