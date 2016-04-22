package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * 营业额查询
 * @author Administrator
 *
 */
public class Query_Admin_Req extends BasePojo{
	private String year; 
	private String month; 
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
