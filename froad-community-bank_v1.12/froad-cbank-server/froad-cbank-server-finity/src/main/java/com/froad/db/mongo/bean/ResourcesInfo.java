package com.froad.db.mongo.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

/**
 * 
 * @ClassName: ResourcesInfo 
 * @Description: 资源mongo类
 * @author: huangyihao
 * @date: 2015年3月25日
 */
public class ResourcesInfo {
	
	
	
	private Long resourceId;		//资源ID
	private String resourceName;	// 资源名称
	private String resourceType;	// 资源类型
	private String treePath;	// 资源路径
	private String resourceIcon; // 资源图标
	private String resourceUrl; // 资源url
	private Long parentResourceId; //父级资源id
	private String api;//资源接口
	private Integer orderValue;//资源排序

	
	
	@JSONField(name="parent_resource_id")
	public Long getParentResourceId() {
		return parentResourceId;
	}

	@JSONField(name="parent_resource_id")
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	@JSONField(name="resource_id")
	public Long getResourceId() {
		return resourceId;
	}
	
	@JSONField(name="resource_id")
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@JSONField(name="resource_name")
	public String getResourceName() {
		return resourceName;
	}

	@JSONField(name="resource_name")
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@JSONField(name="resource_type")
	public String getResourceType() {
		return resourceType;
	}

	@JSONField(name="resource_type")
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@JSONField(name="tree_path")
	public String getTreePath() {
		return treePath;
	}

	@JSONField(name="tree_path")
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	@JSONField(name="resource_icon")
	public String getResourceIcon() {
		return resourceIcon;
	}

	@JSONField(name="resource_icon")
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}

	@JSONField(name="resource_url")
	public String getResourceUrl() {
		return resourceUrl;
	}

	@JSONField(name="resource_url")
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	@JSONField(name="api")
	public String getApi() {
		return api;
	}
	@JSONField(name="api")
	public void setApi(String api) {
		this.api = api;
	}
	@JSONField(name="order_value")
	public Integer getOrderValue() {
		return orderValue;
	}
	@JSONField(name="order_value")
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	@Override
	public String toString() {
		return JSonUtil.toJSonString(this);
	}

	
}
