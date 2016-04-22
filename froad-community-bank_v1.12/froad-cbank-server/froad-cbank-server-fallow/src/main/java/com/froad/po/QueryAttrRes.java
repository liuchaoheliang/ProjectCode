/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryAttrRes.java
 * Package Name:com.froad.po
 * Date:2015年10月22日下午5:03:51
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;
/**
 * ClassName:QueryAttrRes
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 下午5:03:51
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryAttrRes {
	
	public Result result; 
	  /**
	   * 审核流水号
	   */
	public String instanceId;
	
	public String processTypeDetail;
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getProcessTypeDetail() {
		return processTypeDetail;
	}
	public void setProcessTypeDetail(String processTypeDetail) {
		this.processTypeDetail = processTypeDetail;
	} 
	
}
