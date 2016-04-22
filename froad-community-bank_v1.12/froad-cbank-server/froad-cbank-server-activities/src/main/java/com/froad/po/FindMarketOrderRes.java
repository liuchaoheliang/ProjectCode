package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class FindMarketOrderRes implements Serializable {

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
    /** 查询营销活动子订单 - 列表 */
	private List<FindMarketSubOrder> findMarketSubOrderList;
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
	public List<FindMarketSubOrder> getFindMarketSubOrderList() {
		return findMarketSubOrderList;
	}
	public void setFindMarketSubOrderList(
			List<FindMarketSubOrder> findMarketSubOrderList) {
		this.findMarketSubOrderList = findMarketSubOrderList;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
}
