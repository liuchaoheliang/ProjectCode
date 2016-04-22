package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class DictionaryItemReq extends Page{
	
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 字典id
	 */
	private Long dicId;
	/**
	 * 条目编码
	 */
	private String itemCode;
	/**
	 * 条目名称
	 */
	private String itemName;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDicId() {
		return dicId;
	}
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
