package com.froad.po.settlement;

import java.util.List;


import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.Arith;

/**
 *  结算 Bean
  * @ClassName: Settlement
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public class Settlement {
	private String id;							// 主键ID
	
	private String 	settlementId;				// 结算ID
	
	private long 	createTime;					// 创建时间
	
	private String	type;						// 1-团购 3-面对面
	
	private String  paymentId;					// 支付ID
	
	private String 	clientId;					// 客户端ID
	
	private String orderId;						// 订单ID
	
	private String subOrderId;					// 子订单ID
	
	private String productName;					// 商品名称
	
	private String productId;					// 商品ID
	
	private Integer productCount;				// 商品数目
	
	private List<String> tickets;				// 卷ID（团购使用）
	
	private String merchantId;					// 商户ID
	
	private String merchantName;				// 商户名称
	
	private String outletId;					// 门店ID
	
	private String outletName;					// 门店名称
	
	private Long merchantUserId;				// 商户用户ID（收银员）
	
	private String settleState;					// 结算状态 0-未结算 1-结算中 2-已结算 3-结算失败
	
	private int money;							// 结算金额
	
	private String remark;						// 备注

	@JSONField(name="settlement_id")
	public String getSettlementId() {
		return settlementId;
	}
	@JSONField(name="settlement_id")
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	@JSONField(name="create_time")
	public long getCreateTime() {
		return createTime;
	}
	@JSONField(name="create_time")
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@JSONField(name="type")
	public String getType() {
		return type;
	}
	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}
	@JSONField(name="payment_id")
	public String getPaymentId() {
		return paymentId;
	}
	@JSONField(name="payment_id")
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
	@JSONField(name="outlet_id")
	public String getOutletId() {
		return outletId;
	}
	@JSONField(name="outlet_id")
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	@JSONField(name="merchant_user_id")
	public Long getMerchantUserId() {
		return merchantUserId;
	}
	@JSONField(name="merchant_user_id")
	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	@JSONField(name="settle_state")
	public String getSettleState() {
		return settleState;
	}
	@JSONField(name="settle_state")
	public void setSettleState(String settleState) {
		this.settleState = settleState;
	}
	@JSONField(name="money")
	public int getMoney() {
		return money;
	}
	@JSONField(serialize=false,deserialize=false)
	public double getMoneyd() {
		return Arith.div(this.money, 1000,3);
	}
	
	@JSONField(name="money")
	public void setMoney(int money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<String> getTickets() {
		return tickets;
	}
	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}
	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}
	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}
	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@JSONField(name="product_count")
	public Integer getProductCount() {
		return productCount;
	}
	@JSONField(name="product_count")
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	@JSONField(name="_id")
	public String getId() {
		return id;
	}
	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}
	
	@JSONField(name="outlet_name")
	public String getOutletName() {
		return outletName;
	}
	
	@JSONField(name="outlet_name")
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}
	
	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	
	
	
}

