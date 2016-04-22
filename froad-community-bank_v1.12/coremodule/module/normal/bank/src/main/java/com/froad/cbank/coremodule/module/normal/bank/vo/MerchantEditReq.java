package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商户编辑请求实体
 * ClassName: MerchantEditReq
 * Function: TODO ADD FUNCTION
 * date: 2015-8-14 下午02:15:33
 *
 * @author wufei
 * @version
 */
public class MerchantEditReq implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1201506187182214586L;
	
	private String merchantId; // 商户id
	private String merchantName; // 商户简称
	private String merchantFullName; // 商户全称
	private String areaId; //地址
	private String address; // 详细地址
	private String contactPhone; // 联系人电话
	private String complaintPhone; //投诉电话
	private String phone; // 手机
	private String contactEmail; // 联系人邮箱
	private String companyCredential; // 组织机构代码
	private String taxReg; // 税务登记证
	private String introduce; // 商户介绍
	private String contactName; // 联系人
	
	private String legalName; // 法人名
	private String legalCredentType; // 证件类型
	private String openingBank; // 开户行机构号
	private String legalCredentNo; // 证件号码
	private String acctName;    // 收款账户名
	private String acctNumber;      // 收款账户号
	private String confirmAcctNumber;  //确认收款账户号
	private String loginName; // 登录名
	private String loginPhone; // 登录人手机
	private String categoryType; // 商户类型
	private String category;   // 商户分类
	private String orgName;	//所属机构
	private String parentOrgName; //上级机构
	private String clientId;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
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
	public String getMerchantFullName() {
		return merchantFullName;
	}
	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getComplaintPhone() {
		return complaintPhone;
	}
	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getCompanyCredential() {
		return companyCredential;
	}
	public void setCompanyCredential(String companyCredential) {
		this.companyCredential = companyCredential;
	}
	public String getTaxReg() {
		return taxReg;
	}
	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getOpeningBank() {
		return openingBank;
	}
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	public String getLegalCredentNo() {
		return legalCredentNo;
	}
	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAcctNumber() {
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	public String getConfirmAcctNumber() {
		return confirmAcctNumber;
	}
	public void setConfirmAcctNumber(String confirmAcctNumber) {
		this.confirmAcctNumber = confirmAcctNumber;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPhone() {
		return loginPhone;
	}
	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getLegalCredentType() {
		return legalCredentType;
	}
	public void setLegalCredentType(String legalCredentType) {
		this.legalCredentType = legalCredentType;
	}
	
	
}
