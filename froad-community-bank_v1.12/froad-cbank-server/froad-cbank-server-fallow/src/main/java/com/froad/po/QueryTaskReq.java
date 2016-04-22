/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryTaskReq.java
 * Package Name:com.froad.po
 * Date:2015年10月22日上午11:29:56
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;
/**
 * ClassName:QueryTaskReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 上午11:29:56
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryTaskReq {
	
    /**
     * 源对象
     */
	public Origin origin; 
    /**
     * 审核流水号
     */
	public String instanceId;
	public Origin getOrigin() {
		return origin;
	}
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	

}
