package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * VIP商品推荐
 */
public class VipPresalePojo {
	  /**
	   * 商户ID
	   */
	  public String merchantId; // required
	  /**
	   * 商品id
	   */
	  public String productId; // required
	  /**
	   * 商品名
	   */
	  public String name; // required
	  /**
	   * 销售价
	   */
	  public double price; // required
	  /**
	   * 市场价
	   */
	  public double marketPrice; // required
	  /**
	   * 销售数量
	   */
	  public int sellCount; // required
	  /**
	   * 销售有效期开始
	   */
	  public long startTime; // required
	  /**
	   * 销售有效期结束
	   */
	  public long endTime; // required
	  /**
	   * 简介
	   */
	  public String briefIntroduction; // required
	  /**
	   * 小图片地址
	   */
	  public String smallImgUrl; // required
	  /**
	   * 商品库存数量
	   */
	  public int store; // required
	  /**
	   * VIP价
	   */
	  public double vipPrice; // required
	  /**
	   * 服务器时间
	   */
	  public long serverTime; // required
	  
	  
	  
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getSellCount() {
		return sellCount;
	}
	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public long getServerTime() {
		return serverTime;
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	 
	  
	  
	  
	  
	  
	  
}
