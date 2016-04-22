package com.froad.po;
/**
 * 业务销售统计详情列表
 * @ClassName: SaleCountDetail 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月2日 上午10:28:13
 */
public class SaleCountDetail {
	
	private String orgCode;
	private String orgName;
	private Integer orderCount;
	private Double totalAmount;
	private Integer productSaleCount;
	private Double productSaleAmount;
	private Integer buyCount;
	private Double averAmount;
	private String forgCode;		//一级机构号
	private String forgName;		//一级机构名
	private String sorgCode;		//二级机构号
	private String sorgName;		//二级机构名
	private String torgCode;		//三级机构号
	private String torgName;		//三级机构名
	private String lorgCode;		//四级机构号
	private String lorgName;		//四级机构名
	
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
	public String getForgCode() {
		return forgCode;
	}
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	public String getForgName() {
		return forgName;
	}
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	public String getSorgCode() {
		return sorgCode;
	}
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	public String getSorgName() {
		return sorgName;
	}
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	public String getTorgCode() {
		return torgCode;
	}
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	public String getTorgName() {
		return torgName;
	}
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	public String getLorgCode() {
		return lorgCode;
	}
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	public String getLorgName() {
		return lorgName;
	}
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	
	
}
