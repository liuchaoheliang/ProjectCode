package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午4:07:22 
 */
public class MerchantTrendVo implements Serializable{

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1899554055872569748L;
	private int week;
	private int addCount;
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getAddCount() {
		return addCount;
	}
	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}
	
	
}