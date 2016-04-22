package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.thrift.vo.MerchantUserVo;

/**
 * 类描述：商户用户请求类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-5-3下午4:45:30
 */
public class MerchantUserInfoReq extends Page implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 2229663389991284191L;

	private String id; // ID
	/**
	 * 创建时间
	 */
	private String createTime; // optional
	/**
	 * 客户端ID
	 */
	private String clientId; // optional
	/**
	 * 商户ID
	 */
	private String merchantId; // optional
	/**
	 * 门店ID
	 */
	private String outletId; // optional
	/**
	 * 角色ID
	 */
	private String merchantRoleId; // optional
	/**
	 * 帐号
	 */
	private String userName; // optional
	/**
	 * 密码
	 */
	private String password; // optional
	/**
	 * 邮箱地址
	 */
	private String email; // optional
	/**
	 * 电话号码
	 */
	private String phone; // optional
	/**
	 * 密码是否重置
	 */
	private boolean isRest; // optional
	/**
	 * 是否删除
	 */
	private boolean isDelete; // optional
	/**
	 * 真实姓名
	 */
	private String realname; // optional
	/**
	 * 商户名称
	 */
	private String merchantName; // optional
	/**
	 * 门店名称
	 */
	private String outletName; // optional
	/**
	 * 结构码
	 */
	private String orgCode; // optional

	private boolean isUse;// 是否可用

	private boolean isLocal;// 是否锁定
	private String lastLogin;// 最后登录

	private MerchantUserVo merchantUserVo;

	private long begDate;
	private long startDate;
	private long endDate;
	private Long beginTime;
	private Long endTime;

	public long getBegDate() {
		return begDate;
	}

	public void setBegDate(long begDate) {
		this.begDate = begDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public MerchantUserVo getMerchantUserVo() {
		return merchantUserVo;
	}

	public void setMerchantUserVo(MerchantUserVo merchantUserVo) {
		this.merchantUserVo = merchantUserVo;
	}

}
