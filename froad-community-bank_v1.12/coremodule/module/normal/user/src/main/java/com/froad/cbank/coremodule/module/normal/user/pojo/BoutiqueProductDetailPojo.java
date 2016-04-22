/**
 * Project Name:coremodule-user
 * File Name:BoutiqueProductDetailPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年12月4日下午5:39:35
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:BoutiqueProductDetailPojo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月4日 下午5:39:35
 * @author   wm
 * @version  
 * @see 	 
 */
public class BoutiqueProductDetailPojo {
	
	public String productId;
	   
	 
	public String clientId;
	
	 
	public String merchantId;
	
	public String merchantName;
	
	 
	public String name;
	
	 
	public String fullName;
	
	 
	public String briefIntroduction;
	
	 
	public double marketPrice;
	
	 
	public double price;
	
	 
	public double vipPrice;
	
	 
	public int min;
	
	 
	public int max;
	
	 
	public int maxVip;
	
	 
	public int sellCount;
	
	 
	public int store;
	
	 
	public boolean isRecommend;
	
	 
	public boolean isNew;
	
	 
	public boolean isHot;
	
	 
	public long deliveryTime;
	
	 
	public String introduction;
	
	 
	public String buyKnow;
	
	 
	public String afterShop;
	
	 
	public List<String> imageUrls;
	
	public String type;
	
	private String isSeckill;
	
	/**
	 * activePojo:关联的活动信息
	 */
	private List<FindActivePojo> activePojo = new ArrayList<FindActivePojo>();
	
	private Boolean isCollected;//是否已收藏
	/*
	 * 普通价格可购买数量	
	 */
	private  int num;
	/*VIP价格可购买数量
	 */
	private  int vipNum;
	/*
	 * 可购买的总数量（-1表示没有限制）
	 */
	private  int totalNum;
	
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
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


	public int getSellCount() {
		return sellCount;
	}


	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}


	public int getStore() {
		return store;
	}


	public void setStore(int store) {
		this.store = store;
	}


	public boolean isRecommend() {
		return isRecommend;
	}


	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public boolean isHot() {
		return isHot;
	}


	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}


	public long getDeliveryTime() {
		return deliveryTime;
	}


	public void setDeliveryTime(long deliveryTime) {
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


	public List<String> getImageUrls() {
		return imageUrls;
	}


	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	
	public List<FindActivePojo> getActivePojo() {
		return activePojo;
	}
	public void setActivePojo(List<FindActivePojo> activePojo) {
		this.activePojo = activePojo;
	}


	public Boolean getIsCollected() {
		return isCollected;
	}


	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	
	public String getIsSeckill() {
		return isSeckill;
	}


	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	

	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getVipNum() {
		return vipNum;
	}


	public void setVipNum(int vipNum) {
		this.vipNum = vipNum;
	}


	public int getTotalNum() {
		return totalNum;
	}
	
	public String getMerchantName() {
		return merchantName;
	}


	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}


	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
}
