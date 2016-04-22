package com.froad.po;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;

/**
 * CbMerchantRole po. 
 */

@Guarded
public class MerchantRole implements java.io.Serializable {

	// Fields

	private Long id;
	@MaxLength(value = 32, message = "角色名称不能超过{max}个字符")
	private String name;
	@MaxLength(value = 64, message = "描述不能超过{max}个字符")
	private String description;
	private Boolean isDelete;

	// Constructors

	/** default constructor */
	public MerchantRole() {
	}

	/** minimal constructor */

	/** full constructor */

	// Property accessors
	
	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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