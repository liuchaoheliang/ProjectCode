package com.froad.cbank.coremodule.normal.boss.pojo.refund;


/**
 * 类描述：退款商品响应对象
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:2015-5-1下午5:36:56
 */
public class RefGood {
	private String productId;//商品ID
	private String productName;//商品名称
	private String image;//商品图片
	private Integer quantity;//数量
	private Double price;//单价
	private Integer vipQuantity;//数量
	private Double vipPrice;//单价
	private Double priceSum;//小计
	private String merchantId;//商户ID
	private String merchantName;//商户名
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Double getPriceSum() {
		return priceSum;
	}
	public void setPriceSum(Double priceSum) {
		this.priceSum = priceSum;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
