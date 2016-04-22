package com.froad.fft.persistent.entity;


/**
 * 角色
 * @author FQ
 *
 */
public class SysRole extends BaseEntity {
	
	private String name;// 角色名称
	private String value;// 角色标识  
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
