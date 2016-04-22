package com.froad.cbank.coremodule.normal.boss.pojo.delivery;

public class DeliverGoodsRes {

	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 子订单ID
	 */
	private String subOrderId;
	/**
	 * 所属一级机构
	 */
	private String fOrgName;
	/**
	 * 所属二级机构
	 */
	private String sOrgName;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 购买数量
	 */
	private int buyCount;
	/**
	 * 退款数量
	 */
	private int refundCount;
	/**
	 * 实际订购数量
	 */
	private int realCount;
	/**
	 * 收货人
	 */
	private String reciver;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 收货地址
	 */
	private String address;

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

	public String getFOrgName() {
		return fOrgName;
	}

	public void setFOrgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}

	public String getSOrgName() {
		return sOrgName;
	}

	public void setSOrgName(String sOrgName) {
		this.sOrgName = sOrgName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}

	public int getRealCount() {
		return realCount;
	}

	public void setRealCount(int realCount) {
		this.realCount = realCount;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
