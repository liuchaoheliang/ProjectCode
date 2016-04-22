package com.froad.fft.persistent.entity;

/**
 * 系统客户端
 * @author FQ
 *
 */
public class SysClient extends BaseEntity {
	
	private String name;//名称
	private String number;//客户端编号
	private String shortLetter;//字母简称 如（重庆|CQ）
	private Boolean isEnabled;//是否启用
	private String area;//客户端所属地
	private String remark;//备注
	private String orgNo;//积分机构号
	private String partnerId;//合作伙伴ID
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getShortLetter() {
		return shortLetter;
	}
	public void setShortLetter(String shortLetter) {
		this.shortLetter = shortLetter;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	
}
