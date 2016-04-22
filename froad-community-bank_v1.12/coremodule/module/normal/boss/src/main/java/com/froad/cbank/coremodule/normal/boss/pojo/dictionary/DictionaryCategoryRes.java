package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

public class DictionaryCategoryRes {

	/**
	 * 字典类别id(自增主键)
	 */
	private Long id;
	/**
	 * 字典类别编号
	 */
	private String categoryCode;
	/**
	 * 字典类别名称
	 */
	private String categoryName;
	/**
	 * 字典类别描述
	 */
	private String depiction;
	/**
	 * 字典类别级别
	 */
	private String categoryLevel;
	/**
	 * 父字典类别ID
	 */
	private Long parentId;
	/**
	 * 树路径
	 */
	private String treePath;
	/**
	 * 是否有效
	 */
	private Boolean isEnable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	public String getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
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

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}
