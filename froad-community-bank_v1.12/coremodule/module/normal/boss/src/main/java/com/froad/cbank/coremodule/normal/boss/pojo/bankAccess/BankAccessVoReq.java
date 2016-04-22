package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 新增或修改实体类
 * @author yfy
 * @date: 2015年9月18日 下午09:23:12
 */
public class BankAccessVoReq {
	
	/**
	 * 客户端
	 */
	@NotEmpty(value="请选择接入的银行客户端")
	private String clientId;
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
