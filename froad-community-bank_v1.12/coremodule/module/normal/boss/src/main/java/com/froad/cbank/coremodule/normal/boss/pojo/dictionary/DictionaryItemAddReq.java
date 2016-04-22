package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;


public class DictionaryItemAddReq{
	
	/**
	 * id (修改时用)
	 */
	private Long id;
	
	/**
	 * 字典类别
	 */
	@NotEmpty(value="条目编码不能为空")
	private String itemCode;
	/**
	 * 字典编号
	 */
	@NotEmpty(value="条目名称不能为空")
	private String itemName;
	/**
	 * 字典名称
	 */
	@NotEmpty(value="条目值不能为空")
	private String itemValue;
	
	/**
	 * 字典
	 */
	@NotEmpty(value="字典不能为空")
	private Long dicId;
	
	/**
	 * 客户端
	 */
	@NotEmpty(value="客户端不能为空")
	private String clientId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
}
