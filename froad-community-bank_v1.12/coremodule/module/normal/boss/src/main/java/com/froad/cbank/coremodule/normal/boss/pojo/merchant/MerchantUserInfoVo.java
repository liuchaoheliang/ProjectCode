package com.froad.cbank.coremodule.normal.boss.pojo.merchant;
/**
 * 类描述：商户用户实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-3下午4:38:39 
 */
public class MerchantUserInfoVo {
	private String id; // ID
	/**
	 * 创建时间
	 */
	private String createTime; 
	/**
	 * 客户端ID
	 */
	private String clientId; 
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
	   * 帐号
	   */
	private String userName; 
	  /**
	   * 邮箱地址
	   */
	private String email; 
	  /**
	   * 电话号码
	   */
	private String phone; 
	  /**
	   * 密码是否重置
	   */
	private boolean isRest; 
	  /**
	   * 是否删除
	   */
	private boolean isDelete; 
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
	   * 结构码
	   */
	private String orgCode; 
	
	private boolean isUse;//是否可用
	
	private boolean isLocal;//是否锁定
	private String lastLogin;//最后登录
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public boolean isRest() {
		return isRest;
	}
	public void setRest(boolean isRest) {
		this.isRest = isRest;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public boolean isUse() {
		return isUse;
	}
	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}
	public boolean isLocal() {
		return isLocal;
	}
	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	
}
