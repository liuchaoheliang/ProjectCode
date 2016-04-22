package com.froad.po.mongo;

import java.util.List;

import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * BizInstanceAttr entity.
 */

public class AuditInstanceDetail implements java.io.Serializable {
	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 7304592497773925508L;

	/**
	 * ID
	 */
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private ObjectId id;

	/**
	 * 创建时间
	 */
	@JSONField(name = "create_time", serialize = true, deserialize = true)
	private Long createTime;

	/**
	 * 客户端ID
	 */
	@JSONField(name = "client_id", serialize = true, deserialize = true)
	private String clientId;

	/**
	 * 审核流水号
	 */
	@JSONField(name = "instance_id", serialize = true, deserialize = true)
	private String instanceId;

	/**
	 * 审核创建人信息
	 */
	@JSONField(name = "create_actor", serialize = true, deserialize = true)
	private Actor createActor;

	/**
	 * 已执行过的审核人信息
	 */
	@JSONField(name = "audit_actor", serialize = true, deserialize = true)
	private List<Actor> auditActor;

	/**
	 * 下一审核人信息
	 */
	@JSONField(name = "next_actor", serialize = true, deserialize = true)
	private Actor nextActor;

	/**
	 * 流程类型；取自cb_process表的type字段
	 */
	@JSONField(name = "process_type", serialize = true, deserialize = true)
	private String processType;

	/**
	 * 流程类型详情；取自cb_process表的type_detail字段
	 */
	@JSONField(name = "process_type_detail", serialize = true, deserialize = true)
	private String processTypeDetail;

	/**
	 * 业务ID；如果是商户审核就是商户ID，如果是门店审核就是门店ID，以此类推
	 */
	@JSONField(name = "bess_id", serialize = true, deserialize = true)
	private String bessId;

	/**
	 * 所属商户的发展机构
	 */
	@JSONField(name = "org_code", serialize = true, deserialize = true)
	private String orgCode;

	/**
	 * 业务数据；
	 */
	@JSONField(name = "bess_data", serialize = true, deserialize = true)
	private JSONObject bessData;
	
	/**
	 * 当前审核审核流水的审核状态
	 */
	@JSONField(name = "audit_state", serialize = true, deserialize = true)
	private String auditState;

	/**
	 * 审核流水状态(0-结束, 1-活动)
	 */
	@JSONField(name = "instance_state", serialize = true, deserialize = true)
	private String instanceState;
	/**
	 * 归档时间
	 */
	@JSONField(name = "finish_time", serialize = true, deserialize = true)
	private Long finishTime;

	public AuditInstanceDetail() {
		super();
	}

	public AuditInstanceDetail(Long createTime, String clientId, String instanceId, Actor createActor, List<Actor> auditActor, Actor nextActor, String processType, String processTypeDetail, String bessId, String orgCode, JSONObject bessData, String auditState, String instanceState, Long finishTime) {
		super();
		this.createTime = createTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.createActor = createActor;
		this.auditActor = auditActor;
		this.nextActor = nextActor;
		this.processType = processType;
		this.processTypeDetail = processTypeDetail;
		this.bessId = bessId;
		this.orgCode = orgCode;
		this.bessData = bessData;
		this.auditState = auditState;
		this.instanceState = instanceState;
		this.finishTime = finishTime;
	}
	
	public AuditInstanceDetail(ObjectId id, Long createTime, String clientId, String instanceId, Actor createActor, List<Actor> auditActor, Actor nextActor, String processType, String processTypeDetail, String bessId, String orgCode, JSONObject bessData, String auditState, String instanceState, Long finishTime) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.createActor = createActor;
		this.auditActor = auditActor;
		this.nextActor = nextActor;
		this.processType = processType;
		this.processTypeDetail = processTypeDetail;
		this.bessId = bessId;
		this.orgCode = orgCode;
		this.bessData = bessData;
		this.auditState = auditState;
		this.instanceState = instanceState;
		this.finishTime = finishTime;
	}

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

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
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


}