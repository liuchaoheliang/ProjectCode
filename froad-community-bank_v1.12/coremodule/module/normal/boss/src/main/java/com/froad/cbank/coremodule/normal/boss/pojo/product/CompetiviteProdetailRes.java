package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

/**
 * 
 * @ClassName: CompetiviteProdetailRes
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月30日 上午10:47:51 
 * @desc <p>精品商城商品详情查询返回实体</p>
 */
public class CompetiviteProdetailRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2645241546718024449L;
	
	private String clientId;
	private String merchantId;
	private List<FileVo> images;
	private String name;
	private String fullName;
	private String briefIntroduction;
	private String seoKeyWords;
	private double marketPrice;
	private double price;
	private double vipPrice;
	private long categoryId;
	private int min;
	private int max;
	private int maxVip;
	private int store;
	private Boolean isRecommend;
	private Boolean isNew;
	private Boolean isHot;
	private Boolean isBatchDelivery;
	private String deliveryTime;//预计发货时间
	private String introduction;
	private String buyKnow;
	private String afterShop;
	private String type;
	private long id;
	private String productId;
	private String categoryName;
	private String clientName;
	private String merchantName;
	private String marketableStatus;//0未上架,1已上架,2已下架,3已删除,4禁用下架
	private String createTime;
	  /**
	   * 是否秒杀 0非秒杀,1秒杀
	   */
	private String isSeckill;
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
	public List<FileVo> getImages() {
		return images;
	}
	public void setImages(List<FileVo> images) {
		this.images = images;
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
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getSeoKeyWords() {
		return seoKeyWords;
	}
	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMaxVip() {
		return maxVip;
	}
	public void setMaxVip(int maxVip) {
		this.maxVip = maxVip;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
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
	public Boolean getIsBatchDelivery() {
		return isBatchDelivery;
	}
	public void setIsBatchDelivery(Boolean isBatchDelivery) {
		this.isBatchDelivery = isBatchDelivery;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
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
	public String getAfterShop() {
		return afterShop;
	}
	public void setAfterShop(String afterShop) {
		this.afterShop = afterShop;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMarketableStatus() {
		return marketableStatus;
	}
	public void setMarketableStatus(String marketableStatus) {
		this.marketableStatus = marketableStatus;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	
	
	
	
}
