package com.froad.cbank.coremodule.normal.boss.pojo.message;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class SmsTemplateVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 主键id
	private String name;// 模板名称
	private Integer isFrequency;// 频率校验
	private String content;// 短信内容
	private String clientId;// 客户端id
	private Integer smsType;// 短信类型
	private String channel;// 渠道
	private List<SmsElementVoReq> smsElements;// 短信元素集合

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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public Integer getIsFrequency() {
		return isFrequency;
	}

	public void setIsFrequency(Integer isFrequency) {
		this.isFrequency = isFrequency;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<SmsElementVoReq> getSmsElements() {
		return smsElements;
	}

	public void setSmsElements(List<SmsElementVoReq> smsElements) {
		this.smsElements = smsElements;
	}

}
