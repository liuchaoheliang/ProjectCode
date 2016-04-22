package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BussinessStatisticResVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8872094083063740162L;
	
	/**机构号*/
	private String orgCode;
	/**机构名称*/
	private String orgName;
	/**订单数*/
	private Integer orderCount;
	/**订单总金额*/
	private Double totalAmount;
	/**商品销售数量*/
	private Integer productSaleCount;
	/**商品销售金额*/
	private Double productSaleAmount;
	/**购买人次*/
	private Integer buyCount;
	/**平均消费金额*/
	private Double averAmount;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getProductSaleCount() {
		return productSaleCount;
	}
	public void setProductSaleCount(Integer productSaleCount) {
		this.productSaleCount = productSaleCount;
	}
	public Double getProductSaleAmount() {
		return productSaleAmount;
	}
	public void setProductSaleAmount(Double productSaleAmount) {
		this.productSaleAmount = productSaleAmount;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public Double getAverAmount() {
		return averAmount;
	}
	public void setAverAmount(Double averAmount) {
		this.averAmount = averAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
