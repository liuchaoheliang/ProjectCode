package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.SmsType;

/**
 * 短信模板
 * 
 *
 */
public class SmsContent extends BaseEntity{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private SmsType smsType;//模版所属类别
	private Long clientId; //客户端编号
	private String content;//模版内容
	private String msgSuffix;//后缀
	private Boolean isEnableSuffix;//后缀是否启用	
	
	public SmsContent(SmsType smsType,Long clientId){
		this.smsType = smsType;
		this.clientId = clientId;
	}
	
	public SmsContent(){}
	
	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgSuffix() {
		return msgSuffix;
	}

	public void setMsgSuffix(String msgSuffix) {
		this.msgSuffix = msgSuffix;
	}

	public Boolean getIsEnableSuffix() {
		return isEnableSuffix;
	}

	public void setIsEnableSuffix(Boolean isEnableSuffix) {
		this.isEnableSuffix = isEnableSuffix;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
}
