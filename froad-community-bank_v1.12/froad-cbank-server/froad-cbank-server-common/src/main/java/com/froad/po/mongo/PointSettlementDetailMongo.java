package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 积分结算详情表
 */
public class PointSettlementDetailMongo implements java.io.Serializable {

	/**
	 * serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2878599065095475175L;
	
	private String id;		// 主键ID
	
	private	Long	cTime;    //统计时间
	
	private Long 	createTime;		// 结算时间创建时间
	
	private String 	settlementId;		// 结算ID
	
	private	String	clientId;    //	客户端标识

	private	Integer	isQrcode;    //	是否面对面

		

	private	String	orderId;    //	订单编号

	private	Long	memberCode;    //	会员编号

	private	String	memberName;    //	会员名

	private	String	merchantId;    //	商户ID

	private	String	merchantName;    //	商户名

	private	String	productId;    //	商品ID

	private	String	productName;    //	商品名称

	private	Integer	productPrice;    //	商品单价

	private	Integer	productQuantity;    //	商品数量

	private	Integer	productTotalPrice;    //	商品总价

	private	Integer	settlementTotalPrice;    //	结算总价

	private	String	paymentMethod;    //	支付方式

	private	Integer	bankPoint;    //	银行积分

	private	String	bankPointRate;    //	银行积分比例

	private	Integer	froadPoint;    //	联盟积分

	private	Integer	cash;    //	现金

	@JSONField(name="_id")
	public String getId() {
		return id;
	}
	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}
	@JSONField(name="c_time")
	public Long getcTime() {
		return cTime;
	}
	@JSONField(name="c_time")
	public void setcTime(Long cTime) {
		this.cTime = cTime;
	}
	@JSONField(name="create_time")
	public long getCreateTime() {
		return createTime;
	}
	@JSONField(name="create_time")
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	@JSONField(name="settlement_id")
	public String getSettlementId() {
		return settlementId;
	}
	@JSONField(name="settlement_id")
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}

	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JSONField(name="is_qrcode")
	public Integer getIsQrcode() {
		return isQrcode;
	}

	@JSONField(name="is_qrcode")
	public void setIsQrcode(Integer isQrcode) {
		this.isQrcode = isQrcode;
	}


	@JSONField(name="order_id")
	public String getOrderId() {
		return orderId;
	}

	@JSONField(name="order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@JSONField(name="member_code")
	public Long getMemberCode() {
		return memberCode;
	}

	@JSONField(name="member_code")
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	@JSONField(name="member_name")
	public String getMemberName() {
		return memberName;
	}

	@JSONField(name="member_name")
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}

	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}

	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}

	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}

	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	@JSONField(name="product_price")
	public Integer getProductPrice() {
		return productPrice;
	}

	@JSONField(name="product_price")
	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	@JSONField(name="product_quantity")
	public Integer getProductQuantity() {
		return productQuantity;
	}

	@JSONField(name="product_quantity")
	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	@JSONField(name="product_total_price")
	public Integer getProductTotalPrice() {
		return productTotalPrice;
	}

	@JSONField(name="product_total_price")
	public void setProductTotalPrice(Integer productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	@JSONField(name="settlement_total_price")
	public Integer getSettlementTotalPrice() {
		return settlementTotalPrice;
	}

	@JSONField(name="settlement_total_price")
	public void setSettlementTotalPrice(Integer settlementTotalPrice) {
		this.settlementTotalPrice = settlementTotalPrice;
	}

	@JSONField(name="payment_method")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	@JSONField(name="payment_method")
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@JSONField(name="bank_point")
	public Integer getBankPoint() {
		return bankPoint;
	}

	@JSONField(name="bank_point")
	public void setBankPoint(Integer bankPoint) {
		this.bankPoint = bankPoint;
	}

	@JSONField(name="bank_point_rate")
	public String getBankPointRate() {
		return bankPointRate;
	}

	@JSONField(name="bank_point_rate")
	public void setBankPointRate(String bankPointRate) {
		this.bankPointRate = bankPointRate;
	}

	@JSONField(name="froad_point")
	public Integer getFroadPoint() {
		return froadPoint;
	}

	@JSONField(name="froad_point")
	public void setFroadPoint(Integer froadPoint) {
		this.froadPoint = froadPoint;
	}

	@JSONField(name="cash")
	public Integer getCash() {
		return cash;
	}

	@JSONField(name="cash")
	public void setCash(Integer cash) {
		this.cash = cash;
	}
	
	
}
