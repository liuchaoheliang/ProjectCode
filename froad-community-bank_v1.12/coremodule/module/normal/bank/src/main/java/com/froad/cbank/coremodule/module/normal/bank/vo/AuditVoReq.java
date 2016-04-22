package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class AuditVoReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String productId;
	private List<String> productIdList;
	private String merchantId;
	private String auditState;// 审核状态
	private String auditReason;// 审核备注
	private String auditStaff;// 审核人
	private String productType;// 商品类型
	private String loginPhone;
	private String loginName;
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<String> getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditReason() {
		return auditReason;
	}
	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}
	public String getAuditStaff() {
		return auditStaff;
	}
	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getLoginPhone() {
		return loginPhone;
	}
	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
