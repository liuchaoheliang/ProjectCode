package com.froad.po;

import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;

import com.froad.po.mongo.CategoryInfo;

/**
 * CbOutlet po. 
 */

@Guarded
public class Outlet implements java.io.Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2740034089614554675L;
	
	private Long id;
	private Date createTime;
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	private String merchantId;
	@MaxLength(value = 20, message = "门店id不能超过{max}个字符")
	private String outletId;
	private Long areaId;
	private Integer orderValue;
	@MaxLength(value = 32, message = "门店名称不能超过{max}个字符")
	private String outletName;
	@MaxLength(value = 64, message = "门店全称不能超过{max}个字符")
	private String outletFullname;
	private Boolean outletStatus;
	@MaxLength(value = 64, message = "门店地址不能超过{max}个字符")
	private String address;
	@MaxLength(value = 32, message = "营业时间不能超过{max}个字符")
	private String businessHours;
	@MaxLength(value = 16, message = "邮编不能超过{max}个字符")
	private String zip;
	@MaxLength(value = 20, message = "传真不能超过{max}个字符")
	private String fax;
	@MaxLength(value = 20, message = "电话不能超过{max}个字符")
	private String phone;
	@MaxLength(value = 16, message = "联系人姓名不能超过{max}个字符")
	private String contactName;
	@MaxLength(value = 20, message = "联系人电话不能超过{max}个字符")
	private String contactPhone;
	@MaxLength(value = 32, message = "联系人邮箱不能超过{max}个字符")
	@Email(when="js:this.email != null && !''", message="联系人邮箱不合法")
	private String contactEmail;
	@MaxLength(value = 32, message = "服务提供商不能超过{max}个字符")
	private String serviceProvider;
//	@Range(min=-180, max=180, message="经度必须为{min}至{max}范围之内")
	private String longitude;
//	@Range(min=-90, max=90, message="纬度必须为{min}至{max}范围之内")
	private String latitude;
	private Boolean isEnable;
	@MaxLength(value = 1, message="不可用状态不能超过{max}个字符")
	private String disableStatus;
//	@MaxLength(value = 300, message = "描述不能超过{max}个字符")
	private String description;
	
//	@MaxLength(value = 3000, message = "优惠详情不能超过{max}个字符")
	private String preferDetails;
	
	private Date preferStartPeriod;
	private Date preferEndPeriod;
//	@MaxLength(value = 32, message="折扣不能超过{max}个字符")
	private String discount;
	@MaxLength(value = 64, message = "优惠折扣码不能超过{max}个字符")
	private String discountCode;
	@MaxLength(value = 3, message = "优惠折扣比例不能超过{max}个字符")
	private String discountRate;
	
	private Date startCreateTime;
	private Date endCreateTime;

	private List<String> disableStatusList;
	
	private Date auditTime;
    
	private String auditComment;
	
	private String auditStaff;
    
	@MaxLength(value = 1, message = "审核状态不能超过{max}个字符")
	private String auditState;

	private String editAuditState;
	

	private List<CategoryInfo> categoryInfo;

	
	private Boolean isDefault;
	
	private Boolean preferStatus;//优惠状态 0=无效  1=有效
	
	private String qrcodeUrl;//门店二维码	
	
	/** default constructor */
	public Outlet() {
	}
    
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPreferStartPeriod() {
		return preferStartPeriod;
	}

	public void setPreferStartPeriod(Date preferStartPeriod) {
		this.preferStartPeriod = preferStartPeriod;
	}

	public Date getPreferEndPeriod() {
		return preferEndPeriod;
	}

	public void setPreferEndPeriod(Date preferEndPeriod) {
		this.preferEndPeriod = preferEndPeriod;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getOutletId() {
		return this.outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	
	public Long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public Integer getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
	public String getOutletName() {
		return this.outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	public String getOutletFullname() {
		return this.outletFullname;
	}

	public void setOutletFullname(String outletFullname) {
		this.outletFullname = outletFullname;
	}
	
	public Boolean getOutletStatus() {
		return this.outletStatus;
	}

	public void setOutletStatus(Boolean outletStatus) {
		this.outletStatus = outletStatus;
	}
	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getBusinessHours() {
		return this.businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
	public String getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPreferDetails() {
		return this.preferDetails;
	}

	public void setPreferDetails(String preferDetails) {
		this.preferDetails = preferDetails;
	}

	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
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

	public List<String> getDisableStatusList() {
		return disableStatusList;
	}

	public void setDisableStatusList(List<String> disableStatusList) {
		this.disableStatusList = disableStatusList;
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
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


	public List<CategoryInfo> getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
		this.categoryInfo = categoryInfo;
	}

	public Boolean getPreferStatus() {
		return preferStatus;
	}

	public void setPreferStatus(Boolean preferStatus) {
		this.preferStatus = preferStatus;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}	
}