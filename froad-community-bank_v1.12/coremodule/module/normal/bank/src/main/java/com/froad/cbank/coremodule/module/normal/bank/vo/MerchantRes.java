package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户response
 * 
 * @author ylchu
 *
 */
public class MerchantRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2689082484087242319L;
	private String merchantId; // 商户id
	private Long merchantUserId;// 商户用户Id
	private String merchantName; // 商户简称
	private Long contractBegintime; // 签约时间
	private Long contractEndtime; // 到期时间
	private String contractStaff; // 签约人员
	private StringBuilder category; // 商户分类
	private StringBuilder type; // 商户类型
	private String auditStage; // 审核阶段
	private String auditState; // 审核状态
	private String editAuditState; // 编辑审核状态
	private String auditStaff; // 审核人
	private Long auditTime; // 审核时间
	private Boolean isEnable; // 状态
	private String enable; // 签约状态
	private String phone;
	private String license; // 营业执照
	private String merchantUserName;
	private Long createTime;
	private String orgName; // 银行所属网点名称
	private String orgCode;
	private String userOrgCode; // 当前机构号
	private String disableStatus; // 是否签约解约
	private String companyCredential;// 商户组织机构代码

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
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

	public Long getContractBegintime() {
		return contractBegintime;
	}

	public void setContractBegintime(Long contractBegintime) {
		this.contractBegintime = contractBegintime;
	}

	public Long getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(Long contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	public StringBuilder getCategory() {
		if (category == null) {
			category = new StringBuilder();
		}
		return category;
	}

	public void setCategory(StringBuilder category) {
		this.category = category;
	}

	public StringBuilder getType() {
		if (type == null) {
			type = new StringBuilder();
		}
		return type;
	}

	public void setType(StringBuilder type) {
		this.type = type;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public Long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Long getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public String getUserOrgCode() {
		return userOrgCode;
	}

	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}

	public String getCompanyCredential() {
		return companyCredential;
	}

	public void setCompanyCredential(String companyCredential) {
		this.companyCredential = companyCredential;
	}

	public String getContractStaff() {
		return contractStaff;
	}

	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}
	
}
