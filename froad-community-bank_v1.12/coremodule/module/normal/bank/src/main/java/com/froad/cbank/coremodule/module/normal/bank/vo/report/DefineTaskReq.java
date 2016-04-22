/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:DefineTaskReq.java
 * Package Name:com.froad.po.resp
 * Date:2015-12-5下午03:45:03
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo.report;

import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;

/**
 * ClassName:DefineTaskReq
 * Reason:	 任务列表请求实体
 * Date:     2015-12-5 下午03:45:03
 * @author   wufei
 * @version  
 * @see 	 
 */
public class DefineTaskReq extends BaseVo{
	
	private Long loginId;
	
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	
	
}
