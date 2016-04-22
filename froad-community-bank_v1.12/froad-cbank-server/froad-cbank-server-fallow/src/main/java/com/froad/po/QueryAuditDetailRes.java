/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryAuditRes.java
 * Package Name:com.froad.po
 * Date:2015年10月27日下午6:20:14
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.List;

import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.froad.po.mongo.Actor;


/**
 * ClassName:QueryAuditRes
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 下午6:20:14
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryAuditDetailRes {
	/**
	 * ID
	 */
	private ObjectId id;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 客户端ID
	 */
	private String clientId;

	/**
	 * 审核流水号
	 */
	private String instanceId;

	/**
	 * 审核创建人信息
	 */
	private Actor createActor;

	/**
	 * 已执行过的审核人信息
	 */
	private List<Actor> auditActor;

	/**
	 * 下一审核人信息
	 */
	private Actor nextActor;

	/**
	 * 流程类型；取自cb_process表的type字段
	 */
	private String processType;

	/**
	 * 流程类型详情；取自cb_process表的type_detail字段
	 */
	private String processTypeDetail;

	/**
	 * 业务ID；如果是商户审核就是商户ID，如果是门店审核就是门店ID，以此类推
	 */
	private String bessId;

	/**
	 * 所属商户的发展机构
	 */
	private String orgCode;

	/**
	 * 业务数据；
	 */
	private JSONObject bessData;

	/**
	 * 审核流水状态
	 */
	private String instanceState;
	/**
	 * 归档时间
	 */
	private Long finishTime;
	
	/**
	 * 当前审核审核流水的审核状态
	 */
	private String auditState;
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public Actor getCreateActor() {
		return createActor;
	}
	public void setCreateActor(Actor createActor) {
		this.createActor = createActor;
	}
	public List<Actor> getAuditActor() {
		return auditActor;
	}
	public void setAuditActor(List<Actor> auditActor) {
		this.auditActor = auditActor;
	}
	public Actor getNextActor() {
		return nextActor;
	}
	public void setNextActor(Actor nextActor) {
		this.nextActor = nextActor;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getProcessTypeDetail() {
		return processTypeDetail;
	}
	public void setProcessTypeDetail(String processTypeDetail) {
		this.processTypeDetail = processTypeDetail;
	}
	public String getBessId() {
		return bessId;
	}
	public void setBessId(String bessId) {
		this.bessId = bessId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public JSONObject getBessData() {
		return bessData;
	}
	public void setBessData(JSONObject bessData) {
		this.bessData = bessData;
	}
	public String getInstanceState() {
		return instanceState;
	}
	public void setInstanceState(String instanceState) {
		this.instanceState = instanceState;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
}
