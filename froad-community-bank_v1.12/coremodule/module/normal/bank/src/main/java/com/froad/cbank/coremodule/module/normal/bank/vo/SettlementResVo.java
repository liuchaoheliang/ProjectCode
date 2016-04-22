/**
 * Project Name:coremodule-bank
 * File Name:SettlementReqVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年8月17日下午1:14:21
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:SettlementReqVo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年8月17日 下午1:14:21
 * @author   明灿
 * @version  
 * @see 	 
 */
public class SettlementResVo  implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2L;

	public String id; // 主键id
	public String settlementId; // 结算id
	public String createTime; // 创建时间
	public String clientId; // 客户端id
	public String orderId; // 订单id
	public String subOrderId; // 子订单编号
	public String merchantId; // 商户id
	public String outletId; // 门店id
	public int type; // 结算类型
	public String paymentId; // 支付id
	public String settleState; // 结算状态(0-未结算,1-结算中,2-结算成功,3-结算失败,4-无效结算记录)
	public double money; // 结算金额
	public String remark; // 备注
	public String productName; // 商品名称
	public String productId; // 商品id
	public int productCount; // 消费数量
	public String ticket; // 券码
	public String merchantName; // 商户名称
	public String outletName; // 门店名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getSettleState() {
		return settleState;
	}

	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

}
