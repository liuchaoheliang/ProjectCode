/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:ShippingState.java
 * Package Name:com.froad.enums
 * Date:2015年12月3日上午9:39:20
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;
/**
 * ClassName:ShippingState(物流收货状态)
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月3日 上午9:39:20
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public enum ShippingState {
	
	unreceipted(0, "未收货"),
	manual_confirm(1, "人工确认"),
	sys_confirm(2, "系统确认"),
	;
	
	private ShippingState(Integer state, String desc){
		this.state = state;
		this.desc = desc;
	}
	
	private Integer state;
	
	private String desc;


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
