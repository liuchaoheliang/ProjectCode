package com.froad.cbank.coremodule.normal.boss.pojo.settle;

/**
 * 结算异常订单详情
 * @ClassName SettleExceptionDetailRes
 * @author zxl
 * @date 2015年9月21日 上午10:50:29
 */
public class SettleExceptionDetailRes {
	
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
	 * 支付时间
	 */
	private Long paymentTime;
	
	/**
	 * 账单号
	 */
	private String billNo;
	
	/**
	 * 支付方式
	 */
	private String paymentMethod;
	
	/**
	 * 支付id
	 */
	private String paymentId;
	
	/**
	 * 支付金额
	 */
	private Double totalPrice;
	
	/**
	 * 支付账号
	 */
	private String memberName;
	
	/**
	 * 支付状态
	 */
	private String paymentStatus;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 门店名称
	 */
	private String outletName;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 消费数量
	 */
	private Integer productCount;
	
	/**
	 * 结算金额
	 */
	private Double money;
	
	/**
	 * 券码
	 */
	private String tickets;
	
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
	 * 积分比例
	 */
	private Integer pointRate;
	
	
	public Integer getPointRate() {
		return pointRate;
	}
	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public Long getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
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
	
	
}
