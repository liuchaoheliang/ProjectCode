package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;


public class DictionaryAddReq{
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 字典类别
	 */
	@NotEmpty(value="所属类别不能为空")
	private Long categoryId;
	/**
	 * 字典编号
	 */
	@NotEmpty(value="字典编码不能为空")
	private String dicCode;
	/**
	 * 字典名称
	 */
	@NotEmpty(value="字典名称不能为空")
	private String dicName;
	
	/**
	 * 描述
	 */
	private String depiction;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepiction() {
		return depiction;
	}
	public void setDepiction(String depiction) {
		this.depiction = depiction;
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
	
}
