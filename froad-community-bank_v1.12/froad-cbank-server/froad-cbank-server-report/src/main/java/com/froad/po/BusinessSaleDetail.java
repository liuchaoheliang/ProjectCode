package com.froad.po;
/**
 * 业务类型销售统计详情列表
 * @ClassName: BusinessSaleDetail 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月3日 下午5:36:37
 */
public class BusinessSaleDetail {
	private String type;
	private String orgCode;
	private String orgName;
	private int orderCount;
	private double orderAmount;
	private double cashAmount;
	private double bankPointAmount;
	private double fftPointAmount;
	private int productCount;
	private double productAmount;
	private int buyCount;
	private String forgCode;		//一级机构号
	private String forgName;		//一级机构名
	private String sorgCode;		//二级机构号
	private String sorgName;		//二级机构名
	private String torgCode;		//三级机构号
	private String torgName;		//三级机构名
	private String lorgCode;		//四级机构号
	private String lorgName;		//四级机构名
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	public double getBankPointAmount() {
		return bankPointAmount;
	}
	public void setBankPointAmount(double bankPointAmount) {
		this.bankPointAmount = bankPointAmount;
	}
	public double getFftPointAmount() {
		return fftPointAmount;
	}
	public void setFftPointAmount(double fftPointAmount) {
		this.fftPointAmount = fftPointAmount;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
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
