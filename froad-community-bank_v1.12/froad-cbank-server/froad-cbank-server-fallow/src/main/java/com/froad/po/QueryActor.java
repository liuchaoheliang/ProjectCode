/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryActor.java
 * Package Name:com.froad.po
 * Date:2015年10月28日上午11:18:59
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ClassName:QueryActor
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 上午11:18:59
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryActor {
	/**
	 * 操作人id
	 */
	private Long actorId;

	/**
	 * 操作人用户名
	 */
	private String actorUserName;

	/**
	 * 操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)
	 */
	private String orgId;
	
	/**
	 * 操作人关联的taskId
	 */
	private String taskId;
	
	/**
	 * 审核状态
	 */
	private String auditState;

	public Long getActorId() {
		return actorId;
	}

	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

	public String getActorUserName() {
		return actorUserName;
	}

	public void setActorUserName(String actorUserName) {
		this.actorUserName = actorUserName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
}
