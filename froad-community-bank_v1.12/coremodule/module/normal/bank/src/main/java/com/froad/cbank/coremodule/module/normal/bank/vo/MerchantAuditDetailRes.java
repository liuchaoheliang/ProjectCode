package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 待审核商户详情返回实体 ClassName: MerchantAuditDetailRes Function: TODO ADD FUNCTION
 * date: 2015-8-14 上午11:33:23
 *
 * @author wufei
 * @version
 */
public class MerchantAuditDetailRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8368950550435252972L;
	private String orderNumber; // 订单流水号
	private String oldOrgNames; // 原所属机构
	private String newOrgNames; // 新所属机构
	private String oldParentOrgNames; // 原所属机构上级机构
	private String newParentOrgNames; // 新所属机构上级机构
	private String businessType; // 业务类型
	private String pigeonholeTime; // 归档时间
	private String auditStatus; // 审核状态
	private Long createTime; // 创建时间
	private String merchantName; // 商户简称
	private String license; // 营业执照
	private String creater; // 创建人
	private String merchantFullName; // 商户全称
	private String oldPhone; // 原联系人电话
	private String newPhone; // 新联系人电话
	private String oldContactPhone;// 原联系人手机
	private String newContactPhone;// 新联系人手机
	private String oldContactName; // 原联系人
	private String newContactName; // 新联系人
	private String oldCategory; // 原商户分类
	private String newCategory; // 新商户分类
	private String oldLegalName; // 原法人名
	private String newLegalName; // 新法人名
	private String oldLegalCredentType; // 原证件类型
	private String newLegalCredentType; // 新证件类型
	private String oldLegalCredentNo;// 原证件号码
	private String newLegalCredentNo;// 新证件号码
	private String oldCategoryType; // 原商户类型
	private String newCategoryType; // 新商户类型
	private String oldAcctName; // 原收款账户名
	private String newAcctName; // 新收款账户名
	private String oldAcctNumber; // 原收款账户号
	private String newAcctNumber; // 新收款账户号
	private String createOrgName; // 创建机构
	private String oldLoginPhone; // 原登录人手机
	private String newLoginPhone; // 新登录人手机
	private String newMerchantName; // 新商户简称
	private String oldMerchantName; // 原商户简称
	private String newContractStaff; // 新签约人
	private String oldContractStaff; // 原签约人
	private Boolean isOutsource;// 是否外包
	private Boolean oldIsOutsource;// 是否外包(原值)
	private Long companyId;// 外包公司id
	private Long oldCompanyId;// 外包公司id(原值)
	private String companyName;// 公司名称
	private String oldCompanyName;// 公司名称(原值)

	public String getNewContractStaff() {
		return newContractStaff;
	}

	public void setNewContractStaff(String newContractStaff) {
		this.newContractStaff = newContractStaff;
	}

	public String getOldContractStaff() {
		return oldContractStaff;
	}

	public void setOldContractStaff(String oldContractStaff) {
		this.oldContractStaff = oldContractStaff;
	}

	public String getNewMerchantName() {
		return newMerchantName;
	}

	public void setNewMerchantName(String newMerchantName) {
		this.newMerchantName = newMerchantName;
	}

	public String getOldMerchantName() {
		return oldMerchantName;
	}

	public void setOldMerchantName(String oldMerchantName) {
		this.oldMerchantName = oldMerchantName;
	}

	public String getOldPhone() {
		return oldPhone;
	}

	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

	public String getOldContactPhone() {
		return oldContactPhone;
	}

	public void setOldContactPhone(String oldContactPhone) {
		this.oldContactPhone = oldContactPhone;
	}

	public String getNewContactPhone() {
		return newContactPhone;
	}

	public void setNewContactPhone(String newContactPhone) {
		this.newContactPhone = newContactPhone;
	}

	public String getOldCategory() {
		return oldCategory;
	}

	public void setOldCategory(String oldCategory) {
		this.oldCategory = oldCategory;
	}

	public String getOldCategoryType() {
		return oldCategoryType;
	}

	public void setOldCategoryType(String oldCategoryType) {
		this.oldCategoryType = oldCategoryType;
	}

	public String getNewCategory() {
		return newCategory;
	}

	public void setNewCategory(String newCategory) {
		this.newCategory = newCategory;
	}

	public String getNewCategoryType() {
		return newCategoryType;
	}

	public void setNewCategoryType(String newCategoryType) {
		this.newCategoryType = newCategoryType;
	}

	public String getOldParentOrgNames() {
		return oldParentOrgNames;
	}

	public void setOldParentOrgNames(String oldParentOrgNames) {
		this.oldParentOrgNames = oldParentOrgNames;
	}

	public String getNewParentOrgNames() {
		return newParentOrgNames;
	}

	public void setNewParentOrgNames(String newParentOrgNames) {
		this.newParentOrgNames = newParentOrgNames;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOldOrgNames() {
		return oldOrgNames;
	}

	public void setOldOrgNames(String oldOrgNames) {
		this.oldOrgNames = oldOrgNames;
	}

	public String getNewOrgNames() {
		return newOrgNames;
	}

	public void setNewOrgNames(String newOrgNames) {
		this.newOrgNames = newOrgNames;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getPigeonholeTime() {
		return pigeonholeTime;
	}

	public void setPigeonholeTime(String pigeonholeTime) {
		this.pigeonholeTime = pigeonholeTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getMerchantFullName() {
		return merchantFullName;
	}

	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}

	public String getOldContactName() {
		return oldContactName;
	}

	public void setOldContactName(String oldContactName) {
		this.oldContactName = oldContactName;
	}

	public String getNewContactName() {
		return newContactName;
	}

	public void setNewContactName(String newContactName) {
		this.newContactName = newContactName;
	}

	public String getOldLegalName() {
		return oldLegalName;
	}

	public void setOldLegalName(String oldLegalName) {
		this.oldLegalName = oldLegalName;
	}

	public String getNewLegalName() {
		return newLegalName;
	}

	public void setNewLegalName(String newLegalName) {
		this.newLegalName = newLegalName;
	}

	public String getOldLegalCredentType() {
		return oldLegalCredentType;
	}

	public void setOldLegalCredentType(String oldLegalCredentType) {
		this.oldLegalCredentType = oldLegalCredentType;
	}

	public String getNewLegalCredentType() {
		return newLegalCredentType;
	}

	public void setNewLegalCredentType(String newLegalCredentType) {
		this.newLegalCredentType = newLegalCredentType;
	}

	public String getOldLegalCredentNo() {
		return oldLegalCredentNo;
	}

	public void setOldLegalCredentNo(String oldLegalCredentNo) {
		this.oldLegalCredentNo = oldLegalCredentNo;
	}

	public String getNewLegalCredentNo() {
		return newLegalCredentNo;
	}

	public void setNewLegalCredentNo(String newLegalCredentNo) {
		this.newLegalCredentNo = newLegalCredentNo;
	}

	public String getOldAcctName() {
		return oldAcctName;
	}

	public void setOldAcctName(String oldAcctName) {
		this.oldAcctName = oldAcctName;
	}

	public String getNewAcctName() {
		return newAcctName;
	}

	public void setNewAcctName(String newAcctName) {
		this.newAcctName = newAcctName;
	}

	public String getOldAcctNumber() {
		return oldAcctNumber;
	}

	public void setOldAcctNumber(String oldAcctNumber) {
		this.oldAcctNumber = oldAcctNumber;
	}

	public String getNewAcctNumber() {
		return newAcctNumber;
	}

	public void setNewAcctNumber(String newAcctNumber) {
		this.newAcctNumber = newAcctNumber;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getOldLoginPhone() {
		return oldLoginPhone;
	}

	public void setOldLoginPhone(String oldLoginPhone) {
		this.oldLoginPhone = oldLoginPhone;
	}

	public String getNewLoginPhone() {
		return newLoginPhone;
	}

	public void setNewLoginPhone(String newLoginPhone) {
		this.newLoginPhone = newLoginPhone;
	}

	public Boolean getIsOutsource() {
		return isOutsource;
	}

	public void setIsOutsource(Boolean isOutsource) {
		this.isOutsource = isOutsource;
	}

	public Boolean getOldIsOutsource() {
		return oldIsOutsource;
	}

	public void setOldIsOutsource(Boolean oldIsOutsource) {
		this.oldIsOutsource = oldIsOutsource;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getOldCompanyId() {
		return oldCompanyId;
	}

	public void setOldCompanyId(Long oldCompanyId) {
		this.oldCompanyId = oldCompanyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOldCompanyName() {
		return oldCompanyName;
	}

	public void setOldCompanyName(String oldCompanyName) {
		this.oldCompanyName = oldCompanyName;
	}

}
