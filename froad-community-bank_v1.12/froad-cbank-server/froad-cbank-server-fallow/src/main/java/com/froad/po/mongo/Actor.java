/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:Actor.java
 * Package Name:com.froad.po.mongo
 * Date:2015年10月27日上午10:58:14
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ClassName:Actor Reason: TODO ADD REASON. Date: 2015年10月27日 上午10:58:14
 * 
 * @author vania
 * @version
 * @see
 */
public class Actor implements java.io.Serializable {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 6069981373882632223L;

	/**
	 * 操作人id
	 */
	@JSONField(name = "actor_id", serialize = true, deserialize = true)
	private Long actorId;

	/**
	 * 操作人用户名
	 */
	@JSONField(name = "actor_user_name", serialize = true, deserialize = true)
	private String actorUserName;

	/**
	 * 操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)
	 */
	@JSONField(name = "org_id", serialize = true, deserialize = true)
	private String orgId;
	
	/**
	 * 操作人关联的taskId
	 */
	@JSONField(name = "task_id", serialize = true, deserialize = true)
	private String taskId;
	
	/**
	 * 审核状态
	 */
	@JSONField(name = "audit_state", serialize = true, deserialize = true)
	private String auditState;
	
	/**
	 * 当前操作人所属(相对于orgCode来说)的1级机构码
	 */
	@JSONField(name = "pro_org_code", serialize = true, deserialize = true)
	private String proOrgCode;
	/**
	 * 当前操作人所属(相对于orgCode来说)的2级机构码
	 */
	@JSONField(name = "city_org_code", serialize = true, deserialize = true)
	private String cityOrgCode;
	/**
	 * 当前操作人所属(相对于orgCode来说)的3级机构码
	 */
	@JSONField(name = "county_org_code", serialize = true, deserialize = true)
	private String countyOrgCode;
	/**
	 * 当前操作人所属的机构码(如果操作人是银行操作员则本字段等于orgId, 如果是商户或者门店操作员则本字段是所属商户的发展机构码)
	 */
	@JSONField(name = "org_code", serialize = true, deserialize = true)
	private String orgCode;

	public Actor() {
		super();
	}

	public Actor(Long actorId, String actorUserName, String orgId, String taskId, String auditState) {
		super();
		this.actorId = actorId;
		this.actorUserName = actorUserName;
		this.orgId = orgId;
		this.taskId = taskId;
		this.auditState = auditState;
	}	
	
	public Actor(Long actorId, String actorUserName, String orgId, String taskId, String auditState, String proOrgCode, String cityOrgCode, String countyOrgCode, String orgCode) {
		super();
		this.actorId = actorId;
		this.actorUserName = actorUserName;
		this.orgId = orgId;
		this.taskId = taskId;
		this.auditState = auditState;
		this.proOrgCode = proOrgCode;
		this.cityOrgCode = cityOrgCode;
		this.countyOrgCode = countyOrgCode;
		this.orgCode = orgCode;
	}

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

	public String getProOrgCode() {
		return proOrgCode;
	}

	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
	}

	public String getCityOrgCode() {
		return cityOrgCode;
	}

	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}

	public String getCountyOrgCode() {
		return countyOrgCode;
	}

	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	
}
