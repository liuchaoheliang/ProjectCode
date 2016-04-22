package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProductPojo {
	
	private String clientId;
	private String merchantId ;
	private String productId;
	private String type;
	private String name;
	private String price;
	private String marketPrice;
	private String store;
	private String sellCount ;
	private String startTime;
	private String endTime ;
	private String smallImgUrl;
	private String expireStartTime;
	private String expireEndTime;
	private String deliveryStartTime;
	private String deliveryEndTime;
	private String briefIntroduction;
	private String isSeckill;
	private String serverTime;
	private String secStore;
	private String secStartTime;
	private String secEndTime;
	/**
	   * 是否参与送积分活动
	   */
	public Boolean isPoint; // required
	  /**
	   * 是否参与VIP规则活动
	   */
	public Boolean isVip; // required
	  /**
	   * VIP价
	   */
	public String vipPrice; // required
	
	
	/**
	 * activePojo:关联的活动信息
	 */
	private List<FindActivePojo> activePojo = new ArrayList<FindActivePojo>();
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSellCount() {
		return sellCount;
	}
	public void setSellCount(String sellCount) {
		this.sellCount = sellCount;
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
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
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
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public String getSecStore() {
		return secStore;
	}
	public void setSecStore(String secStore) {
		this.secStore = secStore;
	}
	public String getSecStartTime() {
		return secStartTime;
	}
	public void setSecStartTime(String secStartTime) {
		this.secStartTime = secStartTime;
	}
	public String getSecEndTime() {
		return secEndTime;
	}
	public void setSecEndTime(String secEndTime) {
		this.secEndTime = secEndTime;
	}
	public Boolean getIsPoint() {
		return isPoint;
	}
	public void setIsPoint(Boolean isPoint) {
		this.isPoint = isPoint;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	public String getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}
	public List<FindActivePojo> getActivePojo() {
		return activePojo;
	}
	public void setActivePojo(List<FindActivePojo> activePojo) {
		this.activePojo = activePojo;
	}

	
}
