package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * @author FQ
 *
 */
public class SysRoleDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String name;// 角色名称
	private String value;// 角色标识  
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
