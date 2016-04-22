package com.froad.vo;

import java.util.Date;
import java.util.List;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductImage;

/**
 * 可以修改的商品字段
 * @author wangyan
 *
 */
public class ProductUpdateableField {

	private String clientId;
	private String merchantId;
	private String productId;
	private Date startTime;
    private Date endTime;
	private String deliveryOption;
	private String name;
	private String fullName;
	private Integer price;
	private Integer cost;
	private Integer marketPrice;
	private Integer store;
	private Boolean isLimit;
	private String briefIntroduction;
	private String introduction;
	private String buyKnow;
	private Integer deliveryMoney;
	private String isSeckill;//0非秒杀,1秒杀,2秒杀未上架
	private String categoryTreePath;//商品分类TreePath
	private Integer vipPrice;
	private Date expireStartTime;
	private Date expireEndTime;
	private Date deliveryTime;//预计发货时间
	private String seoKeyWords;//搜索关键词
	private String afterShop;//售后说明
	private String goodFlag;//荐 热 新
	private Integer orderValue;
	private String productSupplier;
	private Integer maxPerOutlet;
	private Date deliveryStartTime;
	private Date deliveryEndTime;
	private Boolean clusterState;
	private Boolean clusterType;
	private String weight;
	private String weightUnit;
	private Boolean isBest;
	private List<ProductImage> imageInfos;// 图片详情
	private ProductBuyLimit buyLimit;//购买限制信息
	
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
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getCategoryTreePath() {
		return categoryTreePath;
	}
	public void setCategoryTreePath(String categoryTreePath) {
		this.categoryTreePath = categoryTreePath;
	}
	public Integer getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
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
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
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
	public List<ProductImage> getImageInfos() {
		return imageInfos;
	}
	public void setImageInfos(List<ProductImage> imageInfos) {
		this.imageInfos = imageInfos;
	}
	public ProductBuyLimit getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(ProductBuyLimit buyLimit) {
		this.buyLimit = buyLimit;
	}
	
}
