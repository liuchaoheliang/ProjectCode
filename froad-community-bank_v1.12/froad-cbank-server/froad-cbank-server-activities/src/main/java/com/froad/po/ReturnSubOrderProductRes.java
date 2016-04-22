package com.froad.po;

import java.io.Serializable;

public class ReturnSubOrderProductRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 商品id */
	private String productId;
	/** 普通满减优惠总价*/
	private Double price;
    /** Vip满减优惠总价*/
	private Double vipPrice;
    /** 普通红包优惠总价*/
	private Double vouPrice;
    /** Vip红包优惠总价*/
	private Double vipVouPrice;
    /** vip数量 */
    private Integer vipCount;
    /** 普通数量 */
    private Integer normalCount;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getVipCount() {
		return vipCount;
	}
	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}
	public Integer getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}
	public Double getVouPrice() {
		return vouPrice;
	}
	public void setVouPrice(Double vouPrice) {
		this.vouPrice = vouPrice;
	}
	public Double getVipVouPrice() {
		return vipVouPrice;
	}
	public void setVipVouPrice(Double vipVouPrice) {
		this.vipVouPrice = vipVouPrice;
	}
	
}
