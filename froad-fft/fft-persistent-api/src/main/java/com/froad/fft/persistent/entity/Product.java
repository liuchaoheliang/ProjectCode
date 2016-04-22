package com.froad.fft.persistent.entity;

import java.util.Date;

/**
 * 商品
 * @author FQ
 *
 */
public class Product extends BaseEntity {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private String sn;//编号
	private String name;//名称 
	private String fullName;//全称
	private String price;//销售价
	private String cost;//成本价
	private String marketPrice;//市场价
	private String image;// 展示图片
	private String weight;		//商品重量
	private String weightUnit;	//重量单位 （g、kg、t）
	private Integer store;		//商品库存数量
	private Integer freezeStore;	//冻结库存数量
	private Integer warnNumber;	//警告数量
	private Boolean isMarketable;// 是否上架
	private Boolean isBest;		//是否精品 0-否 1-是
	private Boolean isNew;		//是否新品 0-否 1-是
	private Boolean isHot;		//是否热销 0-否 1-是
	private Integer hits;//点击数
	private String introduction;//介绍
	private String description;//备注
	
	private String keyword;//搜索关键词
	private String seoTitle;//页面标题 
	private String seoKeywords;//页面关键词 
	private String seoDescription;//页面描述
	
	private Date rackTime;//上架时间
	private String inspectors;//审核人
	
	private Long productCategoryId;//商品分类ID
	private Long productTypeId;//商品类型
	
	private Boolean isEnableGroup;//是否启用团购
	private Long productGroupId;//商品团购信息
	
	private Boolean isEnablePresell;//是否启用预售
	private Long productPresellId;//商品预售信息
	
	private Integer orderValue;//排序
	private Long merchantId;//所属商户
	private Boolean collectToFroad;//是否收到方付通账户
	private Long clientId;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public Integer getFreezeStore() {
		return freezeStore;
	}
	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}
	public Integer getWarnNumber() {
		return warnNumber;
	}
	public void setWarnNumber(Integer warnNumber) {
		this.warnNumber = warnNumber;
	}
	public Boolean getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Boolean getIsBest() {
		return isBest;
	}
	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public Date getRackTime() {
		return rackTime;
	}
	public void setRackTime(Date rackTime) {
		this.rackTime = rackTime;
	}
	
	public String getInspectors() {
		return inspectors;
	}
	public void setInspectors(String inspectors) {
		this.inspectors = inspectors;
	}
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public Long getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Boolean getIsEnableGroup() {
		return isEnableGroup;
	}
	public void setIsEnableGroup(Boolean isEnableGroup) {
		this.isEnableGroup = isEnableGroup;
	}
	public Long getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(Long productGroupId) {
		this.productGroupId = productGroupId;
	}
	public Boolean getIsEnablePresell() {
		return isEnablePresell;
	}
	public void setIsEnablePresell(Boolean isEnablePresell) {
		this.isEnablePresell = isEnablePresell;
	}
	public Long getProductPresellId() {
		return productPresellId;
	}
	public void setProductPresellId(Long productPresellId) {
		this.productPresellId = productPresellId;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Boolean getCollectToFroad() {
		return collectToFroad;
	}
	public void setCollectToFroad(Boolean collectToFroad) {
		this.collectToFroad = collectToFroad;
	}
	
}
