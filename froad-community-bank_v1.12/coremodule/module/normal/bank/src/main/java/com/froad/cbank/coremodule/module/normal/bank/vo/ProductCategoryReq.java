package com.froad.cbank.coremodule.module.normal.bank.vo;

public class ProductCategoryReq {
	
	/**
	 * id
	 */
	private Long id; 
	/**
	 * 客户端id
	 */
	private String clientId; 
	/**
	 * name
	 */
	private String name; 
	/**
	 * 树路径 空格间隔
	 */
	private String treePath; 
	/**
	 * parentId
	 */
	private Long parentId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	} 
}
