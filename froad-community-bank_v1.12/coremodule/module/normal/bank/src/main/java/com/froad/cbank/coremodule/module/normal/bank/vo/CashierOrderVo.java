package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 面对面订单
 * @author ylchu
 *
 */
public class CashierOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300209691582751843L;

	private String orderId;	//订单编号
	private String subOrderId;	//子订单号
	private String payType;	//支付方式
	private String transState;	//交易状态
	private String money;	//订单金额
	private String integral;	//积分
	private String cash;	//现金
	private String clientName;	//客户端
	private String createTime;	//创建时间
	private String orderState;	//订单状态
	private String businessType;	//业务类型
	private String merchantOutletName;	//商户门店名称
	private String deduction;	//积分抵扣
	private String sumMoney;	//总金额
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTransState() {
		return transState;
	}
	public void setTransState(String transState) {
		this.transState = transState;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getMerchantOutletName() {
		return merchantOutletName;
	}
	public void setMerchantOutletName(String merchantOutletName) {
		this.merchantOutletName = merchantOutletName;
	}
	public String getDeduction() {
		return deduction;
	}
	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}
	public String getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}
	
}
