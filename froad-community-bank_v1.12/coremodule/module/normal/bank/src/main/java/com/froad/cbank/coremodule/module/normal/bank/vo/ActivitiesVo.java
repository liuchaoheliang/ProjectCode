package com.froad.cbank.coremodule.module.normal.bank.vo;

public class ActivitiesVo {

	private String ActivitiesType;  //活动类型
	private String vipPrice;   //vip价格
	private String points;    //赠送积分
	
	public String getActivitiesType() {
		return ActivitiesType;
	}
	public void setActivitiesType(String activitiesType) {
		ActivitiesType = activitiesType;
	}
	public String getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	
	
}
