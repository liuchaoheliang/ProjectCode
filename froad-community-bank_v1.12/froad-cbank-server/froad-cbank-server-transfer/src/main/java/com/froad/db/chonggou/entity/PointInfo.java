package com.froad.db.chonggou.entity;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PointInfo
 * @Description: 积分信息
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月23日 下午5:54:39
 */
public class PointInfo {

	/**
	 * 方付通积分
	 */
	private double froadPoint;
	
	private String froadAccountId;
	
	/**
	 * 银行积分
	 */
	private double bankPoint;

	private String bankAccountId;
	
	
	public double getFroadPoint() {
		return froadPoint;
	}

	public void setFroadPoint(double froadPoint) {
		this.froadPoint = froadPoint;
	}

	public double getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(double bankPoint) {
		this.bankPoint = bankPoint;
	}

	public String getFroadAccountId() {
		return froadAccountId;
	}

	public void setFroadAccountId(String froadAccountId) {
		this.froadAccountId = froadAccountId;
	}

	public String getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	
	
	
}
