package com.froad.cbank.coremodule.normal.boss.pojo.refund;

import java.util.List;

/**
 * 退款异常订单详情
 * @ClassName RefundExceptionDetailRes
 * @author zxl
 * @date 2015年9月21日 上午10:50:29
 */
public class RefundExceptionDetailRes {
	
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 退款id
	 */
	private String refundId; 
	/**
	 * 退款状态
	 */
	private String refundState; 
	/**
	 * 退款时间
	 */
	private Long refundTime;
	
	/**
	 * 退还现金
	 */
	private Double cash; 
	
	/**
	 * 退还方付通积分
	 */
	private Double fftPoints;
	
	/**
	 * 退还银行积分
	 */
	private Double bankPoints;
	  
	/**
     * 异常类型
     */
	private String exceptionType; 
	
	/**
	 * 异常描述
	 */
	private String exceptionDesc; 
	
	/**
	 * 处理建议
	 */
	private String proposal; 
	
	
	/**
	 * 商品列表
	 */
	private List<RefundExceptionProductRes> productList;
	
	
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	public List<RefundExceptionProductRes> getProductList() {
		return productList;
	}
	public void setProductList(List<RefundExceptionProductRes> productList) {
		this.productList = productList;
	}
	public Double getFftPoints() {
		return fftPoints;
	}
	public void setFftPoints(Double fftPoints) {
		this.fftPoints = fftPoints;
	}
	public Double getBankPoints() {
		return bankPoints;
	}
	public void setBankPoints(Double bankPoints) {
		this.bankPoints = bankPoints;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public Long getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionDesc() {
		return exceptionDesc;
	}
	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
	
	
}
