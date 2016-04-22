package com.froad.cbank.coremodule.module.normal.user.pojo;

public class RefundProduct{
	
	private String  productId;
	private String   productName;
	private String  image ;
	private int  quantity ;
	private double   price;
	private int   vipQuantity;
	private double   vipPrice;
	private double   priceSum;
	
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public double getPriceSum() {
		return priceSum;
	}
	public void setPriceSum(double priceSum) {
		this.priceSum = priceSum;
	}
	
	
}
