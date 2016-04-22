package com.froad.po;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;


/**
 * CbMerchantCategory po. 
 */

@Guarded
public class MerchantCategory implements java.io.Serializable {

	// Fields

	private Long id;
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	private Long parentId;
	@MaxLength(value = 32, message = "商户分类名称不能超过{max}个字符")
	private String name;
	@MaxLength(value = 255, message = "树路径不能超过{max}个字符")
	private String treePath;
	@MaxLength(value = 64, message = "商户分类图标不能超过{max}个字符")
	private String icoUrl;
	private Boolean isDelete;
	private Integer orderValue;

	// Constructors

	/** default constructor */
	public MerchantCategory() {
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