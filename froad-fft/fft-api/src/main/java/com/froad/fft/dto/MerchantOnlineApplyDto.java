package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.MerchantType;

/**
 * 商户在线申请
 * @author FQ
 *
 */
public class MerchantOnlineApplyDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String companyName; //公司名称
	private String linkman;		//联系人
	private String mobile;		//手机
	private String phone;		//座机
	private String address;		//地址
	private Long areaId;//所在地区
	private MerchantType cooperationType;  //意向合作模式 
	private String cooperativePurpose;//合作意向
	private Boolean isRelation;		  //是否已联系
	private Long relationAccount;	  //联系处理人
	private Date relationTime;//联系处理时间
	private String relationMark;//联系处理备注
	
	private Long clientId;//所属客户端
	
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public MerchantType getCooperationType() {
		return cooperationType;
	}

	public void setCooperationType(MerchantType cooperationType) {
		this.cooperationType = cooperationType;
	}

	public String getCooperativePurpose() {
		return cooperativePurpose;
	}

	public void setCooperativePurpose(String cooperativePurpose) {
		this.cooperativePurpose = cooperativePurpose;
	}

	public Boolean getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(Boolean isRelation) {
		this.isRelation = isRelation;
	}

	public Long getRelationAccount() {
		return relationAccount;
	}

	public void setRelationAccount(Long relationAccount) {
		this.relationAccount = relationAccount;
	}

	public Date getRelationTime() {
		return relationTime;
	}

	public void setRelationTime(Date relationTime) {
		this.relationTime = relationTime;
	}

	public String getRelationMark() {
		return relationMark;
	}

	public void setRelationMark(String relationMark) {
		this.relationMark = relationMark;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	
}
