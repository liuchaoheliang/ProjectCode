package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class LineProductVoRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String productId;       //礼品编号
	private String productName;     //礼品名称
	private String merchantId;     //礼品名称
	private String merchantName;     //礼品名称
	private String orgCode;         //录入人机构号
	private String orgName; 		// 机构名称
	private Integer storNum;        //库存
	private String createTime;     //创建时间
	private Double point;           //积分
	private String auditState;      //审核状态
	private Long auditTime;      //审核时间
	private String auditStateName;      //审核状态
	private String isMarketable;     //上下架状态
	private String isMarketableName;     //上下架状态
	private String distributionType;  //礼品类型
	private String startDate;//开始日期
	private String endDate;	//结束日期
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getStorNum() {
		return storNum;
	}
	public void setStorNum(Integer storNum) {
		this.storNum = storNum;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditStateName() {
		return auditStateName;
	}
	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getIsMarketableName() {
		return isMarketableName;
	}
	public void setIsMarketableName(String isMarketableName) {
		this.isMarketableName = isMarketableName;
	}
	public String getDistributionType() {
		return distributionType;
	}
	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
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
