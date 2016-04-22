package com.froad.fft.persistent.entity;

/**
 * 退款
 * @author FQ
 *
 */
public class Refunds extends BaseEntity {
	
	public enum State {
		/**
		 * 申请退款，处理中
		 */
		apply,
		
		/**
		 * 审核通过
		 */
		audit_pass,
		
		/**
		 * 审核不通过
		 */
		audit_no_pass
	}
	
	private String sn;//编号
	private Long transId;//交易ID
	private State state;//状态
	private String reason;//退款事由
	
	private String sysUserId;//操作员
	private String remark;//备注
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
