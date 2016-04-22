package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankOutletListReqVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2934222409980146005L;

	private String orgCode;// 所属机构
	private String merchantName;// 商户名称
	private String outletName;// 门店名称
	private String auditStartTime;// 审核开始时间
	private String auditEndTime;// 审核结束时间
	private String license;// 营业执照
	private String auditState;// 审核状态 5=全部,0=待审核,1=审核通,2=审核不通,3=未提交 ,4=删除

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getAuditStartTime() {
		return auditStartTime;
	}

	public void setAuditStartTime(String auditStartTime) {
		this.auditStartTime = auditStartTime;
	}

	public String getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

}
