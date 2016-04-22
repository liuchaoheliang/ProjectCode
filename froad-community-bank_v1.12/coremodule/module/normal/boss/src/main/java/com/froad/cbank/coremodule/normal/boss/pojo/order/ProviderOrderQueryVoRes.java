package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

/**
 * 供应商订单 列表查询返回实体
 * 
 * @author songzichao
 */
public class ProviderOrderQueryVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 订单编号 */
	private String orderId;
	/** 用户编号 */
	private long memberCode;
	/** 用户手机号 */
	private String phone;
	/** 订单价格 */
	private double totalPrice;
	/** 创建时间 */
	private String createTime;
	/** 客户端ID */
	private String clientId;
	/** 收货地址 */
	private String recvAddress;
	/** 发货状态 */
	private String shippingStatus;
	 /** 物流公司编号  */
	private String shippingCorpCode; 
	/** 物流公司 */
	private String shippingCorp;
	/** 快递单号 */
	private String shippingId;
	/** 上传时间 */
	private String inputTime;
	/** 订单状态 **/
	private String orderStatus;
	/** 客户端名称*/
	private String clientName;
	 /**子订单编号*/
	private String subOrderId;
	 /**
	   * 收货人姓名 *
	   */
	private String consignee; 
	  /**
	   * 商品信息 *
	   */
	  private String productInfo;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getInputTime() {
		return inputTime;
	}

	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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
