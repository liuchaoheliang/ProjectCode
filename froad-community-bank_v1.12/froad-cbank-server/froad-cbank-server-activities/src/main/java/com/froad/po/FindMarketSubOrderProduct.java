package com.froad.po;

import java.io.Serializable;

public class FindMarketSubOrderProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 商品id */
	private String productId;
    /** vip满减金额 */
	private Double vipCutMoney;
    /** 满减金额 */
	private Double cutMoney;
	/** vip代金券/红包金额 */
	private Double vipVouMoney;
    /** 代金券/红包金额 */
	private Double vouMoney;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Double getVipCutMoney() {
		return vipCutMoney;
	}
	public void setVipCutMoney(Double vipCutMoney) {
		this.vipCutMoney = vipCutMoney;
	}
	public Double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}
	public Double getVipVouMoney() {
		return vipVouMoney;
	}
	public void setVipVouMoney(Double vipVouMoney) {
		this.vipVouMoney = vipVouMoney;
	}
	public Double getVouMoney() {
		return vouMoney;
	}
	public void setVouMoney(Double vouMoney) {
		this.vouMoney = vouMoney;
	}
}
