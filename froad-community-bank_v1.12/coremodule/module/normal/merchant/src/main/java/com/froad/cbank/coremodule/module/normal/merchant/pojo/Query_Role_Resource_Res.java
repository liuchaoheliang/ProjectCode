package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

public class Query_Role_Resource_Res {

	private Long resource_id;
	private String resource_name;
	private String resource_type;
	private String tree_path;
	private String resource_url;
	private String icon;
	private List<Query_Role_Resource_Res> sub_list;

	public Long getResource_id() {
		return resource_id;
	}

	public void setResource_id(Long resource_id) {
		this.resource_id = resource_id;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}

	public String getResource_type() {
		return resource_type;
	}

	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}

	public String getTree_path() {
		return tree_path;
	}

	public void setTree_path(String tree_path) {
		this.tree_path = tree_path;
	}

	public String getResource_url() {
		return resource_url;
	}

	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Query_Role_Resource_Res> getSub_list() {
		return sub_list;
	}

	public void setSub_list(List<Query_Role_Resource_Res> sub_list) {
		this.sub_list = sub_list;
	}

}
