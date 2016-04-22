/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:DefineTaskReq.java
 * Package Name:com.froad.po.resp
 * Date:2015-12-5下午03:45:03
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.req;
/**
 * ClassName:DefineTaskReq
 * Reason:	 任务列表请求实体
 * Date:     2015-12-5 下午03:45:03
 * @author   wufei
 * @version  
 * @see 	 
 */
public class DefineTaskReq {
	
	private String clientId;
	private Long loginId;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	
	
}
