package com.froad.db.chonggou.entity;

import net.sf.oval.constraint.MaxLength;

/**
 * CbMerchantRole po. 
 */


public class MerchantRole implements java.io.Serializable {

	// Fields

	private Long id;
	@MaxLength(20)
	private String clientId;
	@MaxLength(32)
	private String name;
	@MaxLength(64)
	private String description;
	private Boolean isDelete;

	// Constructors

	/** default constructor */
	public MerchantRole() {
	}

	/** minimal constructor */
	public MerchantRole(String clientId) {
		this.clientId = clientId;
	}

	/** full constructor */
	public MerchantRole(String clientId, String name, String description, Boolean isDelete) {
		this.clientId = clientId;
		this.name = name;
		this.description = description;
		this.isDelete = isDelete;
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

	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}