package com.froad.cbank.coremodule.normal.boss.pojo.settle;

/**
 * 结算异常订单响应
 * 
 * @ClassName SettleExceptionRes
 * @author zxl
 * @date 2015年9月18日 下午2:43:37
 */
public class SettleExceptionRes {
	
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 账单号
	 */
	private String billNo;
	/**
	 * 支付方式
	 */
	private Integer paymentType;
	
	/**
	 * 支付id
	 */
	private String paymentId;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
