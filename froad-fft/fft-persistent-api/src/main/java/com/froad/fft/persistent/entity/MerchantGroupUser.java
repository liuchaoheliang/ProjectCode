package com.froad.fft.persistent.entity;

/**
 * 商户的组用户
 * @author FQ
 *
 */
public class MerchantGroupUser extends BaseEntity {
	
	private Long merchantId;//商户ID
	private Long merchantOutletId;//门店ID
	
	private Long sysUserId;//用户ID
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}

	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
}
