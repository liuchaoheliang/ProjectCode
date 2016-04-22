package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * 订单校验 响应
 */
public class CheckOrderRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reqId;
    /** 客户端id */
	private String clientId;
    /** 用户编号 */
	private Long memberCode;
	/** 订单成交总金额 */
	private Double orderDealMoney;
	/** Result 结果 */
	private Result result;
	/** 满赠活动响应 - 列表 */
    private List<FullGiveActive> fullGiveActiveList;
	
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
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Double getOrderDealMoney() {
		return orderDealMoney;
	}
	public void setOrderDealMoney(Double orderDealMoney) {
		this.orderDealMoney = orderDealMoney;
	}
	public List<FullGiveActive> getFullGiveActiveList() {
		return fullGiveActiveList;
	}
	public void setFullGiveActiveList(List<FullGiveActive> fullGiveActiveList) {
		this.fullGiveActiveList = fullGiveActiveList;
	}
}
