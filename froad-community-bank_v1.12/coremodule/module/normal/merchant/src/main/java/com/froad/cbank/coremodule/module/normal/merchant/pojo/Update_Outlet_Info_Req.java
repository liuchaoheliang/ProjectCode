package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.framework.common.valid.Regulars;

public class Update_Outlet_Info_Req extends BasePojo {

	@NotEmpty(value="门店ID不为空")
	private String outletId;

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
	@NotEmpty(value="地区不能为空")
	private Long areaId; 
	/**
	 * 排序
	 */
	private Integer orderValue; 
	/**
	 * 门店名称
	 */
	@NotEmpty(value="门店简称不能为空")
	private String outletName; 
	/**
	 * 门店全名
	 */
	@NotEmpty(value="门店全称不能为空")
	private String outletFullname; 
	/**
	 * 是否银⾏行机构对应门店
	 */
	private Boolean outletStatus; 
	/**
	 * 地址
	 */
	@NotEmpty(value="地址不能为空")
	@Regular(reg="^.{1,64}$",value="详细地址不能超过64位")
	private String address; 
	/**
	 * 营业时间
	 */
	@NotEmpty(value="营业时间不能为空")
	@Regular(reg="^.{1,32}$",value="营业时间最长32位")
	private String businessHours; 
	/**
	 * 邮编
	 */
	@Regular(reg="^[0-9]{1,6}$",value="邮编格式不正确")
	private String zip; 
	/**
	 * 传真
	 */
	@Regular(reg="^.{1,20}$",value="传真最多20位")
	private String fax; 
	/**
	 * 电话
	 */
	@NotEmpty(value="电话不能为空")
	@Regular(reg="^.{1,20}$",value="电话最多20位")
	private String phone; 
	/**
	 * 联系人姓名
	 */
	@Regular(reg="^[A-Za-z\u4e00-\u9fa5]{2,16}$",value="联系人姓名格式不正确")
	private String contactName; 
	/**
	 * 联系人电话
	 */
	@Regular(reg=Regulars.MOBILE,value="手机号格式不正确")
	private String contactPhone; 
	/**
	 * 联系人邮箱
	 */
	@Regular(reg=Regulars.EMAIL,value="邮箱格式不正确")
	private String contactEmail; 
	/**
	 * 服务提供商
	 */
	private String serviceProvider; 
	/**
	 * 经度
	 */
	@NotEmpty(value="经度不能为空")
	private String longitude; 
	/**
	 * 纬度
	 */
	@NotEmpty(value="纬度不能为空")
	private String latitude; 
	/**
	 * 是否有效
	 */
	private Boolean isEnable; 
	/**
	 * 描述
	 */
	@NotEmpty(value="门店描述不能为空")
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
     * 2015-08-19 优惠折扣进行规范化
      * 优惠折扣码
      */
    public String discountCode; 
    /**
     *  2015-08-19 优惠折扣进行规范化
      * 优惠折扣比
      */
    public String discountRate; 
	
	private List<Image_Info_Req> imgList;
	

	@Regular(reg="^[\u4e00-\u9fa5]{1,30}$",value="请输入正确的收款账户名")
	private String acctName;
	

	@Regular(reg="^[0-9]{1,24}$",value="请输入正确的收款账户号")
	private String acctNumber;
	
	private String openingBank;


	
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
    @NotEmpty(value="审核状态不能为空")
    private String auditState; // optional
    /**
     * 编辑审核状态  0=待审核 ,1=审核通过 ,2=审核不通过 ,3=未提交 ,4=审核通过待同步
     */
    @NotEmpty(value="编辑审核状态不能为空")
    private String editAuditState; // optional  
    
    /**
	 * 2015-11-05 门店二级分类类目
	 */
	private String[] categoryInfo;
    
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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

	public List<Image_Info_Req> getImgList() {
		return imgList;
	}

	public void setImgList(List<Image_Info_Req> imgList) {
		this.imgList = imgList;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
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

	public String[] getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(String[] categoryInfo) {
		this.categoryInfo = categoryInfo;
	}

}
