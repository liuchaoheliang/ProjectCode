package com.froad.po;


/**
 * CbAgreement entity. @author MyEclipse Persistence Tools
 */
public class Agreement implements java.io.Serializable {

	// Fields

	private Long id;//主键ID
	private String clientId;//客户端ID
	private String content;//详细内容
	// Constructors

	/** default constructor */
	public Agreement() {
	}

	/** minimal constructor */
	public Agreement(String clientId) {
		this.clientId = clientId;
	}

	/** full constructor */
	public Agreement(String clientId, String content) {
		this.clientId = clientId;
		this.content = content;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}