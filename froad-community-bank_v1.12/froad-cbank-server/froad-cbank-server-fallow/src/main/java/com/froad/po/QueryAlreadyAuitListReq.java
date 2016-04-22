/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryAlreadyAuitListReq.java
 * Package Name:com.froad.po
 * Date:2015年10月28日下午4:26:54
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Map;

import com.froad.enums.RestrictionsEnum;

/**
 * ClassName:QueryAlreadyAuitListReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 下午4:26:54
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryAlreadyAuitListReq {
	
	
	/**
	   * 源信息
	   */
	  public Origin origin; 
	  /**
	   * 开始时间
	   */
	  public Long starTime; 
	  /**
	   * 结束时间
	   */
	  public Long endTime; 
	  /**
	   * 查询类型：1:查在途 2:查归档
	   */
	  public String type; 
	  /**
	   * 归档时间-开始时间
	   */
	  public Long finishStarTime; 
	  /**
	   * 归档时间-结束时间
	   */
	  public Long finishEndTime; 
	  /**
	   * 审核流水号
	   */
	  public String instanceId; 
	  /**
	   * 审核状态
	   */
	  public String auditState; 
	  /**
	   * 业务类型：0、商户  1、门店  2、团购商品  3、预售商品
	   */
	  public String processType; 
	  private Map<RestrictionsEnum, String> andBessData;
		private Map<RestrictionsEnum, String> orBessData;
	  
	  /**
	   * 审核机构Code
	   */
	  public String auditOrgCode; 
	  
	  
	public Origin getOrigin() {
		return origin;
	}
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}
	public Long getStarTime() {
		return starTime;
	}
	public void setStarTime(Long starTime) {
		this.starTime = starTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getFinishStarTime() {
		return finishStarTime;
	}
	public void setFinishStarTime(Long finishStarTime) {
		this.finishStarTime = finishStarTime;
	}
	public Long getFinishEndTime() {
		return finishEndTime;
	}
	public void setFinishEndTime(Long finishEndTime) {
		this.finishEndTime = finishEndTime;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public Map<RestrictionsEnum, String> getAndBessData() {
		return andBessData;
	}
	public void setAndBessData(Map<RestrictionsEnum, String> andBessData) {
		this.andBessData = andBessData;
	}
	public Map<RestrictionsEnum, String> getOrBessData() {
		return orBessData;
	}
	public void setOrBessData(Map<RestrictionsEnum, String> orBessData) {
		this.orBessData = orBessData;
	}
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
	
}
