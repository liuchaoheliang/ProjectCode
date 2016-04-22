package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 我的理财产品详细信息</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-16上午11:25:08</p>
 * <p>作者: 吴菲</p>
 */
public class BaseProductPojo extends MyProductBaseInfoPojo{

	/** 理财产品Id*/
	private String poductId;
	/** 产品发售行机构名称*/
	private String productReleaseBankName;
	/** 收益类型*/
	private String incomeType;
	/** 银行产品编号*/
	private String bankProductCode;
	/** 产品期限,单位：天*/
	private int deadline;
	/** 风险等级*/
	private String riskLevel;
	/** 风险描述*/
	private String riskDesc;
	/** 客户权益须知*/
	private String customNoticeUrl;
	/** 风险揭示书*/
	private String riskBookUrl;
	/** 理财产品说明书*/
	private String productManualUrl;
	/** 产品状态 0-未上架 1-已上架 2-已下架 3-已删除 4-募集中 5-成立 6-不成立 7-已赎回*/
	private String productStatus;
	
	
	public String getProductReleaseBankName() {
		return productReleaseBankName;
	}
	public void setProductReleaseBankName(String productReleaseBankName) {
		this.productReleaseBankName = productReleaseBankName;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRiskDesc() {
		return riskDesc;
	}
	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}
	public String getCustomNoticeUrl() {
		return customNoticeUrl;
	}
	public void setCustomNoticeUrl(String customNoticeUrl) {
		this.customNoticeUrl = customNoticeUrl;
	}
	public String getRiskBookUrl() {
		return riskBookUrl;
	}
	public void setRiskBookUrl(String riskBookUrl) {
		this.riskBookUrl = riskBookUrl;
	}
	public String getProductManualUrl() {
		return productManualUrl;
	}
	public void setProductManualUrl(String productManualUrl) {
		this.productManualUrl = productManualUrl;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getPoductId() {
		return poductId;
	}
	public void setPoductId(String poductId) {
		this.poductId = poductId;
	}
	public String getBankProductCode() {
		return bankProductCode;
	}
	public void setBankProductCode(String bankProductCode) {
		this.bankProductCode = bankProductCode;
	}
    
	
}
