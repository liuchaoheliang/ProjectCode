package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 运营商订单 详情返回页面
 * @author Administrator
 *
 */
public class BusinessOrderInfoRes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// -----------支付信息------------------
	/** 订单编号 */
	private String orderId;
	/**子订单编号*/
	private String subOrderId; 
	/** 创建时间 */
	private String createTime;
	/** 用户编号 */
	private long memberCode;
	/** 用户姓名 */
	private String memberName;
	/** 所属客户端 */
	private String clientId;
	/** 客户端名称 */
	private String clientName;
	/** 订单来源 */
	private String createResource;
	/** 支付方式 */
	private String paymentMethod;
	/** 订单金额 */
	private double totalPrice;
	/** 现金 */
	private double realPrice;
	/** 联盟积分 */
	private double fftPoints;
	/** 银行积分 */
	private double bankPoints;
	/** 订单状态 */
	private String orderStatus;
	/** 支付时间 */
	private String paymentTime;
	/** 支付账单号 */
	private String billNo;
	/**银行积分兑换比例*/
	private String poinRate;

	// ------------------物流信息--------------------
	/** 发货状态  0：在途;1：揽件;2：疑难;3：签收;4：退签;5：派件;6：退回*/
	private String status;
	/** 物流公司 */
	private String shippingCorp;
	/** 发货日期 */
	private String shippingTime;
	/** 物流单号 */
	private String shippingId;
	/** 物流进度信息 */
	ArrayList<HashMap<String, Object>> msgList;
	
	// --------------交易概要信息-------------------------
	List<BusinessOrderTradeRes> trades;

	// -----------------退款信息---------------------
	
	List<BusinessOrderRefundRes> refunds;
		
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCreateResource() {
		return createResource;
	}
	public void setCreateResource(String createResource) {
		this.createResource = createResource;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	public double getFftPoints() {
		return fftPoints;
	}
	public void setFftPoints(double fftPoints) {
		this.fftPoints = fftPoints;
	}
	public double getBankPoints() {
		return bankPoints;
	}
	public void setBankPoints(double bankPoints) {
		this.bankPoints = bankPoints;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getShippingCorp() {
		return shippingCorp;
	}
	public void setShippingCorp(String shippingCorp) {
		this.shippingCorp = shippingCorp;
	}
	public String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getShippingId() {
		return shippingId;
	}
	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}
	public ArrayList<HashMap<String, Object>> getMsgList() {
		return msgList;
	}
	public void setMsgList(ArrayList<HashMap<String, Object>> msgList) {
		this.msgList = msgList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getPoinRate() {
		return poinRate;
	}
	public void setPoinRate(String poinRate) {
		this.poinRate = poinRate;
	}
	public List<BusinessOrderTradeRes> getTrades() {
		return trades;
	}
	public void setTrades(List<BusinessOrderTradeRes> trades) {
		this.trades = trades;
	}
	public List<BusinessOrderRefundRes> getRefunds() {
		return refunds;
	}
	public void setRefunds(List<BusinessOrderRefundRes> refunds) {
		this.refunds = refunds;
	}	
	
}
