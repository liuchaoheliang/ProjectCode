package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * 面对面和团购返回类
 * 
 * @author Administrator
 *
 */
public class Query_Face_Group_PC_Res {
	private String orderId;
	private String subOrderId;
	private Long createTime;
	private Double subTotalMoney;
	private String productName;
	private Integer quantity;
	private String type;
	private String orderStatus;
	private String settleState;
	private String deliveryType;
	private String deliveryStatus;
	private Double deliveryMoney;

	/**
	 * 商品ID
	 */
	private String productId; // required
    /**
     * 退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款
     */
    private String refundState; // required
    
    /**
     * 收货人姓名（名优特惠使用）
     */
    private String consignee; // required
    /**
     * 收货人地址（名优特惠使用）
     */
    private String address; // required
    /**
     * 收货人手机号（名优特惠使用）
     */
    private String phone; // required
     /**
     * 结算状态 0-未结算，1:结算中， 2：已结算，3：结算失败，4：无效结算记录
     */
    private String settlementStatus; // required
    
    private String userName;//面对面操作人
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
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

	public Double getSubTotalMoney() {
		return subTotalMoney;
	}

	public void setSubTotalMoney(Double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSettleState() {
		return settleState;
	}

	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
