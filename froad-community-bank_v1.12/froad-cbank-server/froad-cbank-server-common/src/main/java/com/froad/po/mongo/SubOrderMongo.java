package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 子订单表
 * @author zhangkai
 */
public class SubOrderMongo implements java.io.Serializable {

	private static final long serialVersionUID = -2925999999823364340L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 子订单ID
	 */
	private String subOrderId;
	
	/**
	 * 商户ID（子订单为机构商户所属，则为机构号）
	 */
	private String merchantId;
	
	/**
	 * 商户名称（子订单为机构商户所属，则为机构名称）
	 */
	private String merchantName;
	
	/**
	 * 一级机构
	 */
	private String forgCode;
	
	/**
	 * 二级机构
	 */
	private String sorgCode;
	
	/**
	 * 三级机构
	 */
	private String torgCode;
	
	/**
	 * 四级机构
	 */
	private String lorgCode;
	
	/**
	 * 会员ID
	 */
	private Long memberCode;
	
	/**
	 * 会员名称
	 */
	private String memberName;
	
	/**
	 * 子订单类型（SubOrderType）
	 */
	private String type;
	
	/**
	 * 配送方式：预售为配送或自提，线下积分兑换为自提，线上积分兑换为配送，名优特惠为配送，团购为自提
	 */
	private String deliveryOption;
	
	/**
	 * 配送状态
	 */
	private String deliveryState;

	/**
	 * 商品信息
	 */
	private List<ProductMongo> products;
	
	/**
	 * 退款状态
	 */
	private String refundState;
	
	/**
	 * 是否有赠送积分：0-有赠送积分，1-没有赠送积分
	 */
	private Integer isGivePoint;
	
	/**
	 * 银行操作员ID(线下积分兑换) 
	 */
	private String operatorId;
	
	/**
	 * 银行操作员名(线下积分兑换)
	 */
	private String operatorName;

	@JSONField(name="_id")
	public String getId() {
		return id;
	}

	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}
	
	@JSONField(name="create_time")
	public Long getCreateTime() {
		return createTime;
	}

	@JSONField(name="create_time")
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}

	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JSONField(name="order_id")
	public String getOrderId() {
		return orderId;
	}

	@JSONField(name="order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@JSONField(name="sub_order_id")
	public String getSubOrderId() {
		return subOrderId;
	}

	@JSONField(name="sub_order_id")
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@JSONField(name="member_code")
	public Long getMemberCode() {
		return memberCode;
	}

	@JSONField(name="member_code")
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	@JSONField(name="type")
	public String getType() {
		return type;
	}

	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}

	@JSONField(name="products")
	public List<ProductMongo> getProducts() {
		return products;
	}

	@JSONField(name="products")
	public void setProducts(List<ProductMongo> products) {
		this.products = products;
	}

	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}

	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@JSONField(name="delivery_option")
	public String getDeliveryOption() {
		return deliveryOption;
	}

	@JSONField(name="delivery_option")
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	@JSONField(name="delivery_state")
	public String getDeliveryState() {
		return deliveryState;
	}

	@JSONField(name="delivery_state")
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	@JSONField(name="f_org_code")
	public String getForgCode() {
		return forgCode;
	}

	@JSONField(name="f_org_code")
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}

	@JSONField(name="s_org_code")
	public String getSorgCode() {
		return sorgCode;
	}

	@JSONField(name="s_org_code")
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}

	@JSONField(name="t_org_code")
	public String getTorgCode() {
		return torgCode;
	}

	@JSONField(name="t_org_code")
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}

	@JSONField(name="l_org_code")
	public String getLorgCode() {
		return lorgCode;
	}

	@JSONField(name="l_org_code")
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}

	@JSONField(name="order_status")
	public String getOrderStatus() {
		return orderStatus;
	}

	@JSONField(name="order_status")
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@JSONField(name="member_name")
	public String getMemberName() {
		return memberName;
	}

	@JSONField(name="member_name")
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@JSONField(name="refund_state")
	public String getRefundState() {
		return refundState;
	}

	@JSONField(name="refund_state")
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	@JSONField(name="is_give_point")
	public Integer getIsGivePoint() {
		return isGivePoint;
	}

	@JSONField(name="is_give_point")
	public void setIsGivePoint(Integer isGivePoint) {
		this.isGivePoint = isGivePoint;
	}

	@JSONField(name="operator_id")
	public String getOperatorId() {
		return operatorId;
	}

	@JSONField(name="operator_id")
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@JSONField(name="operator_name")
	public String getOperatorName() {
		return operatorName;
	}

	@JSONField(name="operator_name")
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	
}
