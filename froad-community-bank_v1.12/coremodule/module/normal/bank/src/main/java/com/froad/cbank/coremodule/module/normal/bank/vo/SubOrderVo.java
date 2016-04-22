package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

public class SubOrderVo  implements Serializable {

	private static final long serialVersionUID = 1641416665036951891L;
	
	private String merchantName;  //商户名称
	private String merchantId;   //商户ID
	private String type;    //子订单类型
	private String deliveryState; //配送状态
	private String deliveryCorpId;//物流ID
	private String deliveryCorpName;//物流名称
	private String trackingNo;//物流单号
	private String shippingTime;//创建时间
	private String subOrderId;//订单号
	private String totalPrice; // 订单金额
	private String refundState; // 订单子状态

	private List<SubProductVo> productList;//商品
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	public String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public List<SubProductVo> getProductList() {
		return productList;
	}
	public void setProductList(List<SubProductVo> productList) {
		this.productList = productList;
	}

}
