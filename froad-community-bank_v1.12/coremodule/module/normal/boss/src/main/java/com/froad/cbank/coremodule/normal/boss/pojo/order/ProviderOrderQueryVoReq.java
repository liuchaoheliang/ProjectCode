package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;
import java.math.BigInteger;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 供应商订单 列表请求实体
 * 
 * @author songzichao
 */
public class ProviderOrderQueryVoReq extends Page implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 订单编号*/
	private String orderId;
	/** 用户编号*/
	private BigInteger memberCode;
	/** 客户端ID*/
	private String clientId;
	/** 发货状态*/
	private String shippingStatus;
	/** 用户手机号*/
	private String phone;
	/** 订单创建时间*/
	private String begTime;
	/** 订单结束时间*/
	private String endTime;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigInteger getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(BigInteger memberCode) {
		this.memberCode = memberCode;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBegTime() {
		return begTime;
	}
	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
}
