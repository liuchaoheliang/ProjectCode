package com.froad.fft.persistent.entity;

/**
 * 商户优惠活动
 * @author FQ
 *
 */
public class MerchantPreferential extends BaseEntity {
	
	private String title;//标题
	private String content;//内容
	private Integer orderValue;//排序

	private Long merchantOutletId;//门店ID
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	
	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

}
