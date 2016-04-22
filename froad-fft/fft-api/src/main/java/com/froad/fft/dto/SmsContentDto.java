package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.SmsType;

/**
 * 短信模板
 * @author FQ
 *
 */
public class SmsContentDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private SmsType smsType;//模版所属类别
	private Long clientId; //客户端编号
	private String content;//模版内容
	private String msgSuffix;//后缀
	private Boolean isEnableSuffix;//后缀是否启用	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SmsType getSmsType() {
		return smsType;
	}
	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	
}
