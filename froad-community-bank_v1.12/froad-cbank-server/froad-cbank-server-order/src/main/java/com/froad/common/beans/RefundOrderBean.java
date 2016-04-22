package com.froad.common.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundShoppingInfo;

public class RefundOrderBean {
	/**
	 * 退款ID
	 */
	private String refundId = null;
	
	/**
	 * 退款来源
	 */
	private String refundResource = null;
	
	/**
	 * 退款原因
	 */
	private String reason = null;
	
	/**
	 * 订单ID
	 */
	private String orderId = null;
	
	/**
	 * 订单总价(现金+积分转金额)
	 */
	private int totalPrice = 0;
	
	/**
	 * 订单总支付现金
	 */
	private int totalCash = 0;
	
	/**
	 * 订单总支付积分-方付通积分
	 */
	private int totalFftPoint = 0;
	
	/**
	 * 订单总支付积分-银行积分
	 */
	private int totalBankPoint = 0;
	
	/**
	 * 积分兑换比例
	 */
	private double pointRate = 0;
	
	/**
	 * 总赠送积分
	 */
	private int totalCreditPoint = 0;
	
	/**
	 * 会员编号
	 */
	private long memberCode = 0;
	
	/**
	 * 商户ID
	 */
	private String merchantId = null;
	
	/**
	 * 商户名
	 */
	private String merchantName = null;
	
	/**
	 * 已退现金
	 */
	private int refundedCash = 0;
	
	/**
	 * 已退积分
	 */
	private int refundedPoint = 0;
	
	/**
	 * 实际退还现金
	 */
	private int actualRefundCash = 0;
	
	/**
	 * 实际退还积分
	 */
	private int actualRefundPoint = 0;
	
	/**
	 * 退款商品
	 */
	private Map<String, RefundProductBean> productMap = null;
	
	/**
	 * 退款商品列表
	 */
	private List<RefundShoppingInfo> shoppingInfoList = null;
	
	/**
	 * 退款支付流水
	 */
	private List<RefundPaymentInfo> payInfoList = null;
	
	/**
	 * 仅查询
	 */
	private boolean queryOnly = false;
	
	/**
	 * 银行端ID
	 */
	private String clientId = null;
	
	/**
	 * 子订单类型
	 */
	private String subOrderType = null;

	/**
	 * @return the refundId
	 */
	public String getRefundId() {
		return refundId;
	}

	/**
	 * @param refundId the refundId to set
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/**
	 * @return the refundResource
	 */
	public String getRefundResource() {
		return refundResource;
	}

	/**
	 * @param refundResource the refundResource to set
	 */
	public void setRefundResource(String refundResource) {
		this.refundResource = refundResource;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the totalPrice
	 */
	public int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the totalCash
	 */
	public int getTotalCash() {
		return totalCash;
	}

	/**
	 * @param totalCash the totalCash to set
	 */
	public void setTotalCash(int totalCash) {
		this.totalCash = totalCash;
	}

	/**
	 * @return the totalFftPoint
	 */
	public int getTotalFftPoint() {
		return totalFftPoint;
	}

	/**
	 * @param totalFftPoint the totalFftPoint to set
	 */
	public void setTotalFftPoint(int totalFftPoint) {
		this.totalFftPoint = totalFftPoint;
	}

	/**
	 * @return the totalBankPoint
	 */
	public int getTotalBankPoint() {
		return totalBankPoint;
	}

	/**
	 * @param totalBankPoint the totalBankPoint to set
	 */
	public void setTotalBankPoint(int totalBankPoint) {
		this.totalBankPoint = totalBankPoint;
	}

	/**
	 * @return the pointRate
	 */
	public double getPointRate() {
		return pointRate;
	}

	/**
	 * @param pointRate the pointRate to set
	 */
	public void setPointRate(double pointRate) {
		this.pointRate = pointRate;
	}

	/**
	 * @return the totalCreditPoint
	 */
	public int getTotalCreditPoint() {
		return totalCreditPoint;
	}

	/**
	 * @param totalCreditPoint the totalCreditPoint to set
	 */
	public void setTotalCreditPoint(int totalCreditPoint) {
		this.totalCreditPoint = totalCreditPoint;
	}

	/**
	 * @return the memberCode
	 */
	public long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the refundedCash
	 */
	public int getRefundedCash() {
		return refundedCash;
	}

	/**
	 * @param refundedCash the refundedCash to set
	 */
	public void setRefundedCash(int refundedCash) {
		this.refundedCash = refundedCash;
	}

	/**
	 * @return the refundedPoint
	 */
	public int getRefundedPoint() {
		return refundedPoint;
	}

	/**
	 * @param refundedPoint the refundedPoint to set
	 */
	public void setRefundedPoint(int refundedPoint) {
		this.refundedPoint = refundedPoint;
	}

	/**
	 * @return the actualRefundCash
	 */
	public int getActualRefundCash() {
		return actualRefundCash;
	}

	/**
	 * @param actualRefundCash the actualRefundCash to set
	 */
	public void setActualRefundCash(int actualRefundCash) {
		this.actualRefundCash = actualRefundCash;
	}

	/**
	 * @return the actualRefundPoint
	 */
	public int getActualRefundPoint() {
		return actualRefundPoint;
	}

	/**
	 * @param actualRefundPoint the actualRefundPoint to set
	 */
	public void setActualRefundPoint(int actualRefundPoint) {
		this.actualRefundPoint = actualRefundPoint;
	}

	/**
	 * @return the productMap
	 */
	public Map<String, RefundProductBean> getProductMap() {
		return productMap;
	}

	/**
	 * @param productMap the productMap to set
	 */
	public void setProductMap(Map<String, RefundProductBean> productMap) {
		this.productMap = productMap;
	}
	
	/**
	 * 添加退款商品
	 */
	public void addProduct(RefundProductBean productBean){
		if (null == productMap){
			productMap = new HashMap<String, RefundProductBean>();
		}
		
		productMap.put(productBean.getProductId(), productBean);
	}

	/**
	 * @return the shoppingInfoList
	 */
	public List<RefundShoppingInfo> getShoppingInfoList() {
		return shoppingInfoList;
	}

	/**
	 * @param shoppingInfoList the shoppingInfoList to set
	 */
	public void setShoppingInfoList(List<RefundShoppingInfo> shoppingInfoList) {
		this.shoppingInfoList = shoppingInfoList;
	}

	/**
	 * @return the payInfoList
	 */
	public List<RefundPaymentInfo> getPayInfoList() {
		return payInfoList;
	}

	/**
	 * @param payInfoList the payInfoList to set
	 */
	public void setPayInfoList(List<RefundPaymentInfo> payInfoList) {
		this.payInfoList = payInfoList;
	}

	/**
	 * @return the queryOnly
	 */
	public boolean isQueryOnly() {
		return queryOnly;
	}

	/**
	 * @param queryOnly the queryOnly to set
	 */
	public void setQueryOnly(boolean queryOnly) {
		this.queryOnly = queryOnly;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the subOrderType
	 */
	public String getSubOrderType() {
		return subOrderType;
	}

	/**
	 * @param subOrderType the subOrderType to set
	 */
	public void setSubOrderType(String subOrderType) {
		this.subOrderType = subOrderType;
	}
}
