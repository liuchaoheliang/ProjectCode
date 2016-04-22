package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

/**
 * 会员查询条件实体
 * @author: yfy
 */
public class MemberVoRes implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private Long   memberCode;		 //会员号
	private String loginID;          //登录ID（用户名）
	private String uname;			 //用户姓名
	private String mobile;		     //电话号码
	private String bankOrgNo;		 //银行标识
	private String clientChannelName;//客户端
	private Integer status;			 //用户活动状态1正常状态(已激活)，2.未激活，3已冻结
	private String payMode;          //支付方式
	private String cardNo;           //绑定银行卡号
	private String vipStatus;        //是否vip
	private String vipExpirationTime;//vip有效期
	private String createTime;       //注册时间
	private String lastLoginTime;    //最近登录时间
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBankOrgNo() {
		return bankOrgNo;
	}
	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}
	public String getClientChannelName() {
		return clientChannelName;
	}
	public void setClientChannelName(String clientChannelName) {
		this.clientChannelName = clientChannelName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getVipStatus() {
		return vipStatus;
	}
	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}
	public String getVipExpirationTime() {
		return vipExpirationTime;
	}
	public void setVipExpirationTime(String vipExpirationTime) {
		this.vipExpirationTime = vipExpirationTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
