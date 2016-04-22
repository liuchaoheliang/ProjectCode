package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 预售商品信息列表显示数据
 * @author Administrator
 *
 */
public class PresaleProductVoRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String productId;       //商品ID
	private String productName;     //商品名称
	private String orgCode;   		//所属机构
	private String orgName;         //机构名称
	private String auditState;      //审核状态
	private String isMarketable;    //上下架状态
	private String auditStateName;      //审核状态
	private Long auditTime;      //审核时间
	private String isMarketableName;     //上下架状态
	
	private String takeStartDate;   //提货开始时间
	private String takeEndDate;     //提货结束时间
	private String marketPrice;     //市场价
	private String salePrice;       //预售价
	private String startDate;//开始日期
	private String endDate;	//结束日期
	
	private String store;// 库存数量

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
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
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getAuditStateName() {
		return auditStateName;
	}
	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}
	public String getIsMarketableName() {
		return isMarketableName;
	}
	public void setIsMarketableName(String isMarketableName) {
		this.isMarketableName = isMarketableName;
	}
	public String getTakeStartDate() {
		return takeStartDate;
	}
	public void setTakeStartDate(String takeStartDate) {
		this.takeStartDate = takeStartDate;
	}
	public String getTakeEndDate() {
		return takeEndDate;
	}
	public void setTakeEndDate(String takeEndDate) {
		this.takeEndDate = takeEndDate;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	
}
