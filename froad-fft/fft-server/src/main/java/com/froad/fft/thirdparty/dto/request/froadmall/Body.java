package com.froad.fft.thirdparty.dto.request.froadmall;

import java.util.List;

public class Body {

	private String area;
	
	private String operater;
	
	private String CZNo;
	
	private String money;
	
	private String salePrice;

	private String tranID;
	
	private List<Lottery> lotteryList;
	
	private String description;
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getCZNo() {
		return CZNo;
	}

	public void setCZNo(String cZNo) {
		CZNo = cZNo;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getTranID() {
		return tranID;
	}

	public void setTranID(String tranID) {
		this.tranID = tranID;
	}

	public List<Lottery> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<Lottery> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
