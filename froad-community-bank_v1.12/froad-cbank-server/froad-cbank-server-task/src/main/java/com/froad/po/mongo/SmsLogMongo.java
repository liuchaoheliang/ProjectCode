package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;


public class SmsLogMongo {

	@JSONField(name="_id")
	private Long id;//主键ID
	
	@JSONField(name="client_id")
	private String clientId;//客户端编号
	
	@JSONField(name="create_time")
	private Long createTime;//创建时间
	
	@JSONField(name="expire_time")
	private Long expireTime;//失效时间 
	
	@JSONField(name="content")
	private String content;//发送内容 
	
	@JSONField(name="mobile")
	private String mobile;//手机号码(图片验证码无)
	
	@JSONField(name="send_user")
	private String sendUser;//发送短信方
	
	@JSONField(name="send_ip")
	private String sendIp;//发送IP
	
	@JSONField(name="is_used")
	private Boolean isUsed;//验证码是否已使用 
	
	@JSONField(name="is_success")
	private Boolean isSuccess;//是否发送失败 
	
	@JSONField(name="code")
	private String code;//验证码
	
	@JSONField(name="token")
	private String token;//验证码凭证
	
	@JSONField(name="url")
	private String url;//验证码图片地址 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getSendIp() {
		return sendIp;
	}

	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
