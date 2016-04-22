package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProductListPojo{
	 /**
	   * 商品id
	   */
	  private String productId; // required
	  /**
	   * 商品名
	   */
	  private String name; // required
	  /**
	   * 商品全名
	   */
	  private String fullName; // required
	  /**
	   * 销售价
	   */
	  private double price; // required
	  /**
	   * 市场价
	   */
	  private double marketPrice; // required
	  /**
	   * 销售数量
	   */
	  private int sellCount; // required  
	  /**
	   * 图片
	   */
	  private String smallImgUrl;
	  /**
	   * 简介
	   */
	 
	  private String briefIntroduction; // required
	  /**
	   * 团购或预售有效期开始
	   */
	  private long startTime; // required
	  /**
	   * 团购或预售有效期结束
	   */
	  private long endTime; // required
	  /**
	   * 是否秒杀 0非秒杀,1秒杀,2秒杀未上架
	   */
	  private String isSeckill; // required
	  /**
	   * 服务器时间
	   */
	  private long serverTime; // required
	  /**
	   * 秒杀开始时间
	   */
	  private long secStartTime; // required
	  /**
	   * 秒杀结束时间
	   */
	  private long secEndTime; // required
	  
	  /**
	   * 评论星级
	   */
	  public int starLevel; // required
	  /**
	   * 距离
	   */
	  public double distance; // required
	  /**
	   * 门店地址
	   */
	  public String address; // required
	  
	  
		/**
		 * activePojo:关联的活动信息
		 */
	  private List<FindActivePojo> activePojo = new ArrayList<FindActivePojo>();
		
	  
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
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

	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public long getServerTime() {
		return serverTime;
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	public long getSecStartTime() {
		return secStartTime;
	}
	public void setSecStartTime(long secStartTime) {
		this.secStartTime = secStartTime;
	}
	public long getSecEndTime() {
		return secEndTime;
	}
	public void setSecEndTime(long secEndTime) {
		this.secEndTime = secEndTime;
	}
	public int getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<FindActivePojo> getActivePojo() {
		return activePojo;
	}
	public void setActivePojo(List<FindActivePojo> activePojo) {
		this.activePojo = activePojo;
	}

	  
}

