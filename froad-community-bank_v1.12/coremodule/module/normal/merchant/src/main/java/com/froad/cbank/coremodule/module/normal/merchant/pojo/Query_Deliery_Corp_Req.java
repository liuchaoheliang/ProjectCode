package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Deliery_Corp_Req extends BasePojo {

	private Long id; 
	/**
	 * id
	 */
	private String name; 
	/**
	 * name
	 */
	private String url; 
	/**
	 * url
	 */
	private Integer orderValue; 
	/**
	 * orderValue
	 */
	private Boolean isEnable; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}