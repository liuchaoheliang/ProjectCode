package com.froad.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActiveResultInfo implements Serializable {
	/**
	 * @Fields id : 商品/商户ID
	 */
	private String id;

	/**
	 * @Fields activeId : 活动ID
	 */
	private String activeId;

	/**
	 * @Fields activeName : 活动名称
	 */
	private String activeName;

	/**
	 * @Fields activeType : 活动类型
	 */
	private String activeType;

	/**
	 * @Fields description : 活动描述
	 */
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
