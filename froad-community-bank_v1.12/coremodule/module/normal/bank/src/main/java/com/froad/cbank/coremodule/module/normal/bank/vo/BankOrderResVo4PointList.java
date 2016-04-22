/**
 * Project Name:coremodule-bank
 * File Name:BankOrderResVo4PointList.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年9月4日上午10:01:29
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:BankOrderResVo4PointList
 * Reason:	 积分商品订单列表返回Vo
 * Date:     2015年9月4日 上午10:01:29
 * @author   明灿
 * @version  
 * @see 	 
 */

public class BankOrderResVo4PointList implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = -8512570285011671637L;

	private String orderId;// 订单编号
	private String subOrderId;// 订单编号
	private long createTime;// 下单时间
	private String orderStatus;// 订单状态
	private int buyNo;// 购买数量
	private double money;// 金额
	private double point;// 积分
	private String paymentMethod;// 支付方式
	private String merchantName;// 商户名称
	private String productId;// 商品id
	private String productName;// 商品名称
	private String outletName ; //门店名称
	
	
	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getBuyNo() {
		return buyNo;
	}

	public void setBuyNo(int buyNo) {
		this.buyNo = buyNo;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

}
