/**
 * Project Name:coremodule-user
 * File Name:FiledSort.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.enums
 * Date:2015年12月2日下午10:43:59
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.enums;
/**
 * ClassName:FiledSort
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月2日 下午10:43:59
 * @author   wm
 * @version  
 * @see 	 
 */
public enum BoutiqueFiledSort {
	
	recommend("1","recommend"),  //推荐优先" 我的VIP页面里的为您推荐用到
	hensive("2","hensive"),      //综合排序
	sellCount("3","sellCount"),  // "销量排序"
	price("4","price");          //价格排序
	
	private String code;
	
	private String msg;
	
	private BoutiqueFiledSort(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString(){
		return this.code;
	}

}
