/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryTaskListRes.java
 * Package Name:com.froad.po
 * Date:2015年10月22日上午11:30:49
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;
/**
 * ClassName:QueryTaskListRes
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 上午11:30:49
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryTaskListRes {
	
	/**
	  * 任务流水号
	  */
	public String taskId; 
    /**
      * 创建时间
     */
	public Long createTime; 
    /**
      * 审核时间
     */
	public Long auditTime; 
    /**
      * 审核状态
      */
	public String auditState; 
    /**
      * 审核机构
     */
	public String orgId; 
     /**
       * 审核人
       */
	public String auditor; 
    /**
     * 备注
     */
	public String remark;

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
	  
}
