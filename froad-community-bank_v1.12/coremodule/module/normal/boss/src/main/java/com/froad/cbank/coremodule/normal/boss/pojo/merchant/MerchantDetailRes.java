package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.util.List;

import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.TypeInfoVo;

/**
 * 商户详情返回
 * @ClassName MerchantDetailRes
 * @author zxl
 * @date 2016年1月20日 上午9:38:02
 */
public class MerchantDetailRes{

	/**
	 * 创建时间
	 */
	private String createTime; // optional
	/**
	 * 客户端ID
	 */
	private String bankType; // optional
	/**
	 * 被发展组织机构代码的省级机构
	 */
	private String proOrgCode; // optional
	/**
	 * 被发展组织机构名称的省级机构
	 */
	private String proOrgName; // optional
	/**
	 * 被发展组织机构代码的市级机构
	 */
	private String cityOrgCode; // optional

	/**
	 * 被发展组织机构名称的市级机构
	 */
	private String cityOrgName; // optional
	/**
	 * 被发展组织机构代码的区级机构
	 */
	private String countyOrgCode; // optional

	/**
	 * 被发展组织机构名称的区级机构
	 */
	private String countyOrgName; // optional
	/**
	 * 是否银⾏行机构对应的商户
	 */
	private boolean merchantStatus; // optional
	/**
	 * 是否有效商户
	 */
	private boolean isEnable; // optional
	/**
	 * 无效状态,0正常;1禁用;2解约
	 */
	private String disableStatus; // optional
	/**
	 * 是否置顶
	 */
	private boolean isTop; // optional
	/**
	 * 起始审核机构
	 */
	private String auditStartOrgCode; // optional
	/**
	 * 最终审核机构
	 */
	private String auditEndOrgCode; // optional
	/**
	 * 审核状态
	 */
	private String auditState; // optional
	/**
	 * 待审核机构编号(完成置0)
	 */
	private String auditOrgCode; // optional
	/**
	 * 是否需要复核(0/1) - 已删除
	 */
	private boolean needReview; // optional
	/**
	 * 审核步骤(0-初审/1-复审)
	 */
	private String auditStage; // optional
	/**
	 * 审核时间
	 */
	private long auditTime; // optional
	/**
	 * 审核备注
	 */
	private String auditComment; // optional
	/**
	 * 审核人员
	 */
	private String auditStaff; // optional
	/**
	 * 复核人员
	 */
	private String reviewStaff; // optional
	/**
	 * 分类列表(查询使用 - 餐饮、休闲、美容、汽车等)
	 */
	private List<CategoryInfoVo> categoryInfoList; // optional

	/**
	 * 商户类型VO集合
	 */
	public List<TypeInfoVo> typeInfoVoList;

	/**
	 * 开始创建时间(查询条件使用)
	 */
	private long startCreateTime; // optional
	/**
	 * 结束创建时间(查询条件使用)
	 */
	private long endCreateTime; // optional
	/**
	 * 开始审核时间(查询条件使用)
	 */
	private long startAuditTime; // optional
	/**
	 * 结束审核时间(查询条件使用)
	 */
	private long endAuditTime; // optional

	private String qianyue; // 签约状态
	private String merchantId;
	private String userName;
	private String merchantAccountId;
	private String outletId;
	private String merchantName;
	private String merchantFullName;
	private String contactName;
	/**
	 * 所在地区
	 */
	public String areaId; // optional
	private String address;
	private String addressTemp;// 详情页显示
	private String zip;
	private String contactPhone;
	private String phone;
	private String contactEmail;
	private String legalName;
	private String legalCredentType;
	private String legalCredentNo;
	private String taxReg;
	private String license;
	private String orgCode;
	private String openingBank;
	private String contractStaff;
	private String contractBegintime;
	private String contractEndtime;
	private String logo;
	private String introduce;
	private String acctName;
	private String acctNumber;
	private String loginPhone;
	private long merchantUserId;
	private String merchantUserPwd;
	/**
	 * 类型编号
	 */
	public long merchantTypeId; // required
	/**
	 * 类别id
	 */
	public String merchantCategoryId; // required
	private OriginVo originVo;
	/**
	 * 商户vo
	 */
	private MerchantVo merchantVo;
	/**
	 * 商户用户vo
	 */
	private MerchantUserVo merchantUserVo;
	/**
	 * 商户用户vo
	 */
	private MerchantCategoryVo merchantCategoryVo;
	private List<String> outletIds;
	private String merchantUserName;
	/**
	 * 评论星级
	 */
	private int starLevel; // optional
	/**
	 * 机构名称(查询单个详细信息时返回)
	 */
	private String orgName; // required

	private StringBuilder category; // 商户分类
	private StringBuilder type; // 商户类型

	private String complaintPhone;

	private String areaName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMerchantAccountId() {
		return merchantAccountId;
	}

	public void setMerchantAccountId(String merchantAccountId) {
		this.merchantAccountId = merchantAccountId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressTemp() {
		return addressTemp;
	}

	public void setAddressTemp(String addressTemp) {
		this.addressTemp = addressTemp;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public String getTaxReg() {
		return taxReg;
	}

	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
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

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getContractStaff() {
		return contractStaff;
	}

	public void setContractStaff(String contractStaff) {
		this.contractStaff = contractStaff;
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

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
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

	public boolean isMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(boolean merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
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

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	public boolean isNeedReview() {
		return needReview;
	}

	public void setNeedReview(boolean needReview) {
		this.needReview = needReview;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
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

	public List<com.froad.thrift.vo.CategoryInfoVo> getCategoryInfoList() {
		return categoryInfoList;
	}

	public void setCategoryInfoList(
			List<com.froad.thrift.vo.CategoryInfoVo> categoryInfoList) {
		this.categoryInfoList = categoryInfoList;
	}

	public long getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public long getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public long getStartAuditTime() {
		return startAuditTime;
	}

	public void setStartAuditTime(long startAuditTime) {
		this.startAuditTime = startAuditTime;
	}

	public long getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(long endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	public long getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public String getMerchantUserPwd() {
		return merchantUserPwd;
	}

	public void setMerchantUserPwd(String merchantUserPwd) {
		this.merchantUserPwd = merchantUserPwd;
	}

	public long getMerchantTypeId() {
		return merchantTypeId;
	}

	public void setMerchantTypeId(long merchantTypeId) {
		this.merchantTypeId = merchantTypeId;
	}

	public String getMerchantCategoryId() {
		return merchantCategoryId;
	}

	public void setMerchantCategoryId(String merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}

	public OriginVo getOriginVo() {
		return originVo;
	}

	public void setOriginVo(OriginVo originVo) {
		this.originVo = originVo;
	}

	public MerchantVo getMerchantVo() {
		return merchantVo;
	}

	public void setMerchantVo(MerchantVo merchantVo) {
		this.merchantVo = merchantVo;
	}

	public MerchantUserVo getMerchantUserVo() {
		return merchantUserVo;
	}

	public void setMerchantUserVo(MerchantUserVo merchantUserVo) {
		this.merchantUserVo = merchantUserVo;
	}

	public MerchantCategoryVo getMerchantCategoryVo() {
		return merchantCategoryVo;
	}

	public void setMerchantCategoryVo(MerchantCategoryVo merchantCategoryVo) {
		this.merchantCategoryVo = merchantCategoryVo;
	}

	public List<String> getOutletIds() {
		return outletIds;
	}

	public void setOutletIds(List<String> outletIds) {
		this.outletIds = outletIds;
	}

	public int getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<com.froad.thrift.vo.TypeInfoVo> getTypeInfoVoList() {
		return typeInfoVoList;
	}

	public void setTypeInfoVoList(
			List<com.froad.thrift.vo.TypeInfoVo> typeInfoVoList) {
		this.typeInfoVoList = typeInfoVoList;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public boolean isEnable() {
		return isEnable;
	}

	// public String getEnable() {
	// return enable;
	// }
	// public void setEnable(String enable) {
	// this.enable = enable;
	// }
	public StringBuilder getCategory() {
		return category;
	}

	public void setCategory(StringBuilder category) {
		this.category = category;
	}

	public StringBuilder getType() {
		return type;
	}

	public void setType(StringBuilder type) {
		this.type = type;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getQianyue() {
		return qianyue;
	}

	public void setQianyue(String qianyue) {
		this.qianyue = qianyue;
	}

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProOrgName() {
		return proOrgName;
	}

	public void setProOrgName(String proOrgName) {
		this.proOrgName = proOrgName;
	}

	public String getCityOrgName() {
		return cityOrgName;
	}

	public void setCityOrgName(String cityOrgName) {
		this.cityOrgName = cityOrgName;
	}

	public String getCountyOrgName() {
		return countyOrgName;
	}

	public void setCountyOrgName(String countyOrgName) {
		this.countyOrgName = countyOrgName;
	}

}
