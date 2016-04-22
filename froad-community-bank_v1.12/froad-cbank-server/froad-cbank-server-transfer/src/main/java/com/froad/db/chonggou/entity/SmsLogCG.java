package com.froad.db.chonggou.entity;
import java.util.Date;
/**
 * 短信日志
 */
public class SmsLogCG implements java.io.Serializable {


	private Long id;//主键ID
	private String clientId;//客户端编号
	private Date createTime;//创建时间
	private Date expireTime;//失效时间 
	private Integer smsType;//短信类型(图片验证码为特殊类型-1)
	private String mobile;//手机号码(图片验证码无) 
	private String content;//发送内容 
	private Boolean isSuccess;//是否发送成功 
	private String sendUser;//发送短信方
	private String sendIp;//发送IP 
	private Boolean isUsed;//验证码是否已使用 
	private String code;//验证码
	private String token;//验证码凭证 
	private String url;//验证码图片地址 

	// Constructors

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/** default constructor */
	public SmsLogCG() {
	}

	/** minimal constructor */
	public SmsLogCG(String clientId, Date createTime, Date expireTime, Integer smsType, String mobile, String content, Boolean isSuccess) {
		this.clientId = clientId;
		this.createTime = createTime;
		this.expireTime = expireTime;
		this.smsType = smsType;
		this.mobile = mobile;
		this.content = content;
		this.isSuccess = isSuccess;
	}

	/** full constructor */
	public SmsLogCG(String clientId, Date createTime, Date expireTime, Integer smsType, String mobile, String content, Boolean isSuccess, String sendUser, String sendIp, Boolean isUsed, String code, String token,String url) {
		this.clientId = clientId;
		this.createTime = createTime;
		this.expireTime = expireTime;
		this.smsType = smsType;
		this.mobile = mobile;
		this.content = content;
		this.isSuccess = isSuccess;
		this.sendUser = sendUser;
		this.sendIp = sendIp;
		this.isUsed = isUsed;
		this.code = code;
		this.token = token;
		this.url = url;
	}

	public Long getId() {
		return this.id;
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

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getSmsType() {
		return this.smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSendUser() {
		return this.sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getSendIp() {
		return this.sendIp;
	}

	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}

	public Boolean getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

}