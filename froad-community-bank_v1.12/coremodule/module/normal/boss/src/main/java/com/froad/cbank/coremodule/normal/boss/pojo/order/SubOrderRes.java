package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.util.List;

/**
 * 子订单响应对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月2日 下午2:01:53
 */
public class SubOrderRes {
	private String merchantName;//商户名称
	private String type;//子订单类型
	private String deliveryState;//配送状态-名优特惠有值
	private List<SubOrderProductRes> products;//商品集合
	private String merchantId;//商户ID
	private String deliveryCorpId;//接口通用后加上名优特惠的物流信息*
	private String deliveryCorpName;
	private String trackingNo;
	private Long shippingTime;
	private String subOrderId;
	private Double totalPrice;//订单金额
	private String refundState;//订单子状态
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
	public List<SubOrderProductRes> getProducts() {
		return products;
	}
	public void setProducts(List<SubOrderProductRes> products) {
		this.products = products;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getDeliveryCorpId() {
		return deliveryCorpId;
	}
	public void setDeliveryCorpId(String deliveryCorpId) {
		this.deliveryCorpId = deliveryCorpId;
	}
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public Long getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(Long shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
}
