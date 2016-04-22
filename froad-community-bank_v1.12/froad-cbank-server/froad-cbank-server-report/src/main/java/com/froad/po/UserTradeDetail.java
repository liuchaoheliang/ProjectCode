package com.froad.po;

import java.util.Date;

public class UserTradeDetail {
	
	private String orgCode;					//机构号
	private String orgName;					//机构名称
	private String userName;				//用户名称
	private String mobile;					//注册手机号
	private Date regTime;					//注册日期
	private String regType;					//注册类型
	private Integer totalOrderNumber;		//订单数
	private Double totalOrderAmount;		//订单金额
	private Integer totalPointNumber;		//积分支付笔数
	private Double totalPointAmount;		//积分支付金额
	private Integer totalQuickNumber;		//快捷支付笔数
	private Double totalQuickAmount;		//快捷支付金额
	private Integer totalFilmNumber;		//贴膜卡支付笔数
	private Double totalFilmAmount;			//贴膜卡支付金额
	private Integer totalPointFilmNumber;	//积分+贴膜卡支付笔数
	private Double totalPointFilmAmount;	//积分+贴膜卡支付金额
	private Integer totalPointQuickNumber;	//积分+快捷支付笔数
	private Double totalPointQuickAmount;	//积分+快捷支付金额
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public Integer getTotalOrderNumber() {
		return totalOrderNumber;
	}
	public void setTotalOrderNumber(Integer totalOrderNumber) {
		this.totalOrderNumber = totalOrderNumber;
	}
	public Double getTotalOrderAmount() {
		return totalOrderAmount;
	}
	public void setTotalOrderAmount(Double totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}
	public Integer getTotalPointNumber() {
		return totalPointNumber;
	}
	public void setTotalPointNumber(Integer totalPointNumber) {
		this.totalPointNumber = totalPointNumber;
	}
	public Double getTotalPointAmount() {
		return totalPointAmount;
	}
	public void setTotalPointAmount(Double totalPointAmount) {
		this.totalPointAmount = totalPointAmount;
	}
	public Integer getTotalQuickNumber() {
		return totalQuickNumber;
	}
	public void setTotalQuickNumber(Integer totalQuickNumber) {
		this.totalQuickNumber = totalQuickNumber;
	}
	public Double getTotalQuickAmount() {
		return totalQuickAmount;
	}
	public void setTotalQuickAmount(Double totalQuickAmount) {
		this.totalQuickAmount = totalQuickAmount;
	}
	public Integer getTotalFilmNumber() {
		return totalFilmNumber;
	}
	public void setTotalFilmNumber(Integer totalFilmNumber) {
		this.totalFilmNumber = totalFilmNumber;
	}
	public Double getTotalFilmAmount() {
		return totalFilmAmount;
	}
	public void setTotalFilmAmount(Double totalFilmAmount) {
		this.totalFilmAmount = totalFilmAmount;
	}
	public Integer getTotalPointFilmNumber() {
		return totalPointFilmNumber;
	}
	public void setTotalPointFilmNumber(Integer totalPointFilmNumber) {
		this.totalPointFilmNumber = totalPointFilmNumber;
	}
	public Double getTotalPointFilmAmount() {
		return totalPointFilmAmount;
	}
	public void setTotalPointFilmAmount(Double totalPointFilmAmount) {
		this.totalPointFilmAmount = totalPointFilmAmount;
	}
	public Integer getTotalPointQuickNumber() {
		return totalPointQuickNumber;
	}
	public void setTotalPointQuickNumber(Integer totalPointQuickNumber) {
		this.totalPointQuickNumber = totalPointQuickNumber;
	}
	public Double getTotalPointQuickAmount() {
		return totalPointQuickAmount;
	}
	public void setTotalPointQuickAmount(Double totalPointQuickAmount) {
		this.totalPointQuickAmount = totalPointQuickAmount;
	}
	
}
