package com.froad.fft.dto;

import com.froad.fft.enums.AgreementType;

import java.io.Serializable;
import java.util.Date;

/**
 * 协议
 * 
 * @author FQ
 * 
 */
public class AgreementDto implements Serializable {


	private Long id;
	private Date createTime;
	private AgreementType type;// 类型
	private String content;// 内容
	private Long clientId;// 所属客户端
	
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
	public AgreementType getType() {
		return type;
	}
	public void setType(AgreementType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
}
