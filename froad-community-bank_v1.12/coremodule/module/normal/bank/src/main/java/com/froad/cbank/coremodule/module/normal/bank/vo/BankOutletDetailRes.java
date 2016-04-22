package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class BankOutletDetailRes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3139181371222087488L;
	/**
	 * 主键ID
	 */
	private Long id; 
	/**
	 * 创建时间
	 */
	private Long createTime; 
	/**
	 * 地区ID
	 */
	private String areaId; 
	/**
	 * 地区
	 */
	private String areaName;
	/**
	 * 排序
	 */
	private Integer orderValue; 
	/**
	 * 门店名称
	 */
	private String outletName; 
	/**
	 * 门店全名
	 */
	private String outletFullname; 
	/**
	 * 是否银⾏行机构对应⻔门店
	 */
	private Boolean outletStatus; 
	/**
	 * 地址
	 */
	private String address; 
	/**
	 * 营业时间
	 */
	private String businessHours; 
	/**
	 * 邮编
	 */
	private String zip; 
	/**
	 * 传真
	 */
	private String fax; 
	/**
	 * 电话
	 */
	private String phone; 
	/**
	 * 联系人姓名
	 */
	private String contactName; 
	/**
	 * 联系人电话
	 */
	private String contactPhone; 
	/**
	 * 联系人邮箱
	 */
	private String contactEmail; 
	/**
	 * 服务提供商
	 */
	private String serviceProvider; 
	/**
	 * 经度
	 */
	private String longitude; 
	/**
	 * 纬度
	 */
	private String latitude; 
	/**
	 * 是否有效
	 */
	private Boolean isEnable; 
	/**
	 * 描述
	 */
	private String description; 
	/**
	 * 优惠详情
	 */
	private String preferDetails; 
	/**
	 * 优惠开始时间
	 */
	private String preferStartPeriod; 
	/**
	 * 优惠结束时间
	 */
	private String preferEndPeriod; 
	/**
	 * 折扣
	 */
	private String discount; 
	/**
	 * 开始创建时间(查询条件使用)
	 */
	private String startCreateTime; 
	/**
	 * 结束创建时间(查询条件使用)
	 */
	private String endCreateTime; 
	/**
	 * 图片集合
	 */
	private List<OutletImageInfoRes> imgList;
	/**
	 * 账户名称
	 */
	private String acctName;
	/**
	 * 账户号
	 */
	private String acctNumber;
	/**
	 * 开户行
	 */
	private String openingBank;
	/**
	 * 省id
	 */
    private String countyId;
	/**
	 * 省
	 */
    private String countyName;
	/**
	 * 市id
	 */
    private String cityId;
	/**
	 * 市
	 */
    private String cityName;
    
    /**
     * 一级机构号
     */
    private String orgCodeLev1;
    /**
     * 一级机构名称
     */
    private String orgNameLev1;
    /**
     * 二级机构号
     */
    private String orgCodeLev2;
    /**
     * 二级机构名称
     */
    private String orgNameLev2;
    /**
     * 三级机构号
     */
    private String orgCodeLev3;
    /**
     * 三级机构名称
     */
    private String orgNameLev3;
    
    
    private String istrue;
    
    /**
     * 2015-08-19 优惠折扣进行规范化
      * 优惠折扣码
      */
    public String discountCode; 
    /**
     *  2015-08-19 优惠折扣进行规范化
      * 优惠折扣比
      */
    public String discountRate; 
    /**
     * 审核时间
     */
    private Long auditTime; // optional
    /**
     * 审核备注
     */
    private String auditComment; // optional
    /**
     * 审核人
     */
    private String auditStaff; // optional
    /**
     * 审核状态   0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=审核通过待同步
     */
    private String auditState; // optional
    /**
     * 编辑审核状态  0=待审核 ,1=审核通过 ,2=审核不通过 ,3=未提交 ,4=审核通过待同步
     */
    private String editAuditState; // optional  
    
    /**
     * 门店分类列表信息
     */
    private List<BankOutletCategoryInfoRes> categoryList;
    
    /**
     * 是否是大类
     */
    private String isBigCategory;
    
	/**
	 * 是否惠付 true是 false不是
	 */
	private boolean preferStatus;
	/**
	 * 评级分数
	 */
    private String starLevel;
    
	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public boolean isPreferStatus() {
		return preferStatus;
	}

	public void setPreferStatus(boolean preferStatus) {
		this.preferStatus = preferStatus;
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

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getEditAuditState() {
		return editAuditState;
	}

	public void setEditAuditState(String editAuditState) {
		this.editAuditState = editAuditState;
	}

	public String getIstrue() {
		return istrue;
	}

	public void setIstrue(String istrue) {
		this.istrue = istrue;
	}

	public String getOrgCodeLev1() {
		return orgCodeLev1;
	}

	public void setOrgCodeLev1(String orgCodeLev1) {
		this.orgCodeLev1 = orgCodeLev1;
	}

	public String getOrgNameLev1() {
		return orgNameLev1;
	}

	public void setOrgNameLev1(String orgNameLev1) {
		this.orgNameLev1 = orgNameLev1;
	}

	public String getOrgCodeLev2() {
		return orgCodeLev2;
	}

	public void setOrgCodeLev2(String orgCodeLev2) {
		this.orgCodeLev2 = orgCodeLev2;
	}

	public String getOrgNameLev2() {
		return orgNameLev2;
	}

	public void setOrgNameLev2(String orgNameLev2) {
		this.orgNameLev2 = orgNameLev2;
	}

	public String getOrgCodeLev3() {
		return orgCodeLev3;
	}

	public void setOrgCodeLev3(String orgCodeLev3) {
		this.orgCodeLev3 = orgCodeLev3;
	}

	public String getOrgNameLev3() {
		return orgNameLev3;
	}

	public void setOrgNameLev3(String orgNameLev3) {
		this.orgNameLev3 = orgNameLev3;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
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


	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletFullname() {
		return outletFullname;
	}

	public void setOutletFullname(String outletFullname) {
		this.outletFullname = outletFullname;
	}

	public Boolean getOutletStatus() {
		return outletStatus;
	}

	public void setOutletStatus(Boolean outletStatus) {
		this.outletStatus = outletStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}


	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreferDetails() {
		return preferDetails;
	}

	public void setPreferDetails(String preferDetails) {
		this.preferDetails = preferDetails;
	}

	public String getPreferStartPeriod() {
		return preferStartPeriod;
	}

	public void setPreferStartPeriod(String preferStartPeriod) {
		this.preferStartPeriod = preferStartPeriod;
	}

	public String getPreferEndPeriod() {
		return preferEndPeriod;
	}

	public void setPreferEndPeriod(String preferEndPeriod) {
		this.preferEndPeriod = preferEndPeriod;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public List<OutletImageInfoRes> getImgList() {
		return imgList;
	}

	public void setImgList(List<OutletImageInfoRes> imgList) {
		this.imgList = imgList;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public List<BankOutletCategoryInfoRes> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<BankOutletCategoryInfoRes> categoryList) {
		this.categoryList = categoryList;
	}

	public String getIsBigCategory() {
		return isBigCategory;
	}

	public void setIsBigCategory(String isBigCategory) {
		this.isBigCategory = isBigCategory;
	}
	
}
