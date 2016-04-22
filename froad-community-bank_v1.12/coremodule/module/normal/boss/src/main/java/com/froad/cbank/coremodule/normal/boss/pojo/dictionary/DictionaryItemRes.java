package com.froad.cbank.coremodule.normal.boss.pojo.dictionary;

public class DictionaryItemRes {

	/**
	 * 字典条目id
	 */
	private Long id;
	/**
	 * 字典条目编号
	 */
	private String itemCode;
	/**
	 * 字典条目名称
	 */
	private String itemName;
	/**
	 * 字典条目值
	 */
	private String itemValue;
	/**
	 * 字典描述
	 */
	private String depiction;
	/**
	 * 字典ID
	 */
	private Long dicId;
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 客户端名称
	 */
	private String clientName;
	
	/**
	 * 字典名称
	 */
	private String dicName;
	
	
	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

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

	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
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
