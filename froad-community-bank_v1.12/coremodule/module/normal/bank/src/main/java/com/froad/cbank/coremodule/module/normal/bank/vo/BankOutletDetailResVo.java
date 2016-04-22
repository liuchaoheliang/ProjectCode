package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class BankOutletDetailResVo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -861934024185097454L;
	
	private String auditNumber;// 审核流水号
	private String outletId;// 门店编号
	private String auditStatus;// 审核状态
	private String creater;// 创建人
	private String createOrgName;// 创建机构
	private long createTime;// 创建时间
	private String outletName;// 门店名称
	private String outletDescription;// 门店描述
	private String oldOutletName;// 门店名称(原值)
	private String merchantName;// 商户全称
	private String outletFullName;// 门店全称
	private String oldOutletFullName;// 门店全称(原值)
	private String tellphone;// 联系电话
	private String oldTellphone;// 联系电话(原值)
	private String sellTime;// 营业时间
	private String address;// 地址
	private String oldAddress;// 地址(原值)
	private String contactName;// 联系人名称
	private String oldContactName;// 联系人名称(原值)
	private String contactPhone;// 联系人电话
	private String oldContactPhone;// 联系人电话(原值)
	private String discount;// 折扣
	private String discountCode;// 优惠折扣码
	private String oldDiscountCode;// 优惠折扣码(原值)
	private String discountRate;// 优惠折扣比
	private String oldDiscountRate;// 优惠折扣比(原值)
	private String oldDiscount;// 折扣(原值)
	private String preferDetails;// 优惠详情
	private String acctName;// 收款账户名
	private String oldAcctName;// 收款账户名(原值)
	private String acctNumber;// 收款账户号
	private String oldAcctNumber;// 收款账户号(原值)
	private List<String> defaultImage;// 相册
	private List<BankTaskResVo> taskList;// 任务单列表
	private String categories;// 所属分类名
	private String oldCategories;// 所属分类名(原值)

	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
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

	public String getTellphone() {
		return tellphone;
	}

	public void setTellphone(String tellphone) {
		this.tellphone = tellphone;
	}

	public String getSellTime() {
		return sellTime;
	}

	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPreferDetails() {
		return preferDetails;
	}

	public void setPreferDetails(String preferDetails) {
		this.preferDetails = preferDetails;
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

	public List<String> getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(List<String> defaultImage) {
		this.defaultImage = defaultImage;
	}

	public List<BankTaskResVo> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<BankTaskResVo> taskList) {
		this.taskList = taskList;
	}

	public String getOldOutletName() {
		return oldOutletName;
	}

	public void setOldOutletName(String oldOutletName) {
		this.oldOutletName = oldOutletName;
	}

	public String getOutletFullName() {
		return outletFullName;
	}

	public void setOutletFullName(String outletFullName) {
		this.outletFullName = outletFullName;
	}

	public String getOldOutletFullName() {
		return oldOutletFullName;
	}

	public void setOldOutletFullName(String oldOutletFullName) {
		this.oldOutletFullName = oldOutletFullName;
	}

	public String getOldTellphone() {
		return oldTellphone;
	}

	public void setOldTellphone(String oldTellphone) {
		this.oldTellphone = oldTellphone;
	}

	public String getOldAddress() {
		return oldAddress;
	}

	public void setOldAddress(String oldAddress) {
		this.oldAddress = oldAddress;
	}

	public String getOldContactName() {
		return oldContactName;
	}

	public void setOldContactName(String oldContactName) {
		this.oldContactName = oldContactName;
	}

	public String getOldContactPhone() {
		return oldContactPhone;
	}

	public void setOldContactPhone(String oldContactPhone) {
		this.oldContactPhone = oldContactPhone;
	}

	public String getOldDiscount() {
		return oldDiscount;
	}

	public void setOldDiscount(String oldDiscount) {
		this.oldDiscount = oldDiscount;
	}

	public String getOldAcctName() {
		return oldAcctName;
	}

	public void setOldAcctName(String oldAcctName) {
		this.oldAcctName = oldAcctName;
	}

	public String getOldAcctNumber() {
		return oldAcctNumber;
	}

	public void setOldAcctNumber(String oldAcctNumber) {
		this.oldAcctNumber = oldAcctNumber;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getOldDiscountCode() {
		return oldDiscountCode;
	}

	public void setOldDiscountCode(String oldDiscountCode) {
		this.oldDiscountCode = oldDiscountCode;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getOldDiscountRate() {
		return oldDiscountRate;
	}

	public void setOldDiscountRate(String oldDiscountRate) {
		this.oldDiscountRate = oldDiscountRate;
	}

	public String getOutletDescription() {
		return outletDescription;
	}

	public void setOutletDescription(String outletDescription) {
		this.outletDescription = outletDescription;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getOldCategories() {
		return oldCategories;
	}

	public void setOldCategories(String oldCategories) {
		this.oldCategories = oldCategories;
	}
	
}
