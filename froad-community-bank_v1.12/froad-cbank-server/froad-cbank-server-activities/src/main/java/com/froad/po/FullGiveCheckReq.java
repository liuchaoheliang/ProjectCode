package com.froad.po;

import java.io.Serializable;
import java.util.List;

public class FullGiveCheckReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 请求id */
    private String reqId;
    /** 客户端id */
    private String clientId;
    /** 客户号 **/
    private Long memberCode;
    /** 满赠活动id - 列表 */
    private List<String> fullGiveActiveIds;
    /** 订单商品 - 列表 */
    private List<OrderProduct> orderProductList;
    
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
	public List<String> getFullGiveActiveIds() {
		return fullGiveActiveIds;
	}
	public void setFullGiveActiveIds(List<String> fullGiveActiveIds) {
		this.fullGiveActiveIds = fullGiveActiveIds;
	}
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
}
