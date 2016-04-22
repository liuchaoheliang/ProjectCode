package com.froad.cbank.coremodule.normal.boss.pojo.delivery;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class DeliverGoodsReq extends Page {
	
	/**
	  * 客户端ID
	  */
	private String clientId;
	
	/**
	 * 商品预售开始时间
	 */
	private String presellStartTime; 
	/**
	 * 商品预售结束时间
	 */
	private String presellEndTime; 
	/**
	 * 下单开始时间
	 */
	private String orderStartTime; 
	/**
	 * 下单结束时间
	 */
	private String orderEndTime; 
	/**
	 * 所属一级机构号
	 */
	private String fOrgCode; 
	/**
	 * 所属二级机构号
	 */
	private String sOrgCode;
	
	private Long userId;
	
	private String token;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPresellStartTime() {
		return presellStartTime;
	}
	public void setPresellStartTime(String presellStartTime) {
		this.presellStartTime = presellStartTime;
	}
	public String getPresellEndTime() {
		return presellEndTime;
	}
	public void setPresellEndTime(String presellEndTime) {
		this.presellEndTime = presellEndTime;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public String getFOrgCode() {
		return fOrgCode;
	}
	public void setFOrgCode(String fOrgCode) {
		this.fOrgCode = fOrgCode;
	}
	public String getSOrgCode() {
		return sOrgCode;
	}
	public void setSOrgCode(String sOrgCode) {
		this.sOrgCode = sOrgCode;
	} 
	
	
	
}
