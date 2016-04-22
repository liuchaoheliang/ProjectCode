package com.froad.po;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;


/**
 * CbMerchantType po. 
 */

@Guarded
public class MerchantType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@MaxLength(value = 32, message = "类型名称不能超过{max}个字符")
	private String typeName;
	private Boolean isDelete;
	private String type;

	private String clientId;
	// Constructors

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** default constructor */
	public MerchantType() {
	}

	/** full constructor */
	public MerchantType(String typeName, Boolean isDelete) {
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