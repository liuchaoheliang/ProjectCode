/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryApplyAuitListReq.java
 * Package Name:com.froad.po
 * Date:2015年10月27日下午5:36:15
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Map;

import com.froad.enums.RestrictionsEnum;

/**
 * ClassName:QueryApplyAuitListReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 下午5:36:15
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryApplyAuitListReq {
	
	 /**
	   * 源信息
	   */
	  public Origin origin;
	  /**
	   * 审核流水号
	   */
	  public String instanceId; 
	  /**
	   * 开始时间
	   */
	  public Long starTime; 
	  /**
	   * 结束时间
	   */
	  public Long endTime; 
	  /**
	   * 业务类型：0、商户  1、门店  2、团购商品  3、预售商品
	   */
	  public String processType;
	  private Map<RestrictionsEnum, String> andBessData;
		private Map<RestrictionsEnum, String> orBessData;
	  
	  /**类型详情:0-新增,1-更新*/
	  private String processTypeDetail;
	  
	  /**
	   * 审核机构Code
	   */
	  public String auditOrgCode; // optional
	  
	public Origin getOrigin() {
		return origin;
	}
	public void setOrigin(Origin origin) {
		this.origin = origin;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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
	public String getProcessTypeDetail() {
		return processTypeDetail;
	}
	public void setProcessTypeDetail(String processTypeDetail) {
		this.processTypeDetail = processTypeDetail;
	}
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
}
