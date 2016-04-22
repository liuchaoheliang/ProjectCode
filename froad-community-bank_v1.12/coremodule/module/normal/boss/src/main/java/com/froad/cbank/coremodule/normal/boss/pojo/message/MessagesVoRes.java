package com.froad.cbank.coremodule.normal.boss.pojo.message;

import java.io.Serializable;

public class MessagesVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;// 主键id
	private Long messageSn;// 编号
	private String clientId;// 客户端id
	private String createTime;// 创建时间
	private String mobile;// 手机号码
	private String smsType;// 短信类型
	private String content;// 短信内容
	private Boolean isSuccess;// 是否发送成功
	private String sendAccounts;// 发送帐号
	private String clientName;//客户端名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessageSn() {
		return messageSn;
	}

	public void setMessageSn(Long messageSn) {
		this.messageSn = messageSn;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSendAccounts() {
		return sendAccounts;
	}

	public void setSendAccounts(String sendAccounts) {
		this.sendAccounts = sendAccounts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	

}
