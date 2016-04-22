package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class ReturnMarketOrderRes implements Serializable {

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
	private List<ReturnSubOrderRes> returnSubOrderResList;
     /** 响应结果 */
	private Result result;
	
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
	public List<ReturnSubOrderRes> getReturnSubOrderResList() {
		return returnSubOrderResList;
	}
	public void setReturnSubOrderResList(
			List<ReturnSubOrderRes> returnSubOrderResList) {
		this.returnSubOrderResList = returnSubOrderResList;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
