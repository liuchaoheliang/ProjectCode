package com.froad.po;

import java.sql.Timestamp;
import java.util.Date;

public class PresellShip implements java.io.Serializable {

	/**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -3430753746515977410L;
	
	private Long id;
	private String clientId;
	private Date createTime;
	private String orderId;
	private String subOrderId;
	private Date orderTime;
	private String receiveName;
	private String phone;
	private String receiveAddress;
	private String fOrg;
	private String fOrgName;
	private String sOrg;
	private String sOrgName;
	private String productId;
	private String productName;
	private Date presellStartTime;
	private Date presellEndTime;
	private Date startTime;
	private Date endTime;
	private int buyNumber;
	private int refundNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getfOrg() {
		return fOrg;
	}
	public void setfOrg(String fOrg) {
		this.fOrg = fOrg;
	}
	public String getsOrg() {
		return sOrg;
	}
	public void setsOrg(String sOrg) {
		this.sOrg = sOrg;
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
	public Date getPresellStartTime() {
		return presellStartTime;
	}
	public void setPresellStartTime(Date presellStartTime) {
		this.presellStartTime = presellStartTime;
	}
	public Date getPresellEndTime() {
		return presellEndTime;
	}
	public void setPresellEndTime(Date presellEndTime) {
		this.presellEndTime = presellEndTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}
	public int getRefundNumber() {
		return refundNumber;
	}
	public void setRefundNumber(int refundNumber) {
		this.refundNumber = refundNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getfOrgName() {
		return fOrgName;
	}
	public void setfOrgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}
	public String getsOrgName() {
		return sOrgName;
	}
	public void setsOrgName(String sOrgName) {
		this.sOrgName = sOrgName;
	}
	
	
	
	
	
}

