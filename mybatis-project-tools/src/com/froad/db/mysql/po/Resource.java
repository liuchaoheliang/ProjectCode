package com.froad.db.mysql.po;

public class Resource {
    private Long id;

    private String clientId;

    private String resourceName;

    private Boolean resourceType;

    private Long parentResourceId;

    private Boolean status;

    private String resourceUrl;

    private String resourceIcon;

    private String treePath;

    private Boolean isDelete;

    private String api;

    private Integer orderValue;

    private String resourceKey;

    private String platform;

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
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public Boolean getResourceType() {
        return resourceType;
    }

    public void setResourceType(Boolean resourceType) {
        this.resourceType = resourceType;
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

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey == null ? null : resourceKey.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }
}