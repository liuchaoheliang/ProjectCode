package com.froad.fft.persistent.entity;

/**
 * 商户相册
 * @author FQ
 *
 */
public class MerchantPhoto extends BaseEntity {

	private String title;//标题
	private String url;//储存url
	private Integer width;//宽度 
	private Integer height;//高度 
	private Integer orderValue;//排序
	private String description;//描述
	
	private Long merchantOutletId;//门店ID

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMerchantOutletId() {
		return merchantOutletId;
	}

	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	
	
	
}
