package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Query_Face_Group_Detail_PC_Res{
	private Long createTime; 
	private String orderStatus; 
	private Double pointDiscount; 
	private Double totalMoney; 
	private String outletId; 
	private String settlementStatus; 
	private String consignee; 
	private String address; 
	private String phone; 
	private String deliveryCorpId; 
	private String deliveryCorpName; 
	private String trackingNo; 
	private Long shippingTime; 
	private String subOrderId;
	private String orderId;
	private Double totalDeliveryMoney;
	private List<Query_Face_Group_Detail_Product_PC_Res> productInfo; 
    private String outletName;
    public String clientId;
    /**
     * 退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款
     */
    private String refundState; // required
    //惠付新增字段
    /**
     * 消费总金额 
     */
    private Double consumeMoney ;
    /**
     * 门店折扣金额 
     */
    private Double discountMoney ;
    /**
     * 实付款
     */
    private Double totalCash ;
    /**
     * 优惠金额 
     */
    private Double cutMoney ;
    /**
     * 积分抵扣的金额
     */
    private Double pointMoney;
    
     
    
	public Double getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(Double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Double getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(Double totalCash) {
		this.totalCash = totalCash;
	}

	public Double getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}

	public Double getPointMoney() {
		return pointMoney;
	}

	public void setPointMoney(Double pointMoney) {
		this.pointMoney = pointMoney;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}   
	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Double getTotalDeliveryMoney() {
		return totalDeliveryMoney;
	}

	public void setTotalDeliveryMoney(Double totalDeliveryMoney) {
		this.totalDeliveryMoney = totalDeliveryMoney;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getPointDiscount() {
		return pointDiscount;
	}

	public void setPointDiscount(Double pointDiscount) {
		this.pointDiscount = pointDiscount;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
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

	public List<Query_Face_Group_Detail_Product_PC_Res> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(
			List<Query_Face_Group_Detail_Product_PC_Res> productInfo) {
		this.productInfo = productInfo;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	

}
