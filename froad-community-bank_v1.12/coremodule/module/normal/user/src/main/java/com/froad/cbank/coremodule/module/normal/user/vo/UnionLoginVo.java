package com.froad.cbank.coremodule.module.normal.user.vo;


/**
 * @Description: TODO
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年9月18日 下午4:52:47
 */
public class UnionLoginVo {

	/**
	 * 用户在银行的唯一Id
	 */
	private String UserBankId;
	/**
	 * 身份证号码
	 */
	private String identifyNo;
	/**
	 * 银行机构号
	 */
	private String bankOrgNo;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 登录渠道
	 */
	private String createChannel;
	/**
	 * 登录时间
	 */
	private String loginTime;
	/**
	 * 证件类型
	 */
	private String identifyType;

	
	public String getUserBankId() {
		return UserBankId;
	}
	public void setUserBankId(String userBankId) {
		UserBankId = userBankId;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getBankOrgNo() {
		return bankOrgNo;
	}
	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCreateChannel() {
		return createChannel;
	}
	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	
}
