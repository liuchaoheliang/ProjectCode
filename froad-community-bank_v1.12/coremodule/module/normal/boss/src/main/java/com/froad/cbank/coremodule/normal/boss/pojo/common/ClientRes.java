/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.pojo.common;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-4-30下午4:18:44</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class ClientRes {

	private String ClientName;//客户端名称
	private String clientId;//客户端ID
	private String bankOrg;//银行渠道
	private String bankType;//机构模式（0-单级机构、1-多级机构）
	
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(String bankOrg) {
		this.bankOrg = bankOrg;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
}
