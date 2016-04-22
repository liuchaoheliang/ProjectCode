package com.froad.cbank.coremodule.normal.boss.pojo.system;


public class FinityResourceVoReq {
	
	private Long id;//主键id
	private String resourceName; //资源名称
	private Integer type;//资源类型(1、平台，2、模块，3、元素)
	private Long parentResourceId;//父级资源Id(顶级资源的父级资源ID为0)
	private String resourceUrl;//资源url
	private String resourceIcon;//资源图标
	private String treePath;//资源路径(逗号分隔)
	private Boolean isDelete;//是否删除(false-未删除 true-删除)
	private Integer orderValue;//排序值
	private Boolean isSystem;//是否系统资源true-是,false-否
	private Boolean isMenu;//是否为菜单true-是，false-否
	private String platform;//平台名称（boss、bank、merchant）
	private String resource_key;//资源key
	private Long update_time;//更新时间
	private Boolean is_limit;//是否需要数据权限控制
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
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
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
		this.platform = platform;
	}
	public String getResource_key() {
		return resource_key;
	}
	public void setResource_key(String resource_key) {
		this.resource_key = resource_key;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	public Boolean getIs_limit() {
		return is_limit;
	}
	public void setIs_limit(Boolean is_limit) {
		this.is_limit = is_limit;
	}
	
	
	
	
	
}
