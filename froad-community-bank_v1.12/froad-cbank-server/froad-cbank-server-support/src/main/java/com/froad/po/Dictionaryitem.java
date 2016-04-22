package com.froad.po;

import java.io.Serializable;

public class Dictionaryitem implements Serializable{
	
	  /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5133074553958827302L;
	/**
	   * 自增主键ID
	   */
	  public long id; // required
	  /**
	   * 字典条目编号
	   */
	  public String itemCode; // required
	  /**
	   * 字典条目名称
	   */
	  public String itemName; // required
	  /**
	   * 字典条目值
	   */
	  public String itemValue; // required
	  /**
	   * 字典描述
	   */
	  public String depiction; // required
	  /**
	   * 字典ID
	   */
	  public long dicId; // required
	  /**
	   * 客户端ID
	   */
	  public String clientId; // required
	  /**
	   * 排序值
	   */
	  public short orderValue; // required
	  /**
	   * 是否有效
	   */
	  public String isEnable; // required
	  
	public Dictionaryitem() {
		super();
	}
	public Dictionaryitem(long id, String itemCode, String itemName,
			String itemValue, String depiction, long dicId, String clientId,
			short orderValue, String isEnable) {
		super();
		this.id = id;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.depiction = depiction;
		this.dicId = dicId;
		this.clientId = clientId;
		this.orderValue = orderValue;
		this.isEnable = isEnable;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public long getDicId() {
		return dicId;
	}
	public void setDicId(long dicId) {
		this.dicId = dicId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public short getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(short orderValue) {
		this.orderValue = orderValue;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
	
	
	

}
