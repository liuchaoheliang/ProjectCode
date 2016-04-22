package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class MerchantUser {
	
	/**
	 * 用户ID
	 */
	private Long id; 
	
	/**
	 * 创建时间
	 */
	private Long createTime; 
	
	/**
	 * 商户ID
	 */
	private String merchantId; 
	
	/**
	 * 门店ID
	 */
	private String outletId; 
	
	/**
	 * 角色ID
	 */
	private String merchantRoleId; 
	
	/**
	 * 用户名
	 */
	private String username; 
	
	/**
	 * EMAIL
	 */
	private String email; 
	
	/**
	 * 电话
	 */
	private String phone; 
	
	/**
	 * 是否重置密码
	 */
	private Boolean isRest; 
	
	/**
	 * 是否有效
	 */
	private Boolean isDelete; 
	
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
     * 真实姓名
     */
	private String realname;
	
	/**
	 * 商户名称
	 */
	private String merchantName; 
	
	/**
	 * 门店名称
	 */
	private String outletName;
	
	/**
	 * 机构号
	 */
	private String orgCode;
	
	/**
	 * 是否超级管理员
	 */
	private Boolean isSuperAdmin;
	
	/**
	 * 商户状态 0-正常 1-禁用 2-解约
	 */
	private String merchantDisableStatus;
	
	public String getMerchantDisableStatus() {
		return merchantDisableStatus;
	}

	public void setMerchantDisableStatus(String merchantDisableStatus) {
		this.merchantDisableStatus = merchantDisableStatus;
	}

	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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

	public String getMerchantRoleId() {
		return merchantRoleId;
	}

	public void setMerchantRoleId(String merchantRoleId) {
		this.merchantRoleId = merchantRoleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsRest() {
		return isRest;
	}

	public void setIsRest(Boolean isRest) {
		this.isRest = isRest;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
