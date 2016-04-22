package com.froad.fft.persistent.entity;

/**
 * 资源
 * @author FQ
 *
 */
public class SysResource extends BaseEntity {
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	
	//资源类型
	public enum Type {
		
		/**
		 * 功能
		 */
		function,
		
		/**
		 * 操作
		 */
		operation
		
	}
	
	private String name;// 资源名称
	private String icon;//资源图标
	private String code;//资源编码
	private String url;// 资源url
	private Type type;//资源类型
	
	private Long parentId;//上级资源
	private String treePath;// 树路径
	private Boolean isEnabled;//是否启用
	private Boolean isSystem;// 是否为系统内置资源
	
	private String description;// 描述
	private Integer orderValue;// 排序
	
	private Long clientId;//客户端
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTreePath() {
		return treePath;
	}
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Boolean getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
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
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
}
