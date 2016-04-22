package com.froad.po.mysql;

import java.util.Date;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Range;
import net.sf.oval.guard.Guarded;



/**
 * CbHistoryInstance entity. 
 */

@Guarded
public class HistoryInstance implements java.io.Serializable {

	// Fields

	private Long id;
	private Date createTime;
	private Date updateTime;
	@MaxLength(value = 32, message = "客户端ID不能超过{max}个字符")
	private String clientId;
	@MaxLength(value = 32, message = "审核流水号不能超过{max}个字符")
	private String instanceId;
	@MaxLength(value = 32, message = "流程ID不能超过{max}个字符")
	private String processId;
	@MaxLength(value = 1, message = "实例状态不能超过{max}个字符")
//	@Range(min = 0, max = 1, message = "实例状态必须为{min}至{max}范围之内")
	private String instanceState;
	@MaxLength(value = 32, message = "创建人不能超过{max}个字符")
	private String creator;
	@MaxLength(value = 32, message = "最后一次更新人不能超过{max}个字符")
	private String lastUpdator;
	private Integer orderValue;
	@MaxLength(value = 16, message = "创建机构不能超过{max}个字符")
	private String orgId;

	// Constructors

	/** default constructor */
	public HistoryInstance() {
	}

	/** minimal constructor */
	public HistoryInstance(Date createTime, Date updateTime, String clientId, String instanceId, String processId, String instanceState,String orgId) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.processId = processId;
		this.instanceState = instanceState;
		this.orgId = orgId;
	}

	/** full constructor */
	public HistoryInstance(Date createTime, Date updateTime, String clientId, String instanceId, String processId, String instanceState, String creator, String lastUpdator, Integer orderValue,String orgId) {
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.clientId = clientId;
		this.instanceId = instanceId;
		this.processId = processId;
		this.instanceState = instanceState;
		this.creator = creator;
		this.lastUpdator = lastUpdator;
		this.orderValue = orderValue;
		this.orgId = orgId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getInstanceState() {
		return instanceState;
	}

	public void setInstanceState(String instanceState) {
		this.instanceState = instanceState;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	// Property accessors
	
	
	
	

}