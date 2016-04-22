package com.froad.common.beans;

public class RefundProductBean {
	/**
	 * 商品ID
	 */
	private String productId = null;
	
	/**
	 * 商品名称
	 */
	private String productName = null;
	
	/**
	 * 商品图片URL
	 */
	private String image = null;
	
	/**
	 * 子订单ID
	 */
	private String subOrderId = null;
	
	/**
	 * 子订单类型
	 */
	private String subOrderType = null;
	
	/**
	 * 商品单价
	 */
	private int price = 0;
	
	/**
	 * 商品购买数量
	 */
	private int quantity = 0;
	
	/**
	 * VIP单价
	 */
	private int vipPrice = 0;
	
	/**
	 * 以VIP价格购买商品数
	 */
	private int vipQuantity = 0;
	
	/**
	 * 已退商品数量
	 */
	private int refundedQuantity = 0;
	
	/**
	 * 已退VIP商品数量
	 */
	private int refundedVipQuantity = 0;
	
	/**
	 * 购买商品赠送积分
	 */
	private int creditPoint = 0;
	
	/**
	 * 快递费用
	 */
	private int deliveryFee = 0;
	
	/**
	 * 请求退款数量
	 */
	private int requestQuantity = 0;
	
	/**
	 * 实际退数量
	 */
	private int actualRefundQuantity = 0;
	
	/**
	 * 实际退VIP数量
	 */
	private int actualRefundVipQuantity = 0;
	
	/**
	 * 配送或者自提方式
	 */
	private String deliveryOption;
	
	/**
	 * 普通价每单位商品支付积分
	 */
	private Integer[] pointArray;
	
	/**
	 * VIP价每单位商品支付积分
	 */
	private Integer[] vipPointArray;
	
	/**
	 * 商品总支付积分
	 */
	private Integer totalPoint;
	
	/**
	 * 普通价每单位商品实付现金
	 */
	private Integer[] cashArray;
	
	/**
	 * VIP价每单位商品实付现金
	 */
	private Integer[] vipCashArray;
	
	/**
	 * 每单位普通价商品优惠金额数组
	 */
	private Integer[] cutMoneyArray;
	
	/**
	 * 每单位VIP价商品优惠金额数组
	 */
	private Integer[] vipCutMoneyArray;
	
	/**
	 * 商品总实付现金
	 */
	private Integer totalCash;
	
	private Integer refundTotalPrice;
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the subOrderId
	 */
	public String getSubOrderId() {
		return subOrderId;
	}

	/**
	 * @param subOrderId the subOrderId to set
	 */
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
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

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the vipPrice
	 */
	public int getVipPrice() {
		return vipPrice;
	}

	/**
	 * @param vipPrice the vipPrice to set
	 */
	public void setVipPrice(int vipPrice) {
		this.vipPrice = vipPrice;
	}

	/**
	 * @return the vipQuantity
	 */
	public int getVipQuantity() {
		return vipQuantity;
	}

	/**
	 * @param vipQuantity the vipQuantity to set
	 */
	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}

	/**
	 * @return the refundedQuantity
	 */
	public int getRefundedQuantity() {
		return refundedQuantity;
	}

	/**
	 * @param refundedQuantity the refundedQuantity to set
	 */
	public void setRefundedQuantity(int refundedQuantity) {
		this.refundedQuantity = refundedQuantity;
	}

	/**
	 * @return the refundedVipQuantity
	 */
	public int getRefundedVipQuantity() {
		return refundedVipQuantity;
	}

	/**
	 * @param refundedVipQuantity the refundedVipQuantity to set
	 */
	public void setRefundedVipQuantity(int refundedVipQuantity) {
		this.refundedVipQuantity = refundedVipQuantity;
	}

	/**
	 * @return the creditPoint
	 */
	public int getCreditPoint() {
		return creditPoint;
	}

	/**
	 * @param creditPoint the creditPoint to set
	 */
	public void setCreditPoint(int creditPoint) {
		this.creditPoint = creditPoint;
	}

	/**
	 * @return the deliveryFee
	 */
	public int getDeliveryFee() {
		return deliveryFee;
	}

	/**
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(int deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	/**
	 * @return the requestQuantity
	 */
	public int getRequestQuantity() {
		return requestQuantity;
	}

	/**
	 * @param requestQuantity the requestQuantity to set
	 */
	public void setRequestQuantity(int requestQuantity) {
		this.requestQuantity = requestQuantity;
	}

	/**
	 * @return the actualRefundQuantity
	 */
	public int getActualRefundQuantity() {
		return actualRefundQuantity;
	}

	/**
	 * @param actualRefundQuantity the actualRefundQuantity to set
	 */
	public void setActualRefundQuantity(int actualRefundQuantity) {
		this.actualRefundQuantity = actualRefundQuantity;
	}

	/**
	 * @return the actualRefundVipQuantity
	 */
	public int getActualRefundVipQuantity() {
		return actualRefundVipQuantity;
	}

	/**
	 * @param actualRefundVipQuantity the actualRefundVipQuantity to set
	 */
	public void setActualRefundVipQuantity(int actualRefundVipQuantity) {
		this.actualRefundVipQuantity = actualRefundVipQuantity;
	}

	/**
	 * @return the deliveryOption
	 */
	public String getDeliveryOption() {
		return deliveryOption;
	}

	/**
	 * @param deliveryOption the deliveryOption to set
	 */
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public Integer[] getPointArray() {
		return pointArray;
	}

	public void setPointArray(Integer[] pointArray) {
		this.pointArray = pointArray;
	}

	public Integer[] getVipPointArray() {
		return vipPointArray;
	}

	public void setVipPointArray(Integer[] vipPointArray) {
		this.vipPointArray = vipPointArray;
	}

	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public Integer[] getCashArray() {
		return cashArray;
	}

	public void setCashArray(Integer[] cashArray) {
		this.cashArray = cashArray;
	}

	public Integer[] getVipCashArray() {
		return vipCashArray;
	}

	public void setVipCashArray(Integer[] vipCashArray) {
		this.vipCashArray = vipCashArray;
	}

	public Integer getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(Integer totalCash) {
		this.totalCash = totalCash;
	}

	public Integer[] getCutMoneyArray() {
		return cutMoneyArray;
	}

	public void setCutMoneyArray(Integer[] cutMoneyArray) {
		this.cutMoneyArray = cutMoneyArray;
	}

	public Integer[] getVipCutMoneyArray() {
		return vipCutMoneyArray;
	}

	public void setVipCutMoneyArray(Integer[] vipCutMoneyArray) {
		this.vipCutMoneyArray = vipCutMoneyArray;
	}

	public Integer getRefundTotalPrice() {
		return refundTotalPrice;
	}

	public void setRefundTotalPrice(Integer refundTotalPrice) {
		this.refundTotalPrice = refundTotalPrice;
	}

	
	

}
