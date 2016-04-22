package com.froad.po;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;

/**
 * CbMerchantOutletPhoto po. 
 */

@Guarded
public class MerchantOutletPhoto implements java.io.Serializable {

	// Fields

	private Long id;
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	private String merchantId;
	@MaxLength(value = 20, message = "门店id不能超过{max}个字符")
	private String outletId;
	private Boolean isDefault;
	@MaxLength(value = 32, message = "标题不能超过{max}个字符")
	private String title;
	@MaxLength(value = 255, message = "原图片URL不能超过{max}个字符")
	private String source;
	@MaxLength(value = 255, message = "大图片URL不能超过{max}个字符")
	private String large;
	@MaxLength(value = 255, message = "中图片URL不能超过{max}个字符")
	private String medium;
	@MaxLength(value = 255, message = "缩略图URL不能超过{max}个字符")
	private String thumbnail;

	// Constructors

	/** default constructor */
	public MerchantOutletPhoto() {
	}

	/** minimal constructor */
	public MerchantOutletPhoto(String merchantId, String outletId, Boolean isDefault) {
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.isDefault = isDefault;
	}

	/** full constructor */
	public MerchantOutletPhoto(String merchantId, String outletId, Boolean isDefault, String title, String source, String large, String medium, String thumbnail) {
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.isDefault = isDefault;
		this.title = title;
		this.source = source;
		this.large = large;
		this.medium = medium;
		this.thumbnail = thumbnail;
	}

	// Property accessors
	
	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	
	public String getOutletId() {
		return this.outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	
	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	public String getLarge() {
		return this.large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	
	public String getMedium() {
		return this.medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	
	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}