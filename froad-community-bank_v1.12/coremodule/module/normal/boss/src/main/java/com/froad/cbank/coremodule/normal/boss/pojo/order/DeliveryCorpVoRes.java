package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;

public class DeliveryCorpVoRes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	   * 物流公司ID
	   */
	private long id; // optional
	/**
	   * 客户端ID
	   */
	private String clientId; // optional
	/**
	   * 名称
	   */
	public String name; // optional
	/**
	 * 物流公司编号
	 */
	private String corpCode;
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	  

}
