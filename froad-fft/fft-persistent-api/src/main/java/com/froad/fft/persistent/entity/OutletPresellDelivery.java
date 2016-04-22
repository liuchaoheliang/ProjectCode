package com.froad.fft.persistent.entity;

import java.io.Serializable;

/**
 * 商户门店和提货点（目前一对一）
 * @author FQ
 *
 */
public class OutletPresellDelivery implements Serializable{
	
	private Long merchantOutletId;//商户门店ID
	private Long presellDeliveryId;//预售提货点ID
	
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}
	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	public Long getPresellDeliveryId() {
		return presellDeliveryId;
	}
	public void setPresellDeliveryId(Long presellDeliveryId) {
		this.presellDeliveryId = presellDeliveryId;
	}
	
}
