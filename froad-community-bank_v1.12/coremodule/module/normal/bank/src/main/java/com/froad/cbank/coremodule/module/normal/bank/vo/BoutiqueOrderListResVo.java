/**
 * Project Name:coremodule-bank
 * File Name:BoutiqueOrderListResVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年12月15日下午3:18:12
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:BoutiqueOrderListResVo Reason: 下午3:18:12
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class BoutiqueOrderListResVo implements Serializable {

	private static final long serialVersionUID = 2371190660078787192L;
	private String subOrderId;// 订单编号
	private String paymentMethod;// 支付方式
	private String orderStatus;// 订单状态
	private String providerName;// 供应商名称
	private List<String> productNames;// 商品名称
	private double subTotalMoney;// 金额
	private long createTime;// 下单时间

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public List<String> getProductNames() {
		return productNames;
	}

	public void setProductNames(List<String> productNames) {
		this.productNames = productNames;
	}

	public double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
