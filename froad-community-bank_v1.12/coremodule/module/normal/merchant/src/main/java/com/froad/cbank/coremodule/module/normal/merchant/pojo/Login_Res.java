package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Login_Res {
	
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 门店ID
	 */
	private String outletId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 是否首次登录 0否 1是
	 */
	private String firstLogin;
	/**
	 * 角色
	 */
	private Long merchantRole;
	/**
	 * 角色类型 1admin 2operator
	 */
	private String roleType;
	/**
	 * token
	 */
	private String token;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 资源列表
	 */
//	private List<Query_User_Resource_Res> resource;
	private List<FinitResourceVo> rootResources;
	
	/**
	 * 是否超级管理员
	 */
	private Boolean isAdmin;
	
	/**
	 * 商户状态 0-正常 1-禁用 2-解约
	 */
	private String merchantDisableStatus;
	
	/**
	 * 商户类型
	 */
	private String merchantType;
	
	/**
	 * 是否需要验证码
	 */
	private boolean isVerify;
	private int loginFailureCount;
	/**
	 * 密码是否重置
	 */
	private boolean isRest; // optional
	
	/**
	 * 门店状态
	 */
	private String  outletStatus;
	
	/**
	 * 是否是默认门店 1:true 默认门店  0：false不是默认门店
	 */
	private String isDefault;
	
	
	
	public String getOutletStatus() {
		return outletStatus;
	}

	public void setOutletStatus(String outletStatus) {
		this.outletStatus = outletStatus;
	}

	public boolean isRest() {
		return isRest;
	}

	public void setRest(boolean isRest) {
		this.isRest = isRest;
	}

	public int getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public String getMerchantDisableStatus() {
		return merchantDisableStatus;
	}

	public void setMerchantDisableStatus(String merchantDisableStatus) {
		this.merchantDisableStatus = merchantDisableStatus;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Long getMerchantRole() {
		return merchantRole;
	}

	public void setMerchantRole(Long merchantRole) {
		this.merchantRole = merchantRole;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}



	public List<FinitResourceVo> getRootResources() {
		return rootResources;
	}

	public void setRootResources(List<FinitResourceVo> rootResources) {
		this.rootResources = rootResources;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}
