package com.froad.cbank.coremodule.normal.boss.pojo.member;

/**
 * 审核列表审核参数实体
 * @author yfy
 */
public class MemberAuditVo {
	
	private Long msgID;//主键
	private Integer state;//审核状态 1-通过 2-不通过
	private String clientId;//客户端ID
	private String mobile;//解绑手机号
	private String bindMobile;//绑定手机号
	private String auditor;//审核人
	private String smsContent;//短信内容
	
	public Long getMsgID() {
		return msgID;
	}
	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBindMobile() {
		return bindMobile;
	}
	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
}
