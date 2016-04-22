package com.froad.db.chonggou.entity;


/**
 * CbMerchantCategory po. 
 */


public class MerchantCategoryCG implements java.io.Serializable {

	// Fields

	private Long id;
	private String clientId;
	private Long parentId;
	private String name;
	private String treePath;
	private String icoUrl;
	private Boolean isDelete;
	private Integer orderValue;

	// Constructors

	/** default constructor */
	public MerchantCategoryCG() {
	}


	// Property accessors
	
	
	
	public Integer getOrderValue() {
		return orderValue;
	}


	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	
	public String getIcoUrl() {
		return this.icoUrl;
	}

	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}

	
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}