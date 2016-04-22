package com.froad.db.chonggou.entity;

import net.sf.oval.constraint.MaxLength;
public class MerchantOutletPhotoCG implements java.io.Serializable{


	// Fields

	/**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String merchantId;
	private String outletId;
	private Boolean isDefault;
	private String title;
	private String source;
	private String large;
	private String medium;
	private String thumbnail;

	// Constructors

	/** default constructor */
	public MerchantOutletPhotoCG() {
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

