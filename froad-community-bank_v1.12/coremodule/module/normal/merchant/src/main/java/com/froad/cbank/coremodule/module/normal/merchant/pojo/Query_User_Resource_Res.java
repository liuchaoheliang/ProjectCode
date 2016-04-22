package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Query_User_Resource_Res {

	private Long id;
	private String resourceName;
	private int type;
	private Long parentResourceId;
	private Boolean status;
	private String resourceUrl;
	private String resourceIcon;
	private String treePath;
	private String resourceKey;
	private Boolean isSystem;
	private Boolean isMenu;
	private String platform;
	private Boolean isCheck;
	private List<Query_User_Resource_Res> finityResourceList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getParentResourceId() {
		return parentResourceId;
	}
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceIcon() {
		return resourceIcon;
	}
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public String getResourceKey() {
		return resourceKey;
	}
	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	public Boolean getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}
	public Boolean getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	public List<Query_User_Resource_Res> getFinityResourceList() {
		return finityResourceList;
	}
	public void setFinityResourceList(
			List<Query_User_Resource_Res> finityResourceList) {
		this.finityResourceList = finityResourceList;
	}

	

	
}
