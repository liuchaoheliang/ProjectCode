package com.froad.cbank.coremodule.normal.boss.pojo.settle;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * 类描述:结算vo
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年8月5日 下午4:20:21
 * @email "chenzhangwei@f-road.com.cn"
 */
public class SettlementVoReq extends Page {	
	private String id;
	private String orderId; // optional 订单编号
	private String clientId; // optional 要查询的银行的id
	private List<String> merchantIds; // optional 商户的id集合
	private String merchantName; //商户名称
	private List<String> outletIds; // optional  门店id集合 
	private String outletName;// 门店名称
	private String settleState; // optional 结算状态:全部、未结算、结算中、已计算、结算失败
	private String remark;
	private String billNo;//账单编号
	private String token;
	private String userId;
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String productName;//商品名称
	
	private String orgCodes;
	
	public String getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List<String> getMerchantIds() {
		return merchantIds;
	}
	public void setMerchantIds(List<String> merchantIds) {
		this.merchantIds = merchantIds;
	}
	public List<String> getOutletIds() {
		return outletIds;
	}
	public void setOutletIds(List<String> outletIds) {
		this.outletIds = outletIds;
	}
	public String getSettleState() {
		return settleState;
	}
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
