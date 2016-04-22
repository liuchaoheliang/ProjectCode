package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Merchant_Info_Res {
	
	private Long id; 
	private Long createTime; 
	private Long clientId; 
	private String merchantId; 
	private String cityOrgCode; 
	private String countyOrgCode; 
	private String orgCode; 
	private String merchantName; 
	private String merchantFullname; 
	private String logo; 
	private String address; 
	private String phone; 
	private Long areaId; 
	private Boolean merchantStatus; 
	private Boolean isEnable; 
	private Boolean isTop; 
	private String introduce; 
	private String license; 
	private String taxReg; 
	private Long contractBegintime; 
	private Long contractEndtime; 
	private String contractStaff; 
	private Integer auditState; 
	private String auditOrgCode; 
	private Boolean needReview; 
	private Boolean auditStage; 
	private Long auditTime; 
	private String auditComment; 
	private String auditStaff; 
	private String reviewStaff; 
	private String contactName; 
	private String contactPhone; 
	private String contactEmail; 
	private String legalName; 
	private String legalCredentType; 
	private String legalCredentNo; 
	private Long monthMoney; 
	private Long groupOrderCount; 
	private Long sellOrderCount; 
	private String merchantUserName; 

	private String orgName;

    private String area;
	private String category;
	private String merchantType;
	private String zipCode;
	private String complaintsHotline;
	private String fax;
	private String accountOrgNo;
	private String forAccount;
	
	/**
	   * 商户组织机构代码证
	   */
	  public String companyCredential; // optional
	
	/**
	 * 投诉电话
	 */
	private String complaintPhone;
	
	

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCityOrgCode() {
		return cityOrgCode;
	}

	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}

	public String getCountyOrgCode() {
		return countyOrgCode;
	}

	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}

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

	public String getMerchantFullname() {
		return merchantFullname;
	}

	public void setMerchantFullname(String merchantFullname) {
		this.merchantFullname = merchantFullname;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Boolean getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(Boolean merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getTaxReg() {
		return taxReg;
	}

	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
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

	public String getContractStaff() {
		return contractStaff;
	}

	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	public Boolean getNeedReview() {
		return needReview;
	}

	public void setNeedReview(Boolean needReview) {
		this.needReview = needReview;
	}

	public Boolean getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(Boolean auditStage) {
		this.auditStage = auditStage;
	}

	public Long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public String getReviewStaff() {
		return reviewStaff;
	}

	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}


	public String getLegalCredentType() {
		return legalCredentType;
	}

	public void setLegalCredentType(String legalCredentType) {
		this.legalCredentType = legalCredentType;
	}

	public String getLegalCredentNo() {
		return legalCredentNo;
	}

	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}

	public Long getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(Long monthMoney) {
		this.monthMoney = monthMoney;
	}

	public Long getGroupOrderCount() {
		return groupOrderCount;
	}

	public void setGroupOrderCount(Long groupOrderCount) {
		this.groupOrderCount = groupOrderCount;
	}

	public Long getSellOrderCount() {
		return sellOrderCount;
	}

	public void setSellOrderCount(Long sellOrderCount) {
		this.sellOrderCount = sellOrderCount;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getComplaintsHotline() {
		return complaintsHotline;
	}

	public void setComplaintsHotline(String complaintsHotline) {
		this.complaintsHotline = complaintsHotline;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAccountOrgNo() {
		return accountOrgNo;
	}

	public void setAccountOrgNo(String accountOrgNo) {
		this.accountOrgNo = accountOrgNo;
	}

	public String getForAccount() {
		return forAccount;
	}

	public void setForAccount(String forAccount) {
		this.forAccount = forAccount;
	}

	public String getCompanyCredential() {
		return companyCredential;
	}

	public void setCompanyCredential(String companyCredential) {
		this.companyCredential = companyCredential;
	}
	
	

}
