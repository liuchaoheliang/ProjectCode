package com.froad.po.refund;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class RefundProduct implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商品ID
	 */
	private String productId = null;
	/**
	 * 商品名
	 */
	private String productName = null;
	/**
	 * 商品图片URL
	 */
	private String imageUrl = null;
	/**
	 * 商品数量
	 */
	private Integer quantity = null;
	/**
	 * 商品单价
	 */
	private Integer price = null;
	/**
	 * VIP商品数量
	 */
	private Integer vipQuantity = null;
	/**
	 * VIP商品单价
	 */
	private Integer vipPrice = null;
	
	/* ***记录优惠的信息,退款失败时需要通知给活动平台** */
	/**
	 * 优惠的商品数量
	 */
	private Integer discountQuantity = null;
	/**
	 * 优惠的VIP商品数量
	 */
	private Integer discountVipQuantity = null;
	
	private Integer refundTotalCash;;
	
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * @return the productId
	 */
	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the imageUrl
	 */
	@JSONField(name="image")
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	@JSONField(name="image")
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the quantity
	 */
	@JSONField(name="quantity")
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	@JSONField(name="quantity")
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the price
	 */
	@JSONField(name="price")
	public Integer getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	@JSONField(name="price")
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * @return the vipQuantity
	 */
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	/**
	 * @param vipQuantity the vipQuantity to set
	 */
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	/**
	 * @return the vipPrice
	 */
	public Integer getVipPrice() {
		return vipPrice;
	}
	/**
	 * @param vipPrice the vipPrice to set
	 */
	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
	}
	@JSONField(name="active_id")
	public String getActiveId() {
		return activeId;
	}

	@JSONField(name="active_id")
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	
	@JSONField(name="discount_quantity")
	public Integer getDiscountQuantity() {
		return discountQuantity;
	}
	@JSONField(name="discount_quantity")
	public void setDiscountQuantity(Integer discountQuantity) {
		this.discountQuantity = discountQuantity;
	}
	@JSONField(name="discount_vip_quantity")
	public Integer getDiscountVipQuantity() {
		return discountVipQuantity;
	}
	@JSONField(name="discount_vip_quantity")
	public void setDiscountVipQuantity(Integer discountVipQuantity) {
		this.discountVipQuantity = discountVipQuantity;
	}
	@JSONField(name="refund_total_cash")
	public Integer getRefundTotalCash() {
		return refundTotalCash;
	}
	@JSONField(name="refund_total_cash")
	public void setRefundTotalCash(Integer refundTotalCash) {
		this.refundTotalCash = refundTotalCash;
	}
	
}
