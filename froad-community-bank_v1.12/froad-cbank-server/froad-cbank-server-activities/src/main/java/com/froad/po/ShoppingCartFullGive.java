package com.froad.po;

import java.io.Serializable;

public class ShoppingCartFullGive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 满赠活动id */
	private String fullGiveActiveId;
	/** 满赠活动名称 */
	private String fullGiveActiveName;
	/** 赠送类型 */
	private String giveType;
	/** 赠送金额 */
	private Double giveMoney;
	
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
	public String getFullGiveActiveId() {
		return fullGiveActiveId;
	}
	public void setFullGiveActiveId(String fullGiveActiveId) {
		this.fullGiveActiveId = fullGiveActiveId;
	}
	public String getFullGiveActiveName() {
		return fullGiveActiveName;
	}
	public void setFullGiveActiveName(String fullGiveActiveName) {
		this.fullGiveActiveName = fullGiveActiveName;
	}
	
}
