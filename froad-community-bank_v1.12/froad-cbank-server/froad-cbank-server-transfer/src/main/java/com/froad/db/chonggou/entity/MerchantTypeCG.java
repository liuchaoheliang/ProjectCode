package com.froad.db.chonggou.entity;


/**
 * CbMerchantType po. 
 */


public class MerchantTypeCG implements java.io.Serializable {

	// Fields

	private Long id;
	private String typeName;
	private Boolean isDelete;

	// Constructors

	/** default constructor */
	public MerchantTypeCG() {
	}

	/** full constructor */
	public MerchantTypeCG(String typeName, Boolean isDelete) {
		this.typeName = typeName;
		this.isDelete = isDelete;
	}

	// Property accessors
	
	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}