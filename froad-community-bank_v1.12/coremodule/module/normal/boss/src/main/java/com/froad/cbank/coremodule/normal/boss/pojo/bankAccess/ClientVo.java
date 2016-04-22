package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;


/**
 * 多银行接入客户端实体类
 * @author yfy
 * @date: 2015年9月18日 下午14:33:18
 */
public class ClientVo {

	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 银行名称
	 */
	private String bankName;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
