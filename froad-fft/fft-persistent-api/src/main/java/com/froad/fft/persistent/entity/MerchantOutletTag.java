package com.froad.fft.persistent.entity;

/**
 * 商户标签
 * @author FQ
 *
 */
public class MerchantOutletTag extends BaseEntity{
	
	private Long merchantOutletId;//商户门店ID
	private Long tagId;//标签Id
	
	private Integer orderValue;//排序
	
	
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

}
