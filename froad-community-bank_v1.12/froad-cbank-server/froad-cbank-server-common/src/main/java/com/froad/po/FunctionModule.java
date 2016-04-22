/**
 * Project Name:froad-cbank-server-boss
 * File Name:FunctionModule.java
 * Package Name:com.froad.po
 * Date:2015年9月18日上午9:54:09
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Date;

/**
 * ClassName:FunctionModule
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 上午9:54:09
 * @author   kevin
 * @version  
 * @see 	 
 */
public class FunctionModule {

	private Long id;
	private String clientId; 
	private Integer type;
	private String moduleName;
	private String moduleAlias;
	private String iconUrl;
	private Integer sortValue;
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleAlias() {
		return moduleAlias;
	}
	public void setModuleAlias(String moduleAlias) {
		this.moduleAlias = moduleAlias;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getSortValue() {
		return sortValue;
	}
	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
