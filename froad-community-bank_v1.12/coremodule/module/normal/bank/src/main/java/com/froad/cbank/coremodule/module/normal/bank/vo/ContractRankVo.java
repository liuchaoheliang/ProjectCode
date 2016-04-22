package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:09:18 
 */
public class ContractRankVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 8528566631644490442L;
	private String constractStaff;
	private String count;
	private String sort;
	public String getConstractStaff() {
		return constractStaff;
	}
	public void setConstractStaff(String constractStaff) {
		this.constractStaff = constractStaff;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
