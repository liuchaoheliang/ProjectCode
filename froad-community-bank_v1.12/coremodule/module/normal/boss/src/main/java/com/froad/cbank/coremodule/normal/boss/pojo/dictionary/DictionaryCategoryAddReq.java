package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class DictionaryCategoryAddReq {
	
	/**
	 * id (修改用)
	 */
	private Long id;
	/**
	 * 字典类别名称
	 */
	@NotEmpty(value="分类名称不能为空")
	private String categoryName;
	/**
	 * 字典类别描述
	 */
	private String depiction;
	/**
	 * 字典类别级别
	 */
	@NotEmpty(value="分类级别不能为空")
	private String categoryLevel;
	/**
	 * 父字典类别ID
	 */
	@NotEmpty(value="上级分类不能为空")
	private Long parentId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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


}
