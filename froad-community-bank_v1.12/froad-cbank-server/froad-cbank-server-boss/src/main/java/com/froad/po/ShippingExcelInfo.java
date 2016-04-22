/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ShippingExcelInfo.java
 * Package Name:com.froad.po
 * Date:2015年11月27日上午11:46:31
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;


/**
 * ClassName:ShippingExcelInfo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 上午11:46:31
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ShippingExcelInfo {
	
	/**
	 * 订单编号 *
	 */
	private String orderId;
	
	/**
	 * 订单状态*
	 */
	private String orderStatus;
	
	/**
	 * 用户编号 *
	 */
	private String memberCode;
	
	/**
	 * 用户手机号 *
	 */
	private String phone;
	
	/**
	 * 用户手机号 *
	 */
	private String totalPrice;
	
	/**
	 * 创建时间 *
	 */
	private String createTime;
	
	/**
	 * 客户端名称 *
	 */
	private String clientName;
	
	/**
	 * 收货地址 *
	 */
	private String recvAddress;
	
	/**
	 * 发货状态描述 *
	 */
	private String shippingStatusDesc;
	
	/**
	 * 物流公司名称 *
	 */
	private String shippingCorp;
	
	/**
	 * 物流单号 *
	 */
	private String shippingId;
	
	/**
	 * 上传时间 *
	 */
	private String inputTime;
	
	/**
	 * 收货人姓名
	 */
	private String consignee;

	/**
	 * 商品信息
	 */
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getShippingStatusDesc() {
		return shippingStatusDesc;
	}

	public void setShippingStatusDesc(String shippingStatusDesc) {
		this.shippingStatusDesc = shippingStatusDesc;
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

	public String getInputTime() {
		return inputTime;
	}

	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
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
