package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.AgreementType;
import com.froad.fft.persistent.common.enums.DataState;

/**
 * 协议
 * 
 * @author FQ
 * 
 */
public class Agreement extends BaseEntity {


	private AgreementType type;// 类型
	private String content;// 内容
	
	private Long clientId;//所属客户端
	
	
	private DataState dataState;//数据状态：有效、删除
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	

}
