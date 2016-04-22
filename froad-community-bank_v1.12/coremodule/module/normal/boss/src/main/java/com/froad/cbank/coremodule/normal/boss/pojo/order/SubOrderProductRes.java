package com.froad.cbank.coremodule.normal.boss.pojo.order;

/**
 * 订单商品响应对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月2日 下午2:13:28
 */
public class SubOrderProductRes {
	private String productId;//商品编号
	private String productName;//商品名称
	private Double money;//商品单价
	private Double vipMoney;//商品VIP价格
	private Integer quantity;//普通购买数量
	private Integer vipQuantity;//VIP购买数量
	private Double points;//赠送积分
	private String deliveryOption;//配送或者自提方式
	private Double deliveryMoney;//运费
	private Long startTime;//团购预售（券的有效期-开始时间）
	private Long endTime;//团购预售（券的有效期-结束时间）
	private Double subTotalMoney;//小计
	private Integer refundNumber;//退款数量
	private Integer surplusNumber;//剩余数量
	private String deliveryState;//配送或者自提状态
	private Integer consumeStatus;//预售和团购的消费状态 1.未消费， 2.已消费， 3.部分消费
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getVipMoney() {
		return vipMoney;
	}
	public void setVipMoney(Double vipMoney) {
		this.vipMoney = vipMoney;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public Double getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Double getSubTotalMoney() {
		return subTotalMoney;
	}
	public void setSubTotalMoney(Double subTotalMoney) {
		this.subTotalMoney = subTotalMoney;
	}
	public Integer getRefundNumber() {
		return refundNumber;
	}
	public void setRefundNumber(Integer refundNumber) {
		this.refundNumber = refundNumber;
	}
	public Integer getSurplusNumber() {
		return surplusNumber;
	}
	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}
	public String getDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
	public Integer getConsumeStatus() {
		return consumeStatus;
	}
	public void setConsumeStatus(Integer consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
}
