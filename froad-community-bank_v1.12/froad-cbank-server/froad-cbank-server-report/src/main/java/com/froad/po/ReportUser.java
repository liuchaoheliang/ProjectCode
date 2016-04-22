package com.froad.po;

import java.util.Date;

public class ReportUser {

	private Long id;
	private Date createTime;		//日期
	private String clientId;		//客户端ID
	private String bankCardId;		//银行卡id(银行卡10-11位数字)
	private String forgCode;		//一级机构码
	private String forgName;		//一级机构名称
	private String sorgCode;		//二级机构码
	private String sorgName;		//二级机构名称
	private String torgCode;		//三级机构码
	private String torgName;		//三级机构名称
	private String lorgCode;		//四级机构码
	private String lorgName;		//四级机构名称
	private Long userId;			//用户ID
	private String loginId;			//用户名称
	private String userName;		//客户名称
	private String mobile;			//注册手机号
	private Date regTime;			//注册日期
	private String regType;			//注册类型
	private Boolean isVip;			//是否VIP
	private Date signTime;			//签约/解约日期
	private Integer validStatus;	//签约情况
	
	
	private Long totalUsers;		//用户总数


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public String getBankCardId() {
		return bankCardId;
	}


	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}


	public String getForgCode() {
		return forgCode;
	}


	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}


	public String getForgName() {
		return forgName;
	}


	public void setForgName(String forgName) {
		this.forgName = forgName;
	}


	public String getSorgCode() {
		return sorgCode;
	}


	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}


	public String getSorgName() {
		return sorgName;
	}


	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}


	public String getTorgCode() {
		return torgCode;
	}


	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}


	public String getTorgName() {
		return torgName;
	}


	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}


	public String getLorgCode() {
		return lorgCode;
	}


	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}


	public String getLorgName() {
		return lorgName;
	}


	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Date getRegTime() {
		return regTime;
	}


	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	public String getRegType() {
		return regType;
	}


	public void setRegType(String regType) {
		this.regType = regType;
	}


	public Boolean getIsVip() {
		return isVip;
	}


	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}


	public Date getSignTime() {
		return signTime;
	}


	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}


	public Integer getValidStatus() {
		return validStatus;
	}


	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}


	public Long getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}
	
}
