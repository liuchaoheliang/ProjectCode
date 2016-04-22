package com.froad.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UpdateMarketOrderReq implements Serializable {

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
    /** 订单id */
	private String orderId;
    /** 营销活动订单id */
	private String marketId;
    /** 状态 */
	private Boolean status;
    /**支付时间*/
	private Date payTime;
	/** 代金券/红包id 列表 */
    private List<String> vouchersIdList;
    /** 是否面对面订单 */
    private Boolean isF2FOrder;
    /** 支付账单号 */
    private String payBillNo;    
    /** 满赠活动id - 列表 */
    private List<String> fullGiveActiveIds;
    /** 会员账号(联盟积分需要传此参数) */
    private String loginId;
    
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public List<String> getVouchersIdList() {
		return vouchersIdList;
	}
	public void setVouchersIdList(List<String> vouchersIdList) {
		this.vouchersIdList = vouchersIdList;
	}
	public Boolean getIsF2FOrder() {
		return isF2FOrder;
	}
	public void setIsF2FOrder(Boolean isF2FOrder) {
		this.isF2FOrder = isF2FOrder;
	}
	public String getPayBillNo() {
		return payBillNo;
	}
	public void setPayBillNo(String payBillNo) {
		this.payBillNo = payBillNo;
	}
	public List<String> getFullGiveActiveIds() {
		return fullGiveActiveIds;
	}
	public void setFullGiveActiveIds(List<String> fullGiveActiveIds) {
		this.fullGiveActiveIds = fullGiveActiveIds;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


}
