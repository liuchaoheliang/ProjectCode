package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ProductCategoryListVo extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 客户端id */
	private String clientId;
	/** 分类名称 */
	private String name;
	/** 二级id */
	private String id;
	/** 上级id */
	private String parentId;
	/** 启用状态 */
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
