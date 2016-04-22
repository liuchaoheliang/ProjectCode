package com.froad.po;

import java.io.Serializable;

/**
 * 订单响应
 */
public class OrderRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 商品id */
	private String productId;
    /** 活动id */
	private String activeId;
    /** 活动名称 */
	private String activeName;
    /** 活动类型 */
	private String activeType;
    /** 满减金额 */
	private Double cutMoney;
    /** 普通单个满减金额 */
	private Double generalSingleCutMoney;
    /** 普通最后满减金额 */
	private Double generalAtLastCutMoney;
    /** VIP单个满减金额 */
	private Double vipSingleCutMoney;
    /** VIP最后满减金额 */
	private Double vipAtLastCutMoney;
	
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
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public Double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}
	public Double getGeneralSingleCutMoney() {
		return generalSingleCutMoney;
	}
	public void setGeneralSingleCutMoney(Double generalSingleCutMoney) {
		this.generalSingleCutMoney = generalSingleCutMoney;
	}
	public Double getGeneralAtLastCutMoney() {
		return generalAtLastCutMoney;
	}
	public void setGeneralAtLastCutMoney(Double generalAtLastCutMoney) {
		this.generalAtLastCutMoney = generalAtLastCutMoney;
	}
	public Double getVipSingleCutMoney() {
		return vipSingleCutMoney;
	}
	public void setVipSingleCutMoney(Double vipSingleCutMoney) {
		this.vipSingleCutMoney = vipSingleCutMoney;
	}
	public Double getVipAtLastCutMoney() {
		return vipAtLastCutMoney;
	}
	public void setVipAtLastCutMoney(Double vipAtLastCutMoney) {
		this.vipAtLastCutMoney = vipAtLastCutMoney;
	}
}
