package com.froad.cbank.coremodule.normal.boss.pojo.message;

import java.io.Serializable;

public class SmsTemplateVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 主键id
	private String name;// 模板名称
	private String clientId;// 客户端id
	private Integer smsType;// 短信类型
	private String content;// 模板内容
	private String channel;// 发送渠道

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
