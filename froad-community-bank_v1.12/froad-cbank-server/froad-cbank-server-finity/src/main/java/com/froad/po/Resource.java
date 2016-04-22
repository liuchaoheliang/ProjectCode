package com.froad.po;

import java.util.Date;


public class Resource {
    private Long id;

    private String resourceName;

    private Integer type;

    private Long parentResourceId;

    private Boolean status;

    private String resourceUrl;

    private String resourceIcon;

    private String treePath;

    private Boolean isDelete;

    private String api;

    private Integer orderValue;

    private Boolean isSystem;

    private Boolean isMenu;

    private String platform;

    private String resourceKey;
    
    private Date updateTime;
    
    /** 是否需要数据权限控制 0-否 1-是  */ 
    private Boolean isLimit;

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
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon == null ? null : resourceIcon.trim();
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath == null ? null : treePath.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api == null ? null : api.trim();
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
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
        this.platform = platform == null ? null : platform.trim();
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey == null ? null : resourceKey.trim();
    }

	public Boolean getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
    
}