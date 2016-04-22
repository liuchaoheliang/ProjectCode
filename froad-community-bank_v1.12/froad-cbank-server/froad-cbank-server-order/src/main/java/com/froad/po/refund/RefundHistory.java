package com.froad.po.refund;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class RefundHistory implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * refund_id, 退款ID
	 */
	private String _id = null;
	/**
	 * 生成时间
	 */
	private Long createTime = null;
	/**
	 * 客户端ID
	 */
	private String clientId = null;
	/**
	 * 原订单号
	 */
	private String orderId = null;
	/**
	 * 退款时间
	 */
	private Long refundTime = null;
	/**
	 * 退款购物信息
	 */
	private List<RefundPaymentInfo> paymentInfo = null;
	/**
	 * 退款状态
	 */
	private String refundState = null;
	/**
	 * 退款来源
	 */
	private String refundResource = null;
	/**
	 * 退款购物信息
	 */
	private List<RefundShoppingInfo> shoppingInfo = null;
	/**
	 * 退款原因
	 */
	private String reason = null;
	/**
	 * 会员编号
	 */
	private String memberCode = null;
	
	private Integer isVipRefund;
	
	/**
	 * @return the _id
	 */
	@JSONField(name="_id")
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	@JSONField(name="_id")
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the orderId
	 */
	@JSONField(name="order_id")
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	@JSONField(name="order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the refundTime
	 */
	@JSONField(name="refund_time")
	public Long getRefundTime() {
		return refundTime;
	}

	/**
	 * @param refundTime the refundTime to set
	 */
	@JSONField(name="refund_time")
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * @return the paymentInfo
	 */
	@JSONField(name="payment_info")
	public List<RefundPaymentInfo> getPaymentInfo() {
		return paymentInfo;
	}

	/**
	 * @param paymentInfo the paymentInfo to set
	 */
	@JSONField(name="payment_info")
	public void setPaymentInfo(List<RefundPaymentInfo> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	/**
	 * @return the createTime
	 */
	@JSONField(name="create_time")
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	@JSONField(name="create_time")
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the clientId
	 */
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the refundState
	 */
	@JSONField(name="refund_state")
	public String getRefundState() {
		return refundState;
	}

	/**
	 * @param refundState the refundState to set
	 */
	@JSONField(name="refund_state")
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	/**
	 * @return the refundResource
	 */
	@JSONField(name="refund_resource")
	public String getRefundResource() {
		return refundResource;
	}

	/**
	 * @param refundResource the refundResource to set
	 */
	@JSONField(name="refund_resource")
	public void setRefundResource(String refundResource) {
		this.refundResource = refundResource;
	}

	/**
	 * @return the shoppingInfo
	 */
	@JSONField(name="shopping_info")
	public List<RefundShoppingInfo> getShoppingInfo() {
		return shoppingInfo;
	}

	/**
	 * @param shoppingInfo the shoppingInfo to set
	 */
	@JSONField(name="shopping_info")
	public void setShoppingInfo(List<RefundShoppingInfo> shoppingInfo) {
		this.shoppingInfo = shoppingInfo;
	}

	/**
	 * @return the reason
	 */
	@JSONField(name="reason")
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	@JSONField(name="reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the memberCode
	 */
	@JSONField(name="member_code")
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	@JSONField(name="member_code")
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	@JSONField(name="is_vip_refund")
	public Integer getIsVipRefund() {
		return isVipRefund;
	}

	@JSONField(name="is_vip_refund")
	public void setIsVipRefund(Integer isVipRefund) {
		this.isVipRefund = isVipRefund;
	}
	
	
}
