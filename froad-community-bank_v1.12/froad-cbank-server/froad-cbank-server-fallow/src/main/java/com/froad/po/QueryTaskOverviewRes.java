package com.froad.po;

import java.util.Date;
/**
 * 
 * ClassName: 审核概要信息返回参数
 * Function: TODO ADD FUNCTION
 * date: 2015年10月22日 上午10:45:47
 *
 * @author wm
 * @version
 */
public class QueryTaskOverviewRes {
	
	public Result result;
	/**
	 * 审核流水号
	 */
	public String instanceId; 
	/**
	 * 审核状态
	 */
	public String auditState; 
	/**
	 * 创建人
	 */
	public String creator; //
	/**
	 * 创建时间
	 */
	public Date createTime; 
	/**
	 * 当前操作人所属机构代码
	 */
	public String orgId;
	/**
	 * 业务Id
	 */
	public String bessId;
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getBessId() {
		return bessId;
	}
	public void setBessId(String bessId) {
		this.bessId = bessId;
	}

}
