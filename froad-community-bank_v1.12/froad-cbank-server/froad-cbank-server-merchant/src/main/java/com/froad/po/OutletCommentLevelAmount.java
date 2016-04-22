package com.froad.po;

import java.io.Serializable;

public class OutletCommentLevelAmount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String outletId; // required
	public String outletName; // required
	
	public int levelAmountOne; // required
	public int levelAmountTwo; // required
	public int levelAmountThree; // required
	public int levelAmountFour; // required
	public int levelAmountFive; // required
	
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
	public int getLevelAmountOne() {
		return levelAmountOne;
	}
	public void setLevelAmountOne(int levelAmountOne) {
		this.levelAmountOne = levelAmountOne;
	}
	public int getLevelAmountTwo() {
		return levelAmountTwo;
	}
	public void setLevelAmountTwo(int levelAmountTwo) {
		this.levelAmountTwo = levelAmountTwo;
	}
	public int getLevelAmountThree() {
		return levelAmountThree;
	}
	public void setLevelAmountThree(int levelAmountThree) {
		this.levelAmountThree = levelAmountThree;
	}
	public int getLevelAmountFour() {
		return levelAmountFour;
	}
	public void setLevelAmountFour(int levelAmountFour) {
		this.levelAmountFour = levelAmountFour;
	}
	public int getLevelAmountFive() {
		return levelAmountFive;
	}
	public void setLevelAmountFive(int levelAmountFive) {
		this.levelAmountFive = levelAmountFive;
	}
	
}
