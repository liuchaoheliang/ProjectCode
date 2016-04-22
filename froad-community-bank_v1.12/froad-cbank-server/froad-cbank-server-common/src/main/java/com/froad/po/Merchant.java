package com.froad.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;

import com.froad.logback.LogCvt;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.TypeInfo;

/**
 * CbMerchant po.
 */
@Guarded
public class Merchant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private Date createTime;
	
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	private String merchantId;
	
	@MaxLength(value = 16, message = "一级机构码不能超过{max}个字符")
	private String proOrgCode;
	
	@MaxLength(value = 16, message = "二级机构码不能超过{max}个字符")
	private String cityOrgCode;
	
	@MaxLength(value = 16, message = "三级机构码不能超过{max}个字符")
	private String countyOrgCode;
	
	@MaxLength(value = 16, message = "商户所属机构码不能超过{max}个字符")
	private String orgCode;
	
	@MaxLength(value = 32, message = "商户名称不能超过{max}个字符")
	private String merchantName;
	
	@MaxLength(value = 64, message = "商户全称不能超过{max}个字符")
	private String merchantFullname;
	
	@MaxLength(value = 255, message = "商户logo不能超过{max}个字符")
	private String logo;
	
	@MaxLength(value = 64, message = "商户地址不能超过{max}个字符")
	private String address;
	
	@MaxLength(value = 20, message = "商户电话不能超过{max}个字符")
	private String phone;
	@Min(1)
	private Long areaId;
	private Boolean merchantStatus;
	private Boolean isEnable;
	
	@MaxLength(value = 1, message = "商户不可用状态不能超过{max}个字符")
	private String disableStatus;
	
	private Boolean isTop;
	
	@MaxLength(value = 500, message = "商户简介不能超过{max}个字符")
	private String introduce;
	
	@MaxLength(value = 64, message = "营业执照不能超过{max}个字符")
	private String license;
	
	@MaxLength(value = 64, message = "税务登记证不能超过{max}个字符")
	private String taxReg;
	
	// @Future(message="签约开始时间必须大于当前时间")
	private Date contractBegintime;
	// @Future(message="签约到期时间必须大于当前时间")
	private Date contractEndtime;
	
	@MaxLength(value = 16, message = "初始审核机构不能超过{max}个字符")
	private String auditStartOrgCode;
	
	@MaxLength(value = 16, message = "结束审核机构不能超过{max}个字符")
	private String auditEndOrgCode;
	
	@MaxLength(value = 32, message = "签约人员不能超过{max}个字符")
	private String contractStaff;
	
	@MaxLength(value = 1, message = "审核状态不能超过{max}个字符")
	private String auditState;
	
	@MaxLength(value = 16, message = "审核机构不能超过{max}个字符")
	private String auditOrgCode;
	
	@MaxLength(value = 1, message = "审核步骤不能超过{max}个字符")
	private String auditStage;
	
	private Date auditTime;
	
	@MaxLength(value = 200, message = "审核备注不能超过{max}个字符")
	private String auditComment;
	
	private String auditStaff;
	
	@MaxLength(value = 16, message = "复核人员不能超过{max}个字符")
	private String reviewStaff;
	
	@MaxLength(value = 16, message = "联系人姓名不能超过{max}个字符")
	private String contactName;
	
	@MaxLength(value = 16, message = "联系人电话不能超过{max}个字符")
	private String contactPhone;
	
	@MaxLength(value = 32, message = "联系人邮箱不能超过{max}个字符")
	@Email(when="js:this.contactEmail != null && !''", message = "联系人邮箱不合法")
	private String contactEmail;
	
	@MaxLength(value = 16, message = "法人姓名不能超过{max}个字符")
	private String legalName;
	
	@Range(min = 1, max = 7, message = "法人证件类型必须从{min}到{max}之内")
	private Integer legalCredentType;
	
	@MaxLength(value = 32, message = "法人证件号码不能超过{max}个字符")
	private String legalCredentNo;
	
	@MaxLength(value = 20, message = "投诉电话不能超过{max}个字符")
	private String complaintPhone;
	
	//机构名称
	private String orgName;
	
	//商户用户手机号
	private String mobile;
	
	//商户用户用户名
	private String merchantUserName;
	
	// 机构查询优化
	private List<String> orgCodesCondition;
	
	//是否外包
	private Boolean isOutsource;
	
	//外包公司Id
	private Long companyId;
	
	// 商户分类
	private List<CategoryInfo> categoryInfoObjList;
	// 商户类型
	private List<TypeInfo> typeInfoObjList;
	
	//账户名
	private String acctName;
	
	//账号
	private String acctNumber;
	
	//联系人电话
	private String contactTelephone;
	
	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	private Long monthMoney;
	private Long groupOrderCount;
	private Long sellOrderCount;
	private String userOrgCode;
	
	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}

	private String companyCredential;
	private String editAuditState;

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
	// private Long merchantUserId;
	// private String merchantUserName; // optional
	// private Long merchantRoleId;// optional

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
	
	private Date operateTime;
	private String operateUser;

	// Constructors

	/** default constructor */
	public Merchant() {
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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public List<String> getOrgCodesCondition() {
		return orgCodesCondition;
	}

	public void setOrgCodesCondition(List<String> orgCodesCondition) {
		this.orgCodesCondition = orgCodesCondition;
	}

	public Boolean getIsOutsource() {
		return isOutsource;
	}

	public void setIsOutsource(Boolean isOutsource) {
		this.isOutsource = isOutsource;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<CategoryInfo> getCategoryInfoObjList() {
		return categoryInfoObjList;
	}

	public void setCategoryInfoObjList(List<CategoryInfo> categoryInfoObjList) {
		this.categoryInfoObjList = categoryInfoObjList;
	}

	public List<TypeInfo> getTypeInfoObjList() {
		return typeInfoObjList;
	}

	public void setTypeInfoObjList(List<TypeInfo> typeInfoObjList) {
		this.typeInfoObjList = typeInfoObjList;
	}
	
	public List<String> getCategoryIdList() {
		List<String> ids = new ArrayList<String>();
		try {
			if (null != categoryInfoObjList) {
				for(CategoryInfo info : this.categoryInfoObjList){
					ids.add(info.getCategoryId().toString());
				}
			}
		} catch (Exception e) {
			LogCvt.error("商户分类ID获取异常", e);
		}
		return ids;
	}
	
	public List<String> getCategoryNameList() {
		List<String> names = new ArrayList<String>();
		try {
			if (null != categoryInfoObjList) {
				for(CategoryInfo info : this.categoryInfoObjList){
					names.add(info.getName());
				}
			}
		} catch (Exception e) {
			LogCvt.error("商户分类名称获取异常", e);
		}
		return names;
	}
	
	public List<String> getTypeIdList() {
		List<String> ids = new ArrayList<String>();
		try {
			if (null != typeInfoObjList) {
				for(TypeInfo info : this.typeInfoObjList){
					ids.add(info.getType());
				}
			}
		} catch (Exception e) {
			LogCvt.error("商户类型ID获取异常", e);
		}
		return ids;
	}
	
	public List<String> getTypeNameList() {
		List<String> names = new ArrayList<String>();
		try {
			if (null != typeInfoObjList) {
				for(TypeInfo info : this.typeInfoObjList){
					names.add(info.getTypeName());
				}
			}
		} catch (Exception e) {
			LogCvt.error("商户类型名称获取异常", e);
		}
		return names;
	}

}