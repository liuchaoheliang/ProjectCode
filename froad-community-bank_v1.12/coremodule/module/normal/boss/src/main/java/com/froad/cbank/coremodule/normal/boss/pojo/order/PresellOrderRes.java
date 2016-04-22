package com.froad.cbank.coremodule.normal.boss.pojo.order;

public class PresellOrderRes {

	/**
	 * 子订单编号
	 */
	private String subOrderId;
	/**
	 * 下单时间
	 */
	private Long createTime;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 购买数量
	 */
	private Integer buyQuantity;
	/**
	 * 支付方式；1：现金支付；2：联盟积分支付；3：银行积分支付；4：联盟积分+现金支付；5：银行积分+现金支付；6：赠送积分
	 */
	private String paymentMethod;
	/**
	 * 总价
	 */
	private Double totalPrice;
	/**
	 * 现金
	 */
	private Double cashAmount;
	/**
	 * 银行积分
	 */
	private Double bankPoint;
	/**
	 * 联盟积分
	 */
	private Double froadPoint;
	/**
	 * 配送方式（0-配送  1-自提  2-配送或自提）
	 */
	private String deliveryOption;
	/**
	 * 配送地址
	 */
	private String address;
	/**
	 * 提（收）货人
	 */
	private String consignee;
	/**
	 * 提（收）货人电话
	 */
	private String consigneePhone;
	/**
	 * 所属行社
	 */
	private String orgName;
	/**
	 * 提货网点
	 */
	private String consigneeOrgName;
	/**
	 * 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成
	 */
	private String orderStatus;
	/**
	 * 账单号
	 */
	private String billNo;
	/**
	 * 当前子订单对应的大订单下时候有退款：0：有；1：没有
	 */
	private String isExistsRefund;

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
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

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getConsigneeOrgName() {
		return consigneeOrgName;
	}

	public void setConsigneeOrgName(String consigneeOrgName) {
		this.consigneeOrgName = consigneeOrgName;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Double getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(Double bankPoint) {
		this.bankPoint = bankPoint;
	}

	public Double getFroadPoint() {
		return froadPoint;
	}

	public void setFroadPoint(Double froadPoint) {
		this.froadPoint = froadPoint;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getIsExistsRefund() {
		return isExistsRefund;
	}

	public void setIsExistsRefund(String isExistsRefund) {
		this.isExistsRefund = isExistsRefund;
	}
	
}
