package com.froad.CB.po;

import com.froad.CB.common.Pager;

/**
 * 商户操作组
 * @author FQ
 *
 */
public class MerchantUserSet extends Pager{
    
	private Integer id;
	private String merchantId;//商户ID
	private String userId;//商户user_id
	private String loginName;//商户登录名
	private String beCode;//工号
	private String beCodepwd;//密码
	private String beName;//操作员名称
	private String beSpec;//说明
	private String isAdmin;//是否是管理员 0-否 1-是
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	
	//=======================商户安全机制
    private String isAccountLocked;//账号是否锁定   (0  账户被锁定  1 账户正常)
	private Integer loginFailureCount;//连续登陆失败次数
	private String lockedDate;//账号锁定日期
	private String lastTryTime; //该帐号的最后尝试登录的时间
	//========================权限系统
	private String belongStoreId;  //所属分店Id
	private String roleType;       //类型  0:普通操作员  1:财务人员
	
	public String getIsAccountLocked() {
		return isAccountLocked;
	}
	public void setIsAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}
	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	public String getLockedDate() {
		return lockedDate;
	}
	public void setLockedDate(String lockedDate) {
		this.lockedDate = lockedDate;
	}
	public String getLastTryTime() {
		return lastTryTime;
	}
	public void setLastTryTime(String lastTryTime) {
		this.lastTryTime = lastTryTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getBeCode() {
		return beCode;
	}
	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}
	public String getBeCodepwd() {
		return beCodepwd;
	}
	public void setBeCodepwd(String beCodepwd) {
		this.beCodepwd = beCodepwd;
	}
	public String getBeName() {
		return beName;
	}
	public void setBeName(String beName) {
		this.beName = beName;
	}
	public String getBeSpec() {
		return beSpec;
	}
	public void setBeSpec(String beSpec) {
		this.beSpec = beSpec;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBelongStoreId() {
		return belongStoreId;
	}
	public void setBelongStoreId(String belongStoreId) {
		this.belongStoreId = belongStoreId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
}