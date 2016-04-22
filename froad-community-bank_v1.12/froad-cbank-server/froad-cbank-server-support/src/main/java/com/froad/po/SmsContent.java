package com.froad.po;



/**
 * 短信模板
 */
public class SmsContent implements java.io.Serializable {

	private Long id;//主键ID 
	private String clientId;//客户端编号
	private Integer smsType; // 短信类型 
	private String content;//模版内容 
	private String channel;// 短信渠道
	private String msgSuffix;//后缀 
	private Integer validTime;//有效时间
	private Boolean isEnable;//是否启用 

	// Constructors

	/** default constructor */
	public SmsContent() {
	}

	/** minimal constructor */
	public SmsContent(String clientId, Integer smsType, String content, String channel, Integer validTime) {
		this.clientId = clientId;
		this.smsType = smsType;
		this.content = content;
		this.channel = channel;
		this.validTime = validTime;
	}

	/** full constructor */
	public SmsContent(String clientId, Integer smsType, String content, String channel, String msgSuffix, Integer validTime) {
		this.clientId = clientId;
		this.smsType = smsType;
		this.content = content;
		this.channel = channel;
		this.msgSuffix = msgSuffix;
		this.validTime = validTime;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getSmsType() {
		return this.smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMsgSuffix() {
		return this.msgSuffix;
	}

	public void setMsgSuffix(String msgSuffix) {
		this.msgSuffix = msgSuffix;
	}

	public Integer getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}