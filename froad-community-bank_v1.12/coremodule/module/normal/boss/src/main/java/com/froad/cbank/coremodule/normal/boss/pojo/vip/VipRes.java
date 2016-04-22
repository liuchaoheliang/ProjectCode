package com.froad.cbank.coremodule.normal.boss.pojo.vip;

/**
 * 会员明细响应对象
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年7月10日 上午9:53:12
 */
public class VipRes {
	private Integer availableDays;//有效时间
	private String bankLabelId;//机构ID
	private String bankLabelName;//机构名称
	private String bankOrgNo;//机构号
	private String clientChannel;//开通方式
	private String lastLoginIp;//最后登录IP
	private Long lastLoginTime;//最后登录时间
	private String loginId;//用户名
	private Long memberCode;//会员ID
	private String memberCreateChannel;//创建渠道
	private String mobile;//手机号
	private Long registerTime;//注册日期
	private Integer status;//激活状态（1-已激活、2-未激活、3-已冻结）
	private Long vipActivationTime;//开通日期
	private Long vipExpirationTime;//到期日期
	private String vipLevel;//VIP等级
	private String vipStatus;//VIP是否过期状态
	
	public Integer getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}
	public String getBankLabelId() {
		return bankLabelId;
	}
	public void setBankLabelId(String bankLabelId) {
		this.bankLabelId = bankLabelId;
	}
	public String getBankLabelName() {
		return bankLabelName;
	}
	public void setBankLabelName(String bankLabelName) {
		this.bankLabelName = bankLabelName;
	}
	public String getBankOrgNo() {
		return bankOrgNo;
	}
	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}
	public String getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberCreateChannel() {
		return memberCreateChannel;
	}
	public void setMemberCreateChannel(String memberCreateChannel) {
		this.memberCreateChannel = memberCreateChannel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getVipActivationTime() {
		return vipActivationTime;
	}
	public void setVipActivationTime(Long vipActivationTime) {
		this.vipActivationTime = vipActivationTime;
	}
	public Long getVipExpirationTime() {
		return vipExpirationTime;
	}
	public void setVipExpirationTime(Long vipExpirationTime) {
		this.vipExpirationTime = vipExpirationTime;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getVipStatus() {
		return vipStatus;
	}
	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}
}
