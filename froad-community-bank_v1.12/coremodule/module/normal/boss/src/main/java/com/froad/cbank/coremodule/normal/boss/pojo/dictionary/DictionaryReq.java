package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class DictionaryReq extends Page{

	/**
	 * 字典类别
	 */
	private Long categoryId;
	/**
	 * 字典编号
	 */
	private String dicCode;
	/**
	 * 字典名称
	 */
	private String dicName;
	
	/**
	 * 查询类型 1-当前类别下 2-当前类别(包括子类)下
	 */
	private Integer type;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
