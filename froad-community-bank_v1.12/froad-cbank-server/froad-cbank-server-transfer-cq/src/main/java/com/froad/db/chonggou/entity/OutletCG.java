package com.froad.db.chonggou.entity;

import java.util.Date;




/**
 * CbOutlet po. 
 */


public class OutletCG implements java.io.Serializable {


	// Fields

	private Long id;
	private Date createTime;
	private String clientId;
	private String merchantId;
	private String outletId;
	private Long areaId;
	private Integer orderValue;
	private String outletName;
	private String outletFullname;
	private Boolean outletStatus;
	private String address;
	private String businessHours;
	private String zip;
	private String fax;
	private String phone;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String serviceProvider;
	private String longitude;
	private String latitude;
	private Boolean isEnable;
	private String description;
	private String preferDetails;
	private Date preferStartPeriod;
	private Date preferEndPeriod;
	private String discount;
	
	private Date startCreateTime;
	private Date endCreateTime;
	private String disableStatus;

	// Constructors

	public String getDisableStatus() {
		return disableStatus;
	}






	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
	}






	/** default constructor */
	public OutletCG() {
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

	


}