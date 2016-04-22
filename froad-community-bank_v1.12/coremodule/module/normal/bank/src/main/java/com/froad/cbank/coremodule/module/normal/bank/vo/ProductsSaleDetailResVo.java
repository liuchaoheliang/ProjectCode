package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class ProductsSaleDetailResVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -865841613574854517L;
	/**机构号*/
	private String orgCode;
	/**机构名称*/
	private String orgName;
	/**新增商品数*/
	private Integer addProductCount;
	/**商品总数*/
	private Integer productCount;
	/**商品销售数量*/
	private Integer productSaleCount;
	/**商品销售金额*/
	private Double productSaleAmount;
	/**退款总金额*/
	private Double refundAmount;
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
	public Integer getAddProductCount() {
		return addProductCount;
	}
	public void setAddProductCount(Integer addProductCount) {
		this.addProductCount = addProductCount;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
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
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
}
