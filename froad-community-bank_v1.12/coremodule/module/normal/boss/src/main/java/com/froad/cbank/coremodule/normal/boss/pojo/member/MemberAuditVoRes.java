package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

/**
 * 会员查询条件实体
 * @author: yfy
 */
public class MemberAuditVoRes implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private Long msgID;              //主键ID
	private Long memberCode;		 //会员号
	private String loginID;          //登录ID
	private String mobile;		     //电话号码
	private String email;			 //邮箱 
	private String clientId;		 //客户端
	private String clientChannelName;//客户端
	private Integer userState;	     //用户活动状态1正常状态(已激活),2.未激活,3已锁定,4已禁用
	private Integer state;		     //审核状态（默认0待审核，1审核通过，2审核不通过）
	private String action;           //操作信息
	private String demo;             //操作备注
	private String creater;          //操作人
	private String auditor;          //审核人
	private String createTime;       //操作时间
	private String auditTime;        //审核时间
	
	public Long getMsgID() {
		return msgID;
	}
	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientChannelName() {
		return clientChannelName;
	}
	public void setClientChannelName(String clientChannelName) {
		this.clientChannelName = clientChannelName;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
