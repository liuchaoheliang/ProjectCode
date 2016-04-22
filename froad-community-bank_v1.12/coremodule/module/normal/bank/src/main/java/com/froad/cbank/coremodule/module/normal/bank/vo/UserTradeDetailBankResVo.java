package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class UserTradeDetailBankResVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 机构号 */
	private String orgCode;
	/** 机构名称 */
	private String orgName;
	/** 客户姓名 */
	private String userName;
	/** 注册手机号 */
	private String mobile;
	/** 注册日期 */
	private Long regTime;
	/** 注册类型 */
	private String regType;
	/** 积分支付笔数 */
	private Integer totalPointNumber;
	/** 积分支付金额 */
	private Double totalPointAmount;
	/** 快捷支付笔数 */
	private Integer totalQuickNumber;
	/** 快捷支付金额 */
	private Double totalQuickAmount;
	/** 贴膜卡支付笔数 */
	private Integer totalFilmNumber;
	/** 贴膜卡支付金额 */
	private Double totalFilmAmount;
	/** 积分+快捷支付笔数 */
	private Integer totalPointQuickNumber;
	/** 积分+快捷支付金额 */
	private Double totalPointQuickAmount;
	/** 积分+贴膜卡支付笔数 */
	private Integer totalPointFilmNumber;
	/** 积分+贴膜卡支付金额 */
	private Double totalPointFilmAmount;
	/** 订单数 */
	private Integer orderCount;
	/** 订单金额 */
	private Double totalAmount;

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

	public Long getRegTime() {
		return regTime;
	}

	public void setRegTime(Long regTime) {
		this.regTime = regTime;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
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

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
