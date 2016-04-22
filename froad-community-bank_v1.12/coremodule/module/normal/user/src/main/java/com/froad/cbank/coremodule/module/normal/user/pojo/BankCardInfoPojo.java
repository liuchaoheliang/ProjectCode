package com.froad.cbank.coremodule.module.normal.user.pojo;

public class BankCardInfoPojo {

	public long id;
	public long memberCode;
	public String bankGroupId;
	public String bankName;
	public String cardHostName;
	public String cardNo;
	public String cardType;
	public String mobile;
	public String identifyType;
	public String identifyNo;
	public String pointCardNo;
	public int validStatus;
	public boolean isDefault;
	public String type;

	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	public String getBankGroupId() {
		return bankGroupId;
	}
	public void setBankGroupId(String bankGroupId) {
		this.bankGroupId = bankGroupId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardHostName() {
		return cardHostName;
	}
	public void setCardHostName(String cardHostName) {
		this.cardHostName = cardHostName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public int getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(int validStatus) {
		this.validStatus = validStatus;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPointCardNo() {
		return pointCardNo;
	}
	public void setPointCardNo(String pointCardNo) {
		this.pointCardNo = pointCardNo;
	}
	
}
