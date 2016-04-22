package com.froad.thirdparty.dto.request.userengine;

import java.util.Date;

public class MemberVipInfo {
	/**
	 * 会员号
	 */
	private Long memberCode;
	/**
	 * 留用
	 */
	private String orgCode;
	/**
	 * 生效时间
	 */
	private Date vipActivationTime;
	/**
	 * 过期时间
	 */
	private Date vipExpirationTime;
	/**
	 * 省级名称
	 */
	private String provincial;
	/**
	 * 市级名称
	 */
	private String city;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 上级银行名称
	 */
	private String parentBankName;
	/**
	 * 会员标签ID（二级法人行社ID）
	 */
	private String labelID;
	/**
	 * 一级法人行社ID
	 */
	private String labelParentID;
	
	/**
	 * 0001:VIP一级、0002：VIP二级、0003：VIP三级...0006:VIP六级
	 */
	private String vipLevel;
	private String vipLevelCode;
	/**
	 * 0000：非VIP，0001正常，0002：待审核，0003：已过期
	 */
	private String vipState;
	private String vipStateCode;
	/**
	 * 0000：购买、0001：批量导入
	 */
	private String vipCreateChannel;
	private String vipCreateChannelCode;
	
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getVipActivationTime() {
		return vipActivationTime;
	}
	public void setVipActivationTime(Date vipActivationTime) {
		this.vipActivationTime = vipActivationTime;
	}
	public Date getVipExpirationTime() {
		return vipExpirationTime;
	}
	public void setVipExpirationTime(Date vipExpirationTime) {
		this.vipExpirationTime = vipExpirationTime;
	}
	public String getProvincial() {
		return provincial;
	}
	public void setProvincial(String provincial) {
		this.provincial = provincial;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getParentBankName() {
		return parentBankName;
	}
	public void setParentBankName(String parentBankName) {
		this.parentBankName = parentBankName;
	}
	public String getLabelID() {
		return labelID;
	}
	public void setLabelID(String labelID) {
		this.labelID = labelID;
	}
	public String getLabelParentID() {
		return labelParentID;
	}
	public void setLabelParentID(String labelParentID) {
		this.labelParentID = labelParentID;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getVipLevelCode() {
		return vipLevelCode;
	}
	public void setVipLevelCode(String vipLevelCode) {
		this.vipLevelCode = vipLevelCode;
	}
	public String getVipState() {
		return vipState;
	}
	public void setVipState(String vipState) {
		this.vipState = vipState;
	}
	public String getVipStateCode() {
		return vipStateCode;
	}
	public void setVipStateCode(String vipStateCode) {
		this.vipStateCode = vipStateCode;
	}
	public String getVipCreateChannel() {
		return vipCreateChannel;
	}
	public void setVipCreateChannel(String vipCreateChannel) {
		this.vipCreateChannel = vipCreateChannel;
	}
	public String getVipCreateChannelCode() {
		return vipCreateChannelCode;
	}
	public void setVipCreateChannelCode(String vipCreateChannelCode) {
		this.vipCreateChannelCode = vipCreateChannelCode;
	}
	
}
