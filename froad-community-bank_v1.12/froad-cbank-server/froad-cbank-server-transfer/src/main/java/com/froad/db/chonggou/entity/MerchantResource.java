package com.froad.db.chonggou.entity;

import net.sf.oval.constraint.MaxLength;

/**
 * CbMerchantResource po. 
 */


public class MerchantResource implements java.io.Serializable {

	// Fields

	private Long id;
	@MaxLength(20)
	private String clientId;
	@MaxLength(32)
	private String name;
	@MaxLength(32)
	private String icon;
	@MaxLength(64)
	private String url;
	private Boolean type;
	private Long parentId;
	@MaxLength(255)
	private String treePath;
	private Boolean isEnabled;
	@MaxLength(255)
	private String api;

	// Constructors

	/** default constructor */
	public MerchantResource() {
	}

	/** minimal constructor */
	public MerchantResource(String clientId, String name, String url, Boolean type, Boolean isEnabled) {
		this.clientId = clientId;
		this.name = name;
		this.url = url;
		this.type = type;
		this.isEnabled = isEnabled;
	}

	/** full constructor */
	public MerchantResource(String clientId, String name, String icon, String url, Boolean type, Long parentId, String treePath, Boolean isEnabled) {
		this.clientId = clientId;
		this.name = name;
		this.icon = icon;
		this.url = url;
		this.type = type;
		this.parentId = parentId;
		this.treePath = treePath;
		this.isEnabled = isEnabled;
	}

	// Property accessors
	
	
	
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

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public Boolean getType() {
		return this.type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

	
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	
	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}