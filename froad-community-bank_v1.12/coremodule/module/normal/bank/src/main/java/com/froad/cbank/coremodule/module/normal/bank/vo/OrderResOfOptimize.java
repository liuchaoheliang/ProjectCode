package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * ClassName: OrderRes4Group
 * Function: 团购订单列表的Vo
 * date: 2015年8月26日 上午10:22:37
 *
 * @author 明灿
 * @version
 */
public class OrderResOfOptimize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002342196239168006L;

	private String orderId; // 订单编号
	private String subOrderId; // 子订单编号
	private String paymentMethod; // 支付方式
	private String orderStatus; // 订单状态
	private double money; // 金额
	private int quantity; // 销售数量
	private int buyNo; // 销购买数量
	private double point; // 积分
	private long createTime; // 销购买数量
	private String consumeStatus; // 消费状态
	private String merchantName; // 商户名称
	private String taker; // 收件人
	private String phone; // 手机号
	private String address; // 地址
	private String status; // 状态(配送)
	private String closeReason; // 退款原因
	private String orgNames; // 网点
	private String refundStatus; // 退款状态
	private String isAllRefund; // 是否全单退款
	private String outletName; // 门店名称
	private List<ProductVo4List> productList; // 商户
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getBuyNo() {
		return buyNo;
	}

	public void setBuyNo(int buyNo) {
		this.buyNo = buyNo;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTaker() {
		return taker;
	}

	public void setTaker(String taker) {
		this.taker = taker;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductVo4List> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVo4List> productList) {
		this.productList = productList;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getIsAllRefund() {
		return isAllRefund;
	}

	public void setIsAllRefund(String isAllRefund) {
		this.isAllRefund = isAllRefund;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	
}
