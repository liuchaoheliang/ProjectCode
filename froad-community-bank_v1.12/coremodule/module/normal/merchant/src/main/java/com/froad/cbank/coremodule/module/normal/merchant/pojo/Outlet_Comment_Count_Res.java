package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Outlet_Comment_Count_Res {
	/**
	 * 门店id
	 */
	private String outletId; 
	/**
	 * 门店名称
	 */
	private String outletName; 
	/**
	 * 星级 1 的数量
	 */
	private Integer levelAmountOne; 
	/**
	 * 星级 2 的数量
	 */
	private Integer levelAmountTwo; 
	/**
	 * 星级 3 的数量
	 */
	private Integer levelAmountThree; 
	/**
	 * 星级 4 的数量
	 */
	private Integer levelAmountFour; 
	/**
	 * 星级 5 的数量
	 */
	private Integer levelAmountFive; 
	private Double center;

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Integer getLevelAmountOne() {
		return levelAmountOne;
	}

	public void setLevelAmountOne(Integer levelAmountOne) {
		this.levelAmountOne = levelAmountOne;
	}

	public Integer getLevelAmountTwo() {
		return levelAmountTwo;
	}

	public void setLevelAmountTwo(Integer levelAmountTwo) {
		this.levelAmountTwo = levelAmountTwo;
	}

	public Integer getLevelAmountThree() {
		return levelAmountThree;
	}

	public void setLevelAmountThree(Integer levelAmountThree) {
		this.levelAmountThree = levelAmountThree;
	}

	public Integer getLevelAmountFour() {
		return levelAmountFour;
	}

	public void setLevelAmountFour(Integer levelAmountFour) {
		this.levelAmountFour = levelAmountFour;
	}

	public Integer getLevelAmountFive() {
		return levelAmountFive;
	}

	public void setLevelAmountFive(Integer levelAmountFive) {
		this.levelAmountFive = levelAmountFive;
	}

	public Double getCenter() {
		return center;
	}

	public void setCenter(Double center) {
		this.center = center;
	}

}
