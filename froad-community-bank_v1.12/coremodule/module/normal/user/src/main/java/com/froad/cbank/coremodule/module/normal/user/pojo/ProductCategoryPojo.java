package com.froad.cbank.coremodule.module.normal.user.pojo;

public class ProductCategoryPojo {

	private String id;
	private String clientId;
	private String name;
	private String parentId;
	private String treePath;
	private String icoUrl;
	private Boolean isHaveChild;
	
	public Boolean getIsHaveChild() {
		return isHaveChild;
	}
	public void setIsHaveChild(Boolean isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
}
