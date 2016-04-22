package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;


public class DictionaryRes{
	
	private String id;
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
	 * 描述
	 */
	private String depiction;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}
	
}
