package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class UserTrendBankResVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586380476710508418L;
	/**周数*/
	private Integer week;
	/** 用户总数 */
	private Integer userCount;
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	
}
