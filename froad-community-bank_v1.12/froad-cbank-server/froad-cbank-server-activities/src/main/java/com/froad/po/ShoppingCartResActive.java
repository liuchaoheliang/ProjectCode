package com.froad.po;

import java.io.Serializable;

/**
 * 购物车响应活动
 */
public class ShoppingCartResActive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 活动id */  
	private String activeId;
	/** 活动名称 */   
	private String activeName;
	/** 活动类型 */ 
	private String activeType;
	/** 满减金额 */   
	private Double cutMoney;
	/** 活动状态 */   
	private String activeStatus;
	/** 状态描述 */
	private String statusMsg;
	/** 是否凑单 */
	private Boolean isMinato;
	/** 是否最低删除 */
	private Boolean isLowestDelete;
	/** 赠送类型 */
	private String giveType;
	/** 赠送金额 */
	private Double giveMoney;
	
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public Double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Boolean getIsMinato() {
		return isMinato;
	}
	public void setIsMinato(Boolean isMinato) {
		this.isMinato = isMinato;
	}
	public Boolean getIsLowestDelete() {
		return isLowestDelete;
	}
	public void setIsLowestDelete(Boolean isLowestDelete) {
		this.isLowestDelete = isLowestDelete;
	}
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	public Double getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}
	
}
