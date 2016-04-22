package com.froad.po;

import java.io.Serializable;

/**
 * 支付信息
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午4:48:50
 * @version v1.0
 */
public class PaymentInfo implements Serializable {
	
	private static final long serialVersionUID = -4533588442142781126L;

	/**
	 * 支付方式
	 */
	private Integer paymentType;
	
	/**
	 * 银行代码
	 */
	private String orgCode;
	
	/**
	 * 银行名称
	 */
	private String orgName;
	
	public PaymentInfo(){}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

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
	
	
}
