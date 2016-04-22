package com.froad.cbank.coremodule.normal.boss.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("order")
public class OrderEntity {
	
	private long id;
	
	/**
	 * 客户端
	 */
	private String clientId;
	
	/**
	 * 业务类型(团购，预售)
	 */
	private Integer type;
	
	/**
	 * 机构号
	 */
	private String orgCode;
	
	/**
	 * 订单数
	 */
	private Integer orderCount;
	
	/**
	 * 订单总金额 
	 */
	private String orderTotalPrice;
	
	/**
	 * 商品总数
	 */
	private Integer productCount;
	
	/**
	 * 商品总金额
	 */
	private String productTotalPrice;
	
	/**
	 * 时间
	 */
	private Date createTime;
	
	/**
	 * 购买会员数
	 */
	private int buyCount;
	
	/**
	 * 业务类型数
	 */
	private int typeCount;
	
	/**
	 * 机构数
	 */
	private int orgCount;
	
	/**
	 * 二级机构数量
	 */
	private int cityOrgCount;
	
	/**
	 * 三级机构数量
	 */
	private int countyOrgCount;
	
	/**
	 * 分类各类数
	 */
	private int categoryCount;
	
	/**
	 * 门店数
	 */
	private int outletCount;
	
	/**
	 * 商户数
	 */
	private int merchantCount;
	
	/**
	 * 支付方式数
	 */
	private int payTypeCount;
	
	
	public int getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}

	public int getOutletCount() {
		return outletCount;
	}

	public void setOutletCount(int outletCount) {
		this.outletCount = outletCount;
	}

	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	public int getPayTypeCount() {
		return payTypeCount;
	}

	public void setPayTypeCount(int payTypeCount) {
		this.payTypeCount = payTypeCount;
	}

	public int getCityOrgCount() {
		return cityOrgCount;
	}

	public void setCityOrgCount(int cityOrgCount) {
		this.cityOrgCount = cityOrgCount;
	}

	public int getCountyOrgCount() {
		return countyOrgCount;
	}

	public void setCountyOrgCount(int countyOrgCount) {
		this.countyOrgCount = countyOrgCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(String productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}

	public int getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(int orgCount) {
		this.orgCount = orgCount;
	}
	
	
}
