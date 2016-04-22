/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:DefineTaskResp.java
 * Package Name:com.froad.po.resp
 * Date:2015-12-5下午03:40:06
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.resp;

import java.util.Date;

/**
 * ClassName:DefineTaskResp
 * Reason:	 任务列表返回实体.
 * Date:     2015-12-5 下午03:40:06
 * @author   wufei
 * @version  
 * @see 	 
 */
public class DefineTaskResp {
	private String clientId;
	private Long loginId;
	private String commitTimes;  //提交时间
	private String completeTimes; //任务完成时间
	private String status; //状态  0-已提交 1-执行中 2-执行完毕
	private String url;  //下载url
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	} 
	 
	public String getCommitTimes() {
		return commitTimes;
	}
	public void setCommitTimes(String commitTimes) {
		this.commitTimes = commitTimes;
	}
	public String getCompleteTimes() {
		return completeTimes;
	}
	public void setCompleteTimes(String completeTimes) {
		this.completeTimes = completeTimes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
}
