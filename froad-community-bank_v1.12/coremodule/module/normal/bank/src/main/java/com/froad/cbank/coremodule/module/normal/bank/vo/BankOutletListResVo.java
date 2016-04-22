package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankOutletListResVo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 786920191296244450L;

	private String outletName;// 门店简称
	private String merchantName;// 商户名称
	private String license;// 营业执照
	private String areaName;// 所属地区
	private String address;// 门店地址
	private long createTime;// 创建时间
	private long auditTime;// 审核时间
	private String outletId;// 门店id
	private String auditState;// 原审核状态
	private String editAuditState;// 子状态
	private String orgName;// 所属机构
	private String qrcodeUrl;// 二维码地址
	private String disableStatus;// 是否有效
	private boolean isOpen;// 惠付功能 false:关闭true:开启
	private boolean discountFlag;// false:原无优惠折扣;true:原有优惠折扣
	
	
	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public boolean isDiscountFlag() {
		return discountFlag;
	}

	public void setDiscountFlag(boolean discountFlag) {
		this.discountFlag = discountFlag;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}
