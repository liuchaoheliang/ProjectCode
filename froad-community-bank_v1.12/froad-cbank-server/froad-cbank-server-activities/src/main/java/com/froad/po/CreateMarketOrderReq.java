package com.froad.po;

import java.io.Serializable;
import java.util.List;


public class CreateMarketOrderReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /** 请求id */
	private String reqId;
    /** 客户端id */
	private String clientId;
    /** 用户编号 */
	private Long memberCode;
    /** 用户名称 */
	private String memberName;
    /**电话号码 */
	private String phone;
    /** 订单id */
	private String orderId;
    /** 订单原始金额 */
	private Double orderOriMoney;
    /** 订单成交金额 */
	private Double orderMoney;
    /** 子订单 - 列表 */
	private List<MarketSubOrder> marketSubOrderList;
	/** 代金券(红包) - 列表 */
	private List<String> vouchersIds;
	
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
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getOrderOriMoney() {
		return orderOriMoney;
	}
	public void setOrderOriMoney(Double orderOriMoney) {
		this.orderOriMoney = orderOriMoney;
	}
	public Double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public List<MarketSubOrder> getMarketSubOrderList() {
		return marketSubOrderList;
	}
	public void setMarketSubOrderList(List<MarketSubOrder> marketSubOrderList) {
		this.marketSubOrderList = marketSubOrderList;
	}
	public List<String> getVouchersIds() {
		return vouchersIds;
	}
	public void setVouchersIds(List<String> vouchersIds) {
		this.vouchersIds = vouchersIds;
	}

}
