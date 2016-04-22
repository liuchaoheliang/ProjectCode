package com.froad.po;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.ActiveUtils;

public class ActiveMainReportDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// [{"product_id":"","money":"","vip_money":"","quantity":"","vip_quantity":"","product_name":""}]
	
	@JSONField(name = ActiveUtils.ACT_TYPE, serialize = true, deserialize = true)
	private String activeType;
	
	@JSONField(name = ActiveUtils.ACT_ID, serialize = true, deserialize = true)
	private String activeId;
	
	@JSONField(name = ActiveUtils.VOU_ID, serialize = true, deserialize = true)
	private String vouchersId;
	
	@JSONField(name = ActiveUtils.PRO_ID, serialize = true, deserialize = true)
	private String productId;

	@JSONField(name = ActiveUtils.GEN_DIS_MONEY, serialize = true, deserialize = true)
	private Double genDisMoney;

	@JSONField(name = ActiveUtils.VIP_DIS_MONEY, serialize = true, deserialize = true)
	private Double vipDisMoney;

	@JSONField(name = ActiveUtils.GEN_DIS_COUNT, serialize = true, deserialize = true)
	private Integer genDisCount;

	@JSONField(name = ActiveUtils.VIP_DIS_COUNT, serialize = true, deserialize = true)
	private Integer vipDisCount;

	@JSONField(name = ActiveUtils.PRO_NAME, serialize = true, deserialize = true)
	private String productName;

	@JSONField(name = ActiveUtils.GEN_PRICE, serialize = true, deserialize = true)
	private Double genPrice;

	@JSONField(name = ActiveUtils.VIP_PRICE, serialize = true, deserialize = true)
	private Double vipPrice;

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

	public Double getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	public Double getGenDisMoney() {
		return genDisMoney;
	}

	public void setGenDisMoney(Double genDisMoney) {
		this.genDisMoney = genDisMoney;
	}

	public Double getVipDisMoney() {
		return vipDisMoney;
	}

	public void setVipDisMoney(Double vipDisMoney) {
		this.vipDisMoney = vipDisMoney;
	}

	public Integer getGenDisCount() {
		return genDisCount;
	}

	public void setGenDisCount(Integer genDisCount) {
		this.genDisCount = genDisCount;
	}

	public Integer getVipDisCount() {
		return vipDisCount;
	}

	public void setVipDisCount(Integer vipDisCount) {
		this.vipDisCount = vipDisCount;
	}

	public Double getGenPrice() {
		return genPrice;
	}

	public void setGenPrice(Double genPrice) {
		this.genPrice = genPrice;
	}

	
}
