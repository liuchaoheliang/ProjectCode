package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户优惠活动
 * @author FQ
 *
 */
public class MerchantPreferentialDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String title;//标题
	private String content;//内容
	private Integer orderValue;//排序

	private Long merchantOutletId;//门店ID
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	
}
