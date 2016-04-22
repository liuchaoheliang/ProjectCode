package com.froad.CB.po.transaction;


	/**
	 * 类描述：积分提现信息
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Feb 28, 2014 10:24:13 PM 
	 */
public class WithdrawPoints {

	private String cashPointsNo;
	private String objectNo;//订单号
	private String objectDes;//订单描述
	private String objectType;//类型：订单
	private String points;//提现积分数
	
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getCashPointsNo() {
		return cashPointsNo;
	}
	public void setCashPointsNo(String cashPointsNo) {
		this.cashPointsNo = cashPointsNo;
	}
	public String getObjectNo() {
		return objectNo;
	}
	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}
	public String getObjectDes() {
		return objectDes;
	}
	public void setObjectDes(String objectDes) {
		this.objectDes = objectDes;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}
