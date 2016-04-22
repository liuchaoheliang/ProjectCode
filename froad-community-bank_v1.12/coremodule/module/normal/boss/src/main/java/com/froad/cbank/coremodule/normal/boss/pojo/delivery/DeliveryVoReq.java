package com.froad.cbank.coremodule.normal.boss.pojo.delivery;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class DeliveryVoReq  extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderCode;// 订单号
	private Long buyTime;// 购买时间
	private Long pickDate;// 提货期
	private String receiveType;// 提货方式
	private String branchName;// 网点名称
	private String userName;// 用户名
	private String receiver;// 收货人
	private String receiverPhone;// 收货人手机号
	private String receiveAddress;// 收货地址
	private String logisticsName;// 物流公司
	private String logisticsCode;// 物流单号
	private Integer isDelivery;// 是否配送 0:否,1:是

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Long buyTime) {
		this.buyTime = buyTime;
	}

	public Long getPickDate() {
		return pickDate;
	}

	public void setPickDate(Long pickDate) {
		this.pickDate = pickDate;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public Integer getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(Integer isDelivery) {
		this.isDelivery = isDelivery;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
