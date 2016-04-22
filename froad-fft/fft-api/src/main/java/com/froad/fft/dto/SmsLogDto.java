package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.SmsType;

/**
 * 短信日志记录
 * @author FQ
 *
 */
public class SmsLogDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private SmsType smsType;//短信所属类型
	private String mobile;//手机号码
	private String content;//内容
	private Boolean isSuccess; //发送结果
	private String sendUser;//发送短信帐号 null表示为系统发送	
	
	private Long clientId;//客户端编号
	
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
