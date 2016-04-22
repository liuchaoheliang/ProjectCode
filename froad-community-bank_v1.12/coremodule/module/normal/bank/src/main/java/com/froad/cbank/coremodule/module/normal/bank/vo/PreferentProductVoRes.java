package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 名优特惠请求参数
 * 
 * @author Administrator
 *
 */
public class PreferentProductVoRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String productId; // 商品ID
	private String productName; // 商品名称
	private String merchantName; // 商户名称
	private String orgName; // 机构名称
	private String orgCode; // 录入人机构号
	private String marketPrice; // 原价
	private String salePrice; // 特惠价
	private Integer storeNum; // 当前库存
	private String auditState; // 审核状态
	private Long auditTime; // 审核时间
	private String isMarketable; // 上下架状态
	private String auditStateName; // 审核状态
	private String auditStaff; // 审核人
	private String isMarketableName; // 上下架状态
	private String startDate;// 开始日期
	private String endDate; // 结束日期
	
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public Integer getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
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

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}
	
}
