package com.froad.db.mongo.bean;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class MerchantResource implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long resource_id; // optional
	/**
	 * resource_id
	 */
	public String resource_name; // optional
	/**
	 * resource_name
	 */
	public String resource_type; // optional
	/**
	 * resource_type
	 */
	public String tree_path; // optional
	/**
	 * tree_path
	 */
	public String resource_url; // optional
	
	public Long parent_id; // optional
	
	public String icon;
	
	public String api;
	
	public Integer order_value;
	
	@JSONField(name="icon")
	public String getIcon() {
		return icon;
	}
	
	@JSONField(name="icon")
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@JSONField(name="resource_id")
	public long getResource_id() {
		return resource_id;
	}
	@JSONField(name="resource_id")
	public void setResource_id(long resource_id) {
		this.resource_id = resource_id;
	}
	@JSONField(name="resource_name")
	public String getResource_name() {
		return resource_name;
	}
	@JSONField(name="resource_name")
	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}
	@JSONField(name="resource_type")
	public String getResource_type() {
		return resource_type;
	}
	@JSONField(name="resource_type")
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	@JSONField(name="tree_path")
	public String getTree_path() {
		return tree_path;
	}
	@JSONField(name="tree_path")
	public void setTree_path(String tree_path) {
		this.tree_path = tree_path;
	}
	@JSONField(name="resource_url")
	public String getResource_url() {
		return resource_url;
	}
	@JSONField(name="resource_url")
	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}
	@JSONField(name="parent_id")
	public Long getParent_id() {
		return parent_id;
	}
	@JSONField(name="parent_id")
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
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
	public Integer getOrder_value() {
		return order_value;
	}

	@JSONField(name="order_value")
	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}
	  
	  
}
