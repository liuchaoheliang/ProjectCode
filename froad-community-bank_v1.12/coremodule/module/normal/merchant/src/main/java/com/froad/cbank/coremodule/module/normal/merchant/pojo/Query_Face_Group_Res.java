package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

/**
 * 面对面和团购返回类
 * 
 * @author Administrator
 *
 */
public class Query_Face_Group_Res {
	/**
	 * 子订单号
	 */
	private String subOrderId; 
	/**
	 * 商品图片
	 */
	private List<String> productImages; 
	/**
	 * 数量
	 */
	private Integer quantity; 
	/**
	 * 订单状态
	 */
	private String orderStatus; 
	/**
	 * 用户名
	 */
	private String memberName; 
	/**
	 * 创建时间
	 */
	private Long createTime; 
	/**
	 * 总金额
	 */
	private Double subTotalMoney; 
	/**
	 * 订单号
	 */
    private String orderId;
    
    /**
     * 退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款
     */
    private String refundState; // required
    
    /**
     * 0.未发货，1.已发货，2.已收货，3.未提货，4.已提货
     */
    private String deliveryStatus; // required
    
    /**
     * 实际付款
     */
    private Double realPrice; 
    /**
     * 门店名称
     */
    private String outletName;
    /**
     * 门店编号
     */
    private String outletId;
    /**
     * 用户手机号码
     */
    private String phone;
    /**
     * 消费总额
     */
    private Double totalPrice;
    /**
     * 支付时间
     */
    private Long paymentTime;
    /**
     * 结算状态   0-未结算，1:结算中， 2：已结算，部分结算，3：结算失败，4：无效结算记录
     */
    private String settlementStatus;
    /**
     * 操作员
     */
    private String merchantUserName;
    
    
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
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

	public List<String> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<String> productImages) {
		this.productImages = productImages;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(Double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	
}
