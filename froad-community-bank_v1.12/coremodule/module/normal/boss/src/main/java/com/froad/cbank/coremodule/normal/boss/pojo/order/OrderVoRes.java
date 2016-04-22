package com.froad.cbank.coremodule.normal.boss.pojo.order;

/**
 * 订单信息
 */
public class OrderVoRes {
	private Long memberCode;//用户编号
	private String orderStatus;//订单状态
	private String clientId;//所属客户端
	private String paymentMethod;//支付方式
	private String createSource;//订单来源
	private Long paymentTime;//支付时间
	private String merchantUserId;//收银交易操作人
	private Double totalPrice;//总金额
	private Integer pointType;//积分类型
	private Double points;//积分金额
	private Double realPrice;//现金金额
	private String pointRate;//积分兑换比例
	private Double givePoints;//赠送积分数
	private String remark;//备注
	private Integer isQrcode;//是否面对面-面对面没有详细内容
	private String orderId;//订单号
	private Long createTime;
	private String clientName;
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	public Long getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getPointType() {
		return pointType;
	}
	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public String getPointRate() {
		return pointRate;
	}
	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}
	public Double getGivePoints() {
		return givePoints;
	}
	public void setGivePoints(Double givePoints) {
		this.givePoints = givePoints;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsQrcode() {
		return isQrcode;
	}
	public void setIsQrcode(Integer isQrcode) {
		this.isQrcode = isQrcode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
