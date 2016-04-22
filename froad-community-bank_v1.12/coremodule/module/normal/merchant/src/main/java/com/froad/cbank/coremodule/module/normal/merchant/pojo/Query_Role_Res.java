package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Role_Res {

	/**
	 * 主键ID
	 */
	private long id; 
	/**
	 * 客户端ID
	 */
	private long clientId; 
	/**
	 * 角色名称
	 */
	private String name; 
	/**
	 * 描述
	 */
	private String description; 
	/**
	 * 是否删除
	 */
	private boolean isDelete;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	} 
	
}
