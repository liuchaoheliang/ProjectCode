package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankAuditProductListResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4833775191806023786L;
	private String productId; // 商品主键
	private String merchantName;// 商户名称
	private String auditId;// 审核流水号
	private String taskId;// 任务id
	private String productName;// 商品名称
	private String category;// 商户类型
	private String orgCode;// 所属机构
	private String productCategory;// 商品所属机构
	private String auditType;// 审核类型
	private Long createTime;// 创建时间
	private Long startDate;// 团购开始时间
	private Long endDate;// 团购结束时间


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

}
