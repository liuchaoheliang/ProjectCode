package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户
 * 
 * @author ylchu
 *
 */
public class MerchantReq extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7377747730920349445L;

	private Integer auditState; // 审核状态
	private String orgCode; // 所属机构
	private String auditOrgCode; // 所属机构
	private String merchantName;// 商户名称
	private String userName; // 用户名
	private String merchantType;// 所属分类
	private String category;
	private Boolean isEnable;
	private String userId;
	private String token;
	private String license; //营业执照
	private String auditStartTime; //审核开始时间
	private String auditEndTime;  //审核结束时间
	private String signStatus;// 签约状态(0:全部,1:签约,2:禁用,3:解约)
	private String editAuditStatus;//编辑审核状态
	private String myOrgCode;//当前登录人所属机构

	
	

	public String getMyOrgCode() {
		return myOrgCode;
	}

	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}

	public String getEditAuditStatus() {
		return editAuditStatus;
	}

	public void setEditAuditStatus(String editAuditStatus) {
		this.editAuditStatus = editAuditStatus;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
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

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

}
