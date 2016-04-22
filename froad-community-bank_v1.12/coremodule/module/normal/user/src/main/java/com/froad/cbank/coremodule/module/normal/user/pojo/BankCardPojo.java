package com.froad.cbank.coremodule.module.normal.user.pojo;

public class BankCardPojo {
	private Long id;
	private Long memberCode;
	private String cardNo;
	private String uname;
	private String idcard;
	private String phone;
	private String singlePenLimit;
	private String dayLimit;
	private String monthLimit;
	private String token;
	private String code;
	private String pointCardNo; 
	
	public String getPointCardNo() {
		return pointCardNo;
	}
	public void setPointCardNo(String pointCardNo) {
		this.pointCardNo = pointCardNo;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSinglePenLimit() {
		return singlePenLimit;
	}
	public void setSinglePenLimit(String singlePenLimit) {
		this.singlePenLimit = singlePenLimit;
	}
	public String getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getMonthLimit() {
		return monthLimit;
	}
	public void setMonthLimit(String monthLimit) {
		this.monthLimit = monthLimit;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
