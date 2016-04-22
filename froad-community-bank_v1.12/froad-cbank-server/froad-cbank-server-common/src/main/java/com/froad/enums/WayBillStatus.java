/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:WayBillStatus.java
 * Package Name:com.froad.enums
 * Date:2015年11月27日下午5:25:21
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;

import com.froad.util.Checker;

/**
 * ClassName:WayBillStatus
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 下午5:25:21
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public enum WayBillStatus {
	
	in_transit("0", "在途"),
	pick_up("1", "揽件"),
	exception("2", "疑难"),
	sign_in("3", "签收"),
	sign_out("4", "退签"),
	delivery("5", "派件"),
	send_back("6", "退回"),
	;
	
	private String status;
	private String describe;
	
	private WayBillStatus(String status, String describe){
		this.status = status;
		this.describe = describe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public static WayBillStatus getByStatus(String status){
		if(Checker.isNotEmpty(status)){
			for(WayBillStatus w : values()){
				if(w.getStatus().equals(status)){
					return w;
				}
			}
		}
		return null;
	}
	
}
