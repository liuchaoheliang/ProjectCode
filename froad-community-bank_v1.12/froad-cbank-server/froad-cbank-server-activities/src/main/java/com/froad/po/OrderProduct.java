package com.froad.po;

import java.io.Serializable;

/**
 * 订单商品
 */
public class OrderProduct implements Serializable, Comparable<OrderProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 商品id */
	private String productId;
	/** 商品名称 */
	private String productName;
    /** 活动id */
	private String activeId;
    /** 普通单价 */
	private Double generalPrice;
    /** 普通数量 */
    private Integer generalCount;
    /** vip单价 */
    private Double vipPrice;
    /** vip数量 */
    private Integer vipCount;
    /** 满赠活动id */
    private String activeIdGive;
    /** 分摊满减额 */
    private Double cutMonry;
    
    @Override
	public int compareTo(OrderProduct o) {
    	String productName = o.productName;
		return this.productName.compareTo(productName);
	}
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public Double getGeneralPrice() {
		return generalPrice;
	}
	public void setGeneralPrice(Double generalPrice) {
		this.generalPrice = generalPrice;
	}
	public Integer getGeneralCount() {
		return generalCount;
	}
	public void setGeneralCount(Integer generalCount) {
		this.generalCount = generalCount;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getActiveIdGive() {
		return activeIdGive;
	}
	public void setActiveIdGive(String activeIdGive) {
		this.activeIdGive = activeIdGive;
	}
	public Double getCutMonry() {
		return cutMonry;
	}
	public void setCutMonry(Double cutMonry) {
		this.cutMonry = cutMonry;
	}
    
}
