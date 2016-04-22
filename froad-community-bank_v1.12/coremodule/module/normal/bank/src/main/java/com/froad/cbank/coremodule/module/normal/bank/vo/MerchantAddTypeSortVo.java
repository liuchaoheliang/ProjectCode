package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:27:36 
 */
public class MerchantAddTypeSortVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -3447641487337101344L;
	private String constractStaff;
	private Integer count;
	private Integer sort;
	
	public String getConstractStaff() {
		return constractStaff;
	}
	public void setConstractStaff(String constractStaff) {
		this.constractStaff = constractStaff;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
