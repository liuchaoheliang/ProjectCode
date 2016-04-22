package com.froad.po;

public class ProductDetailSimple {
	
	private String productId; //商品id
	private String clientId;//客户端
	
	
	private String name; //商品名
	private String fullName; //商品全名
	private double price; //销售价
	private double marketPrice; //市场价
	private int sellCount; //销售数量
	private String briefIntroduction; //简介
	private String smallImgUrl; //小图片地址
	private long startTime; //销售开始
	private long endTime; //销售结束
	private String isSeckill; //是否秒杀 0非秒杀,1秒杀,2秒杀未上架
	private String merchantId; //商户ID
	
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
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
