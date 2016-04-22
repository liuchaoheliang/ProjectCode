package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Outlet_Info_Res {
	private Long areaId;
	private String outletName;
	private String outletFullname;
	private String address;
	private String contactPhone;
	private String areaName;
	private String ClientId;
	private String merchantId;
	private String outletId;
	private Long id;
	private String phone;
	
	/**
	 * 是否是默认门店 1:true 默认门店  0：false不是默认门店
	 */
	private String isDefault;

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
	 * 审核状态 0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=审核通过待同步
	 */
	private String auditState; // optional
	/**
	 * 编辑审核状态 0=待审核 ,1=审核通过 ,2=审核不通过 ,3=未提交 ,4=审核通过待同步
	 */
	private String editAuditState; // optional

	/**
	 * 是否有效
	 */
	private Boolean isEnable; // optional
	/**
	 * 无效状态,0正常;1禁用;2解约
	 */
	private String disableStatus; // optional
	
    /**
     *门店二维码
     */
    public String qrcodeUrl;
    /**
     * 门店星级
     */
	public String starLevel;


	
	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(String disableStatus) {
		this.disableStatus = disableStatus;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClientId() {
		return ClientId;
	}

	public void setClientId(String clientId) {
		ClientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}
