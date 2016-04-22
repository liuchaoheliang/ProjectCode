package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.SmsType;

/**
 * 短信日志
 * @author FQ
 *
 */
public class SmsLog extends BaseEntity {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private SmsType smsType;//短信所属类型
	private String mobile;//手机号码
	private String content;//内容
	private Boolean isSuccess; //发送结果
	private String sendUser;//发送短信帐号 null表示为系统发送	
	private Long clientId;//客户端编号
	private String sendIp;//发送方IP
	
	public SmsLog(Boolean isSuccess){
		this.isSuccess = isSuccess;
	}
	public SmsLog(){}
	
	public SmsLog(SmsType smsType, Long clientId, String mobile, String content,
			Boolean isSuccess, String sendUser,String sendIp) {
		this.smsType = smsType;
		this.clientId = clientId;
		this.mobile = mobile;
		this.content = content;
		this.isSuccess = isSuccess;
		this.sendUser = sendUser;
		this.sendIp = sendIp;
	}
	
	public SmsLog(SmsType smsType, Long clientId, String mobile,
			Boolean isSuccess, String sendUser,String sendIp) {
		this.smsType = smsType;
		this.clientId = clientId;
		this.mobile = mobile;
		this.isSuccess = isSuccess;
		this.sendUser = sendUser;
		this.sendIp = sendIp;
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
	public String getSendIp() {
		return sendIp;
	}
	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}
	
}
