package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class ReturnMarketOrderBackReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 请求id */
	private String reqId;
    /** 客户端id */
	private String clientId;
    /** 订单id */
	private String orderId;
    /** 退款子订单 - 列表 */
	private List<ReturnSubOrderBackReq> returnSubOrderBackReqList;
	/** 退款账单号 */
	private String returnBillNo;
	/** 退款金额 */
	private Double returnMoney;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
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
	public List<ReturnSubOrderBackReq> getReturnSubOrderBackReqList() {
		return returnSubOrderBackReqList;
	}
	public void setReturnSubOrderBackReqList(
			List<ReturnSubOrderBackReq> returnSubOrderBackReqList) {
		this.returnSubOrderBackReqList = returnSubOrderBackReqList;
	}
	public String getReturnBillNo() {
		return returnBillNo;
	}
	public void setReturnBillNo(String returnBillNo) {
		this.returnBillNo = returnBillNo;
	}
	public Double getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}
	
	

}
