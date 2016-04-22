package com.froad.po;

import java.io.Serializable;

public class MarketSubOrderProduct implements Serializable, Comparable<MarketSubOrderProduct> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** 活动id */
	private String activeId;
    /** 商品id */
	private String productId;
    /** 商品名称 */
	private String productName;
	/** 普通单价 */
	private Double generalPrice;
    /** 普通数量 */
    private Integer generalCount;
    /** vip单价 */
    private Double vipPrice;
    /** vip数量 */
    private Integer vipCount;
    /** 送现金券时对应的券id - 其它送优惠(商品,积分)直接根据active_id关联查询 */
	private String ticketId;
	/** 子订单id */
	private String subOrderId;
	/** 分摊的满减金额 */
	private Double cutMoney;
	/** 满赠活动id */
	private String activeIdGive;
	
	public Double getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}

	@Override
	public int compareTo(MarketSubOrderProduct o) {
    	String productName = o.productName;
		return this.productName.compareTo(productName);
	}
	
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
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
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getActiveIdGive() {
		return activeIdGive;
	}
	public void setActiveIdGive(String activeIdGive) {
		this.activeIdGive = activeIdGive;
	}
	
}
