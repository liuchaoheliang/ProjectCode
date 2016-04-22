package com.froad.db.ahui.entity;


import java.util.Date;

import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.Trans;

public class TransDto extends Trans {

	private static final long serialVersionUID = -4596046915042888760L;

	private Long transId;
	private Long productId;
	private String productName;
	private Integer quantity;
	private String price;
	private String single;
	private Long supplyMerchantId;
	private Long gatherMerchantId;
	
	private Date detailCreateTime;
	
	private Date detailUpdateTime;
	
	private Long subOrderId;
	
	private Date paymentTime;
	
	private TransType type;
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public Long getSupplyMerchantId() {
		return supplyMerchantId;
	}
	public void setSupplyMerchantId(Long supplyMerchantId) {
		this.supplyMerchantId = supplyMerchantId;
	}
	public Long getGatherMerchantId() {
		return gatherMerchantId;
	}
	public void setGatherMerchantId(Long gatherMerchantId) {
		this.gatherMerchantId = gatherMerchantId;
	}
	public Date getDetailCreateTime() {
		return detailCreateTime;
	}
	public void setDetailCreateTime(Date detailCreateTime) {
		this.detailCreateTime = detailCreateTime;
	}
	public Date getDetailUpdateTime() {
		return detailUpdateTime;
	}
	public void setDetailUpdateTime(Date detailUpdateTime) {
		this.detailUpdateTime = detailUpdateTime;
	}
	public Long getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(Long subOrderId) {
		this.subOrderId = subOrderId;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public TransType getType() {
		return type;
	}
	public void setType(TransType type) {
		this.type = type;
	}
	
	
	
}
