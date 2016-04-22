package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 商户
 * 
 * @author ylchu
 *
 */
public class MerchantManageVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8874088556569119863L;

	private String merchantId; // 商户id
	private String merchantName; // 商户简称
	private String merchantUserId;
	private String merchantFullName; // 商户全称
	private String address; // 详细地址
	private String phone; // 联系人电话
	private String complaintPhone; // 投诉电话
	private String contactPhone; // 联系人手机
	private String contactName; // 联系人
	private String contactEmail; // 联系人邮箱
	private String legalName; // 法人名
	private Integer legalCredentType; // 证件类型
	private String legalCredentNo; // 法人证件号码
	private String license; // 营业执照
	private String orgCode; // 组织机构代码
	private String myOrgCode; // 当前机构号
	private String orgName;// 所属行社
	private String partenOrgName;// 所属网点
	private String taxReg; // 税务登记证
	private String openingBank; // 开户行机构号
	private String contractBegintime; // 签约时间
	private String contractEndtime; // 到期时间
	private String contractStaff; // 签约人员
	private List<Long> categoryType; // 商户类型id
	private Long category; // 商户分类id
	private List<TypeVo> typeList; // 商户类型
	private List<CategoryVo> categoryList;// 商户分类
	private String logo; // 商户图片
	private String introduce; // 商户介绍
	private String acctName; // 打款账户名
	private String acctNumber; // 打款账户号
	private String loginName; // 登录名
	private String loginPhone; // 登录人手机
	private String loginPassword; // 登陆密码
	private Boolean isEnable; // 是否解约
	private Long merchantRoleId; // 管理员角色
	private Long areaId;
	private String areaCode;// 省
	private String cityCode;// 市
	private String countyCode;// 区
	private String auditComment;
	private String auditState;
	private String clientId;
	private String companyCredential;// 商户组织机构代码
	private String disableStatus; // 0正常;1禁用;2解约
	private String operator; // 操作人
	private Long operateTime; // 禁用时间 (解约时间)
	private String auditId; // 关联审核流水号
	private String processId; // 审核流程id
	/** 0-新增；1-更新 */
	private String auditType; // 审核类型
	/**
	 * 增加外包需求
	 * 
	 * @return
	 */
	private Boolean isOutsource;// 是否外包
	private Long companyId;// 外包公司id
	private String companyName;// 外包公司id

	public Boolean getIsOutsource() {
		return isOutsource;
	}

	public void setIsOutsource(Boolean isOutsource) {
		this.isOutsource = isOutsource;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public List<TypeVo> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeVo> typeList) {
		this.typeList = typeList;
	}

	public List<CategoryVo> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryVo> categoryList) {
		this.categoryList = categoryList;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public Integer getLegalCredentType() {
		return legalCredentType;
	}

	public void setLegalCredentType(Integer legalCredentType) {
		this.legalCredentType = legalCredentType;
	}

	public String getLegalCredentNo() {
		return legalCredentNo;
	}

	public void setLegalCredentNo(String legalCredentNo) {
		this.legalCredentNo = legalCredentNo;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMyOrgCode() {
		return myOrgCode;
	}

	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}

	public String getTaxReg() {
		return taxReg;
	}

	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getContractBegintime() {
		return contractBegintime;
	}

	public void setContractBegintime(String contractBegintime) {
		this.contractBegintime = contractBegintime;
	}

	public String getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(String contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	public String getContractStaff() {
		return contractStaff;
	}

	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
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

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getMerchantRoleId() {
		return merchantRoleId;
	}

	public void setMerchantRoleId(Long merchantRoleId) {
		this.merchantRoleId = merchantRoleId;
	}

	public List<Long> getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(List<Long> categoryType) {
		this.categoryType = categoryType;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

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

	public String getPartenOrgName() {
		return partenOrgName;
	}

	public void setPartenOrgName(String partenOrgName) {
		this.partenOrgName = partenOrgName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getCompanyCredential() {
		return companyCredential;
	}

	public void setCompanyCredential(String companyCredential) {
		this.companyCredential = companyCredential;
	}

}
