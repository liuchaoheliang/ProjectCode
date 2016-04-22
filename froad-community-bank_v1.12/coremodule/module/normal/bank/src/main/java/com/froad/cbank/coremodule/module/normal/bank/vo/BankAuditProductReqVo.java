package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class BankAuditProductReqVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1869619997121106125L;
	private String productId;// 商品主键id
	private String instanceId;// 审核流水号
	private String taskId;// 任务id
	private String auditState;// 审核状态 0:审核中 1:审核通过 2:审核不通过
	private String remark;// 审核备注
	private String ip;// 请求的ip

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
