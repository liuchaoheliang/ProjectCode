package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.io.Serializable;

/**
 * 类描述：限购商品信息实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-23下午1:25:16 
 */
public class LimitActivityProduct implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = -437695704354415782L;
	
	private String productId;//商品ID
	private String productName;//商品名称
	private String price;//商品价格
	private String isMarket;//是否上架
	private String createTime;//创建时间
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getIsMarket() {
		return isMarket;
	}
	public void setIsMarket(String isMarket) {
		this.isMarket = isMarket;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
