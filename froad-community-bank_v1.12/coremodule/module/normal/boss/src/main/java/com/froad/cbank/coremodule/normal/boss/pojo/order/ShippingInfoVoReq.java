package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

public class ShippingInfoVoReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 子订单编号 */
	private String subOrderId;
	/** 物流公司编号 */
	private String shippingCorpCode;
	/** 快递单号*/
	private String shippingId;
	/** 上传时间 */
	private String inputTime;
	/**收货地址*/
	private String recvAddress;
	
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getShippingCorpCode() {
		return shippingCorpCode;
	}
	public void setShippingCorpCode(String shippingCorpCode) {
		this.shippingCorpCode = shippingCorpCode;
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
	public String getRecvAddress() {
		return recvAddress;
	}
	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}
	

}
