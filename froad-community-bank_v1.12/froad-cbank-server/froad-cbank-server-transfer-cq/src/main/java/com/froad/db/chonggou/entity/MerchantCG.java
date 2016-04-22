package com.froad.db.chonggou.entity;

import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Assert;
import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.Future;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;

/**
 * CbMerchant po.
 */
@Guarded
public class MerchantCG implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	private String disableStatus;
	
	private Boolean isTop;
	
	private String introduce;
	
	private String license;
	
	private String taxReg;
	
	private Date contractBegintime;
	private Date contractEndtime;
	
	private String auditStartOrgCode;
	
	private String auditEndOrgCode;
	
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
	
	private String complaintPhone;
	
	private Long monthMoney;
	private Long groupOrderCount;
	private Long sellOrderCount;
	private String userOrgCode;
	
	private String companyCredential;

	public String getCompanyCredential() {
		return companyCredential;
	}

	public void setCompanyCredential(String companyCredential) {
		this.companyCredential = companyCredential;
	}

	public String getUserOrgCode() {
		return userOrgCode;
	}

	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}

	private List<String> orgCodeList;

	private List<Long> categoryInfoList;

	private List<Long> typeInfoList;

	private List<String> merchantIdList;

	private Date startCreateTime;
	private Date endCreateTime;
	private Date startAuditTime;
	private Date endAuditTime;

	private Date startContractBegintime;
	private Date endContractBegintime;
	private Date startContractEndtime;
	private Date endContractEndtime;

	// Constructors

	/** default constructor */
	public MerchantCG() {
	}

	// Property accessors

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

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
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

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public List<Long> getCategoryInfoList() {
		return categoryInfoList;
	}

	public void setCategoryInfoList(List<Long> categoryInfoList) {
		this.categoryInfoList = categoryInfoList;
	}

	public List<Long> getTypeInfoList() {
		return typeInfoList;
	}

	public void setTypeInfoList(List<Long> typeInfoList) {
		this.typeInfoList = typeInfoList;
	}

	public List<String> getMerchantIdList() {
		return merchantIdList;
	}

	public void setMerchantIdList(List<String> merchantIdList) {
		this.merchantIdList = merchantIdList;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Date getStartAuditTime() {
		return startAuditTime;
	}

	public void setStartAuditTime(Date startAuditTime) {
		this.startAuditTime = startAuditTime;
	}

	public Date getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Date endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}

	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}

	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}

	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}

	public Date getStartContractBegintime() {
		return startContractBegintime;
	}

	public void setStartContractBegintime(Date startContractBegintime) {
		this.startContractBegintime = startContractBegintime;
	}

	public Date getEndContractBegintime() {
		return endContractBegintime;
	}

	public void setEndContractBegintime(Date endContractBegintime) {
		this.endContractBegintime = endContractBegintime;
	}

	public Date getStartContractEndtime() {
		return startContractEndtime;
	}

	public void setStartContractEndtime(Date startContractEndtime) {
		this.startContractEndtime = startContractEndtime;
	}

	public Date getEndContractEndtime() {
		return endContractEndtime;
	}

	public void setEndContractEndtime(Date endContractEndtime) {
		this.endContractEndtime = endContractEndtime;
	}

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

}