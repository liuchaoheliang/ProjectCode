package com.froad.po;

import java.io.Serializable;

public class ReturnSubOrderProductBackReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 商品id */
	private String productId;
    /** vip数量 */
	private Integer vipCount;
    /**Vip回退总金额*/
	private Double vipPrice;
    /** 普通数量 */
	private Integer normalCount;
    /**普通回退总金额*/
	private Double normalPrice;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getVipCount() {
		return vipCount;
	}
	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}
	public Double getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(Double normalPrice) {
		this.normalPrice = normalPrice;
	}
}
