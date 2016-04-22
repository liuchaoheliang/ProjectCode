package com.froad.po;

import java.io.Serializable;

public class FindAllAdvertisingParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private String terminalType; // 终端类型
	private String positionPage; // 页面标识
	private String clientId; // 客户端ID
	private String paramOneValue; // 第一参数-内容
	private String paramTwoValue; // 第二参数-内容
	private String paramThreeValue; // 第三参数-内容
	private long adLocationId; // 广告位ID
	
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getPositionPage() {
		return positionPage;
	}
	public void setPositionPage(String positionPage) {
		this.positionPage = positionPage;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getParamOneValue() {
		return paramOneValue;
	}
	public void setParamOneValue(String paramOneValue) {
		this.paramOneValue = paramOneValue;
	}
	public String getParamTwoValue() {
		return paramTwoValue;
	}
	public void setParamTwoValue(String paramTwoValue) {
		this.paramTwoValue = paramTwoValue;
	}
	public String getParamThreeValue() {
		return paramThreeValue;
	}
	public void setParamThreeValue(String paramThreeValue) {
		this.paramThreeValue = paramThreeValue;
	}
	public long getAdLocationId() {
		return adLocationId;
	}
	public void setAdLocationId(long adLocationId) {
		this.adLocationId = adLocationId;
	}
}
