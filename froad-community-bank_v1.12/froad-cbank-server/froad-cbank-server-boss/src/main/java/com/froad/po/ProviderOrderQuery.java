/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProvideOrderQuery.java
 * Package Name:com.froad.po
 * Date:2015年11月25日下午3:20:07
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:ProvideOrderQuery
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月25日 下午3:20:07
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderOrderQuery implements Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	
	// 订单编号
	private String orderId;
	
	// 订单状态
	private String orderStatus;
	
	// 会员编号
	private Long memberCode;
	
	// 会员手机号
	private String phone;
	
	// 订单总金额
	private Double totalPrice;
	
	// 订单创建时间
	private Date createTime;
	
	// 所属客户端Id
	private String clientId;
	
	// 所属客户端名称
	private String clientName;
	
	// 收货地址
	private String recvAddress;
	
	// 收货状态
	private String shippingStatus;
	
	// 物流公司编号
	private String shippingCorpCode;
	
	// 物流公司
	private String shippingCorp;
	
	// 物流编号
	private String shippingId;
	
	// 上传时间
	private Date inputTime;

	// 子订单编号
	private String subOrderId;
	
	// 收货人姓名
	private String consignee;
	
	// 商品信息
	private String productInfo;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public String getRecvAddress() {
		return recvAddress;
	}

	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	
	public String getShippingCorpCode() {
		return shippingCorpCode;
	}

	public void setShippingCorpCode(String shippingCorpCode) {
		this.shippingCorpCode = shippingCorpCode;
	}

	public String getShippingCorp() {
		return shippingCorp;
	}

	public void setShippingCorp(String shippingCorp) {
		this.shippingCorp = shippingCorp;
	}

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	
}
