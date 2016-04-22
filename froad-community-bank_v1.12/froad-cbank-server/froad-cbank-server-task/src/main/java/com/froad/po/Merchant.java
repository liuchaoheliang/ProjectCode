package com.froad.po;

import java.util.Date;

import com.froad.enums.MerchantDisableStatusEnum;

/**
 * CbMerchant po. 
 */


public class Merchant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private Date createTime;
	private String clientId;
	private String merchantId;
	private String proOrgCode;
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
	private Date contractBegintime;
	private Date contractEndtime;
	private String contractStaff;
	private String auditState;
	private String auditOrgCode;
	private String auditStage;
	private Date auditTime;
	private String auditComment;
	private String auditStaff;
	private String reviewStaff;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String legalName;
	private Integer legalCredentType;
	private String legalCredentNo;
	private String disableStatus;
	
	// Constructors

	/** default constructor */
	public Merchant() {
	}

	

	// Property accessors
	
	
	
	public Merchant(Long id, Date createTime, String clientId, String merchantId, String cityOrgCode, String countyOrgCode, String orgCode, String merchantName, String merchantFullname, String logo, String address, String phone, Long areaId, Boolean merchantStatus, Boolean isEnable, Boolean isTop, String introduce, String license, String taxReg, Date contractBegintime, Date contractEndtime, String contractStaff, String auditState, String auditOrgCode, Boolean needReview, String auditStage, Date auditTime, String auditComment, String auditStaff, String reviewStaff, String contactName, String contactPhone, String contactEmail, String legalName, Integer legalCredentType, String legalCredentNo) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.clientId = clientId;
		this.merchantId = merchantId;
		this.cityOrgCode = cityOrgCode;
		this.countyOrgCode = countyOrgCode;
		this.orgCode = orgCode;
		this.merchantName = merchantName;
		this.merchantFullname = merchantFullname;
		this.logo = logo;
		this.address = address;
		this.phone = phone;
		this.areaId = areaId;
		this.merchantStatus = merchantStatus;
		this.isEnable = isEnable;
		this.isTop = isTop;
		this.introduce = introduce;
		this.license = license;
		this.taxReg = taxReg;
		this.contractBegintime = contractBegintime;
		this.contractEndtime = contractEndtime;
		this.contractStaff = contractStaff;
		this.auditState = auditState;
		this.auditOrgCode = auditOrgCode;
		this.auditStage = auditStage;
		this.auditTime = auditTime;
		this.auditComment = auditComment;
		this.auditStaff = auditStaff;
		this.reviewStaff = reviewStaff;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.legalName = legalName;
		this.legalCredentType = legalCredentType;
		this.legalCredentNo = legalCredentNo;
	}




	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	
	public String getMerchantFullname() {
		return this.merchantFullname;
	}

	public void setMerchantFullname(String merchantFullname) {
		this.merchantFullname = merchantFullname;
	}

	
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	
	public Boolean getMerchantStatus() {
		return this.merchantStatus;
	}

	public void setMerchantStatus(Boolean merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	
	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	
	public Boolean getIsTop() {
		return this.isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	
	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	
	public String getTaxReg() {
		return this.taxReg;
	}

	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
	}

	
	public Date getContractBegintime() {
		return this.contractBegintime;
	}

	public void setContractBegintime(Date contractBegintime) {
		this.contractBegintime = contractBegintime;
	}

	
	public Date getContractEndtime() {
		return this.contractEndtime;
	}

	public void setContractEndtime(Date contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	
	public String getContractStaff() {
		return this.contractStaff;
	}

	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}

	
	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	
	public String getAuditOrgCode() {
		return this.auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	
	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	
	public String getAuditComment() {
		return this.auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	
	public String getAuditStaff() {
		return this.auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	
	public String getReviewStaff() {
		return this.reviewStaff;
	}

	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}

	
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	
	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	
	public Integer getLegalCredentType() {
		return this.legalCredentType;
	}

	public void setLegalCredentType(Integer legalCredentType) {
		this.legalCredentType = legalCredentType;
	}

	
	public String getLegalCredentNo() {
		return this.legalCredentNo;
	}

	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}



	public String getProOrgCode() {
		return proOrgCode;
	}



	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
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



	public String getDisableStatus() {
		return disableStatus;
	}



	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

}