package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

public class ActivityProductVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;// 商品id
	private String name;// 商品名称
	private String rackTime;// 上架时间
	private Double price;// 售价
	private Double vip1Price;// vip1价格
	private String createTime;// 创建时间
	private String isMarketable;// 是否上架

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRackTime() {
		return rackTime;
	}

	public void setRackTime(String rackTime) {
		this.rackTime = rackTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getVip1Price() {
		return vip1Price;
	}

	public void setVip1Price(Double vip1Price) {
		this.vip1Price = vip1Price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

}
