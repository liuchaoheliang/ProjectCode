package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Query_Face_Group_Detail_Res{
	private String memberName;
	private Long memberCode;
	private Long createTime;
	private String settleState;
	private String outletId;
	private Boolean isOutletComment;
	private String orderStatus;
	private Double totalPrice;
	private Integer quantity;
	private String outletName;
	public String clientId;
	
	private long paymentTime; 
	
	private String bigSettleState;
	
	private List<Query_Face_Group_Detail_Product_Res> productInfos;
	/**
	 * 收货人姓名（名优特惠使用）
	 */
	private String consignee; 
	/**
	 * 收货地址（名优特惠使用）
	 */
	private String address; 
	/**
	 * 收货人手机号码（名优特惠使用）
	 */
	private String phone; 
	/**
	 * 物流ID
	 */
	private String deliveryCorpId; 
	/**
	 * 物流名称
	 */
	private String deliveryCorpName; 
	/**
	 * 物流单号
	 */
	private String trackingNo; 
	/**
	 * 发货时间
	 */
	private Long shippingTime; 
	/**
	 * 地址ID收货地址使用
	 */
	private Long areaId; 
	/**
	 * 发货状态
	 */
	private String deliveryStatus; 
	
	/**
	 * 运费总额
	 */
	private Double totalDevriyMoney;
	
    /**
     * 退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款
     */
    private String refundState; // required
    
    /**
	 * 积分抵扣
	 */
	public double pointDiscount;
	
	 /**
	   * 子订单总优惠金额
	   */
	public double totalCutMoney; // required
	  /**
	   * 子订单总实付金额
	   */
    public double totalCash; // required
    
    //惠付新增字段
    
    /**
     * 消费总金额 
     */
	private double consumeMoney;
	 /**
     * 门店折扣 
     */
	private double discountMoney ;
	/**
	 * 积分抵扣
	 */
	private double pointMoney ;
	/**
	 * 优惠金额
	 */
	private double cutMoney ;
	/**
	 * 操作员
	 */
	private String merchantUserName;
	
	
	public double getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public double getPointMoney() {
		return pointMoney;
	}

	public void setPointMoney(double pointMoney) {
		this.pointMoney = pointMoney;
	}

	public double getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(double cutMoney) {
		this.cutMoney = cutMoney;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public Double getTotalDevriyMoney() {
		return totalDevriyMoney;
	}

	public void setTotalDevriyMoney(Double totalDevriyMoney) {
		this.totalDevriyMoney = totalDevriyMoney;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDeliveryCorpId() {
		return deliveryCorpId;
	}

	public void setDeliveryCorpId(String deliveryCorpId) {
		this.deliveryCorpId = deliveryCorpId;
	}

	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}

	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Long getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Long shippingTime) {
		this.shippingTime = shippingTime;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getSettleState() {
		return settleState;
	}

	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public Boolean getIsOutletComment() {
		return isOutletComment;
	}

	public void setIsOutletComment(Boolean isOutletComment) {
		this.isOutletComment = isOutletComment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<Query_Face_Group_Detail_Product_Res> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(
			List<Query_Face_Group_Detail_Product_Res> productInfos) {
		this.productInfos = productInfos;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getBigSettleState() {
		return bigSettleState;
	}

	public void setBigSettleState(String bigSettleState) {
		this.bigSettleState = bigSettleState;
	}

	public double getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(double pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public double getTotalCutMoney() {
		return totalCutMoney;
	}

	public void setTotalCutMoney(double totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}

	public double getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(double totalCash) {
		this.totalCash = totalCash;
	}
}
