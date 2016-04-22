package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * 订单校验 请求
 */
public class CheckOrderReq implements Serializable {

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
	/** 订单总金额 */
	private Double orderMoney;
    /** 订单商品 - 列表 */
	private List<OrderProduct> orderProductList;
	/** 代金券(红包) - 列表 */
	private List<String> vouchersIds;
    /** 订单中参与的营销活动id - 列表 */
	private List<String> sustainActiveIds;
	/** 订单中包含的代金券(红包)所属活动id - 列表 */
	private List<String> vouchersActiveIds;
	/** 是否面对面订单 */
	private Boolean isF2FOrder;
	
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
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	public Double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public List<String> getVouchersIds() {
		return vouchersIds;
	}
	public void setVouchersIds(List<String> vouchersIds) {
		this.vouchersIds = vouchersIds;
	}
	public List<String> getSustainActiveIds() {
		return sustainActiveIds;
	}
	public void setSustainActiveIds(List<String> sustainActiveIds) {
		this.sustainActiveIds = sustainActiveIds;
	}
	public List<String> getVouchersActiveIds() {
		return vouchersActiveIds;
	}
	public void setVouchersActiveIds(List<String> vouchersActiveIds) {
		this.vouchersActiveIds = vouchersActiveIds;
	}
	public Boolean getIsF2FOrder() {
		return isF2FOrder;
	}
	public void setIsF2FOrder(Boolean isF2FOrder) {
		this.isF2FOrder = isF2FOrder;
	}
}
