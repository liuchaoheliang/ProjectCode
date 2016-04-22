package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 订单信息 Mongodb
 */
public class OrderMongo implements java.io.Serializable {
	
	private static final long serialVersionUID = -520028773446761467L;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 会员ID
	 */
	private Long memberCode;
	
	/**
	 * 客户名称
	 */
	private String memberName;
	
	/**
	 * 商户ID
	 */
	private String merchantId;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 门店ID
	 */
	private String outletId;
	
	/**
	 * 门店简称
	 */
	private String outletName;
	
	/**
	 * 订单来源
	 */
	private String createSource;
	
	/**
	 * 支付方式
	 */
	private String paymentMethod;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 支付时间
	 */
	private Long paymentTime;
	
	/**
	 * 总货币值
	 */
	private Integer totalPrice;
	
	/**
	 * VIP优惠金额
	 */
	private Integer vipDiscount;
	
	/**
	 * 代金券抵扣金额
	 */
	private Integer cashDiscount;
	
	/**
	 * 实际总货币数值
	 */
	private Integer realPrice;
	
	/**
	 * 分分通积分
	 */
	private Integer fftPoints;
	
	/**
	 * 银行积分
	 */
	private Integer bankPoints;
	
	/**
	 * 支付手续费
	 */
	private String fee;
	
	/**
	 * 用于接收券短信的手机号
	 */
	private String phone;
	
	/**
	 * 积分兑换比例
	 */
	private String pointRate;
	
	/**
	 * 赠送积分数
	 */
	private Integer givePoints;
	
	/**
	 * 订单是否为秒杀  1：是   0：否
	 */
	private int isSeckill;
	
	/**
	 * 秒杀订单受理号
	 */
	private String reqId;
	
	/**
	 * 订单是否为面对面支付   1：是   0：否
	 */
	private int isQrcode;
	
	/**
	 * 订单是否为积分兑换   1：是   0：否
	 */
	private int isPoint;
	
	/**
	 * 订单是否为VIP订单   1：是   0：否
	 */
	private int isVipOrder;
	
	/**
	 * 二维码（面对面订单专用）
	 */
	private String qrcode;
	
	/**
	 * 是否是惠付订单  0：不是    1：是（惠付订单专用）
	 */
	private Integer isPrefPay;
	
	/**
	 * 提货人信息
	 */
	private String deliverId;
	
	/**
	 * 收银交易操作人(面对面订单专用)
	 */
	private Long merchantUserId;
	
	/**
	 * 收银交易操作人(面对面订单专用)
	 */
	private String merchantUserName;
	
	/**
	 * （面对面支付、开通VIP）的时候使用
	 */
	private String productId;
	
	
	/**
	 * （开通VIP）的时候使用
	 */
	private String productName;
	
	/**
	 * 配送地址信息
	 */
	private String recvId;
	
	/**
	 * 正常/库存已还/用户删除/商户删除
	 */
	private String state;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 一级机构（面对面订单专用）
	 */
	private String forgCode;
	
	/**
	 * 二级机构（面对面订单专用）
	 */
	private String sorgCode;
	
	/**
	 * 三级机构（面对面订单专用）
	 */
	private String torgCode;
	
	/**
	 * 四级机构（面对面订单专用）
	 */
	private String lorgCode;
	
	/**
	 * 所属区域ID（VIP订单专用）
	 */
	private String areaId;
	
	/**
	 * VIP订单-所属区域名（VIP订单专用）
	 */
	private String areaName;
	
	/**
	 * 开户行组织机构号（VIP订单专用）
	 */
	private String bankLabelID;
	
	/**
	 * 开户行组织机构名（VIP订单专用）
	 */
	private String bankLabelName;
	
	/**
	 * 开通方式（VIP订单专用）
	 */
	private String clientChannel;
	
	/**
	 * 营销活动ID
	 */
	private String marketId;
	
	/**
	 * 订单是否参与营销活动   1：是   0：否
	 */
	private String isActive;
	
	/**
	 * 订单满减金额
	 */
	private Integer cutMoney;
	
	/**
	 * 红包ID
	 */
	private String redPacketId;
	
	/**
	 * 现金券ID
	 */
	private String cashCouponId;
	
	/**
	 * 消费金额（惠付订单专用）
	 */
	private Integer consumeMoney;
	
	/**
	 * 打折优惠金额（惠付订单专用）
	 */
	private Integer discountMoney;
	
	/**
	 * 赠送金额
	 */
	private Integer giveMoney;
	
	@JSONField(name="_id")
	public String getOrderId() {
		return orderId;
	}
	
	@JSONField(name="_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@JSONField(name="client_id")
	public String getClientId() {
		return clientId;
	}

	@JSONField(name="client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JSONField(name="create_time")
	public Long getCreateTime() {
		return createTime;
	}

	@JSONField(name="create_time")
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	@JSONField(name="member_code")
	public Long getMemberCode() {
		return memberCode;
	}

	@JSONField(name="member_code")
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	@JSONField(name="create_source")
	public String getCreateSource() {
		return createSource;
	}

	@JSONField(name="create_source")
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	@JSONField(name="payment_method")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	@JSONField(name="payment_method")
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@JSONField(name="order_status")
	public String getOrderStatus() {
		return orderStatus;
	}

	@JSONField(name="order_status")
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@JSONField(name="payment_time")
	public Long getPaymentTime() {
		return paymentTime;
	}

	@JSONField(name="payment_time")
	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	@JSONField(name="total_price")
	public Integer getTotalPrice() {
		return totalPrice;
	}

	@JSONField(name="total_price")
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JSONField(name="vip_discount")
	public Integer getVipDiscount() {
		return vipDiscount;
	}

	@JSONField(name="vip_discount")
	public void setVipDiscount(Integer vipDiscount) {
		this.vipDiscount = vipDiscount;
	}

	@JSONField(name="cash_discount")
	public Integer getCashDiscount() {
		return cashDiscount;
	}

	@JSONField(name="cash_discount")
	public void setCashDiscount(Integer cashDiscount) {
		this.cashDiscount = cashDiscount;
	}

	@JSONField(name="real_price")
	public Integer getRealPrice() {
		return realPrice;
	}

	@JSONField(name="real_price")
	public void setRealPrice(Integer realPrice) {
		this.realPrice = realPrice;
	}

	@JSONField(name="fft_points")
	public Integer getFftPoints() {
		return fftPoints;
	}

	@JSONField(name="fft_points")
	public void setFftPoints(Integer fftPoints) {
		this.fftPoints = fftPoints;
	}

	@JSONField(name="bank_points")
	public Integer getBankPoints() {
		return bankPoints;
	}

	@JSONField(name="bank_points")
	public void setBankPoints(Integer bankPoints) {
		this.bankPoints = bankPoints;
	}

	@JSONField(name="fee")
	public String getFee() {
		return fee;
	}

	@JSONField(name="fee")
	public void setFee(String fee) {
		this.fee = fee;
	}

	@JSONField(name="phone")
	public String getPhone() {
		return phone;
	}

	@JSONField(name="phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JSONField(name="point_rate")
	public String getPointRate() {
		return pointRate;
	}

	@JSONField(name="point_rate")
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}

	@JSONField(name="give_points")
	public Integer getGivePoints() {
		return givePoints;
	}

	@JSONField(name="give_points")
	public void setGivePoints(Integer givePoints) {
		this.givePoints = givePoints;
	}

	@JSONField(name="is_qrcode")
	public int getIsQrcode() {
		return isQrcode;
	}

	@JSONField(name="is_qrcode")
	public void setIsQrcode(int isQrcode) {
		this.isQrcode = isQrcode;
	}

	@JSONField(name="deliver_id")
	public String getDeliverId() {
		return deliverId;
	}

	@JSONField(name="deliver_id")
	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}

	@JSONField(name="recv_id")
	public String getRecvId() {
		return recvId;
	}

	@JSONField(name="recv_id")
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}

	@JSONField(name="state")
	public String getState() {
		return state;
	}

	@JSONField(name="state")
	public void setState(String state) {
		this.state = state;
	}

	@JSONField(name="remark")
	public String getRemark() {
		return remark;
	}

	@JSONField(name="remark")
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JSONField(name="merchant_user_id")
	public Long getMerchantUserId() {
		return merchantUserId;
	}

	@JSONField(name="merchant_user_id")
	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	@JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}

	@JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
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

	@JSONField(name="outlet_id")
	public String getOutletId() {
		return outletId;
	}

	@JSONField(name="outlet_id")
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	@JSONField(name="qrcode")
	public String getQrcode() {
		return qrcode;
	}

	@JSONField(name="qrcode")
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	@JSONField(name="is_seckill")
	public int getIsSeckill() {
		return isSeckill;
	}

	@JSONField(name="is_seckill")
	public void setIsSeckill(int isSeckill) {
		this.isSeckill = isSeckill;
	}

	@JSONField(name="is_point")
	public int getIsPoint() {
		return isPoint;
	}

	@JSONField(name="is_point")
	public void setIsPoint(int isPoint) {
		this.isPoint = isPoint;
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

	@JSONField(name="req_id")
	public String getReqId() {
		return reqId;
	}

	@JSONField(name="req_id")
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	@JSONField(name="is_vip_order")
	public int getIsVipOrder() {
		return isVipOrder;
	}

	@JSONField(name="is_vip_order")
	public void setIsVipOrder(int isVipOrder) {
		this.isVipOrder = isVipOrder;
	}

	@JSONField(name="area_id")
	public String getAreaId() {
		return areaId;
	}

	@JSONField(name="area_id")
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@JSONField(name="area_name")
	public String getAreaName() {
		return areaName;
	}

	@JSONField(name="area_name")
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@JSONField(name="bank_label_id")
	public String getBankLabelID() {
		return bankLabelID;
	}

	@JSONField(name="bank_label_id")
	public void setBankLabelID(String bankLabelID) {
		this.bankLabelID = bankLabelID;
	}

	@JSONField(name="bank_label_name")
	public String getBankLabelName() {
		return bankLabelName;
	}

	@JSONField(name="bank_label_name")
	public void setBankLabelName(String bankLabelName) {
		this.bankLabelName = bankLabelName;
	}

	@JSONField(name="client_channel")
	public String getClientChannel() {
		return clientChannel;
	}

	@JSONField(name="client_channel")
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}

	@JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}

	@JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	@JSONField(name="merchant_user_name")
	public String getMerchantUserName() {
		return merchantUserName;
	}

	@JSONField(name="merchant_user_name")
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	@JSONField(name="market_id")
	public String getMarketId() {
		return marketId;
	}

	@JSONField(name="market_id")
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	@JSONField(name="is_active")
	public String getIsActive() {
		return isActive;
	}

	@JSONField(name="is_active")
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@JSONField(name="cut_money")
	public Integer getCutMoney() {
		return cutMoney;
	}

	@JSONField(name="cut_money")
	public void setCutMoney(Integer cutMoney) {
		this.cutMoney = cutMoney;
	}

	@JSONField(name="outlet_name")
	public String getOutletName() {
		return outletName;
	}

	@JSONField(name="outlet_name")
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	@JSONField(name="red_packet_id")
	public String getRedPacketId() {
		return redPacketId;
	}

	@JSONField(name="red_packet_id")
	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}

	@JSONField(name="cash_coupon_id")
	public String getCashCouponId() {
		return cashCouponId;
	}

	@JSONField(name="cash_coupon_id")
	public void setCashCouponId(String cashCouponId) {
		this.cashCouponId = cashCouponId;
	}

	@JSONField(name="consume_money")
	public Integer getConsumeMoney() {
		return consumeMoney;
	}

	@JSONField(name="consume_money")
	public void setConsumeMoney(Integer consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	
	@JSONField(name="discount_money")
	public Integer getDiscountMoney() {
		return discountMoney;
	}

	@JSONField(name="discount_money")
	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}

	@JSONField(name="is_pref_pay")
	public Integer getIsPrefPay() {
		return isPrefPay;
	}

	@JSONField(name="is_pref_pay")
	public void setIsPrefPay(Integer isPrefPay) {
		this.isPrefPay = isPrefPay;
	}

	@JSONField(name="give_money")
	public Integer getGiveMoney() {
		return giveMoney;
	}

	@JSONField(name="give_money")
	public void setGiveMoney(Integer giveMoney) {
		this.giveMoney = giveMoney;
	}
	
	
}
