package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;

import java.util.List;

/**
 * 多银行接入详情实体类
 * @author yfy
 * @date: 2015年9月18日 上午11:13:23
 */
public class BankAccessVoRes {
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端
	 */
	private String clientName;
	/**
	 * 功能模块
	 */
	private List<FunctionVoReq> functionList;
	/**
	 * 支付方式
	 */
	private List<PaymentMethodVoReq> paymentList;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public List<FunctionVoReq> getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List<FunctionVoReq> functionList) {
		this.functionList = functionList;
	}
	public List<PaymentMethodVoReq> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<PaymentMethodVoReq> paymentList) {
		this.paymentList = paymentList;
	}
	
}
