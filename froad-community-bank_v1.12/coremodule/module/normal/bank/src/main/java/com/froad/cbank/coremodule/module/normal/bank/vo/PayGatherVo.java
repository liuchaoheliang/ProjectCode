package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 交易汇总
 * @author yfy
 *
 */
public class PayGatherVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String pratenOrgCode;  //支行机构
	private String orgCode;        //网点机构
	private String merchantName;   //商户名称
	private Integer productType;   //商品类型
	private String payType;        //支付方式
	private String totalMoney;     //总金额
	private String orderNumber;    //总订单数量
	
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}
