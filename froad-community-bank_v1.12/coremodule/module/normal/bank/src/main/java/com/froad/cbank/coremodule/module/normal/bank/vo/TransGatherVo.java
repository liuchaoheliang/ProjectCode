package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class TransGatherVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String pratenOrgCode;  //支行机构
	private String orgCode;        //网点机构
	private String startDate;      //开始日期
	private String endDate;        //结束日期
	private String merchantName;   //商户名称
	private String productName;    //商品名称
	private Integer productType;   //业务类型
	private String number;    	   //数量
	private String totalMoney;     //总金额
	private String cashPay;        //现金支付
	private String integralPay;    //积分支付
	
	public String getPratenOrgCode() {
		return pratenOrgCode;
	}
	public void setPratenOrgCode(String pratenOrgCode) {
		this.pratenOrgCode = pratenOrgCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getCashPay() {
		return cashPay;
	}
	public void setCashPay(String cashPay) {
		this.cashPay = cashPay;
	}
	public String getIntegralPay() {
		return integralPay;
	}
	public void setIntegralPay(String integralPay) {
		this.integralPay = integralPay;
	}
	
}
