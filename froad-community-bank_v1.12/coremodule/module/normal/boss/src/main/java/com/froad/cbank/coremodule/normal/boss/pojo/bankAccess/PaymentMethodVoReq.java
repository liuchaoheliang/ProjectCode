package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;

/**
 * 支付方式
 * @author yfy
 * @date: 2015年9月18日 上午09:43:23
 */
public class PaymentMethodVoReq {
	
	/**
	 * 支付方式类型：1:联盟积分;2:银行积分;20:贴膜卡支付;41:银联无卡支付;
	 * 50:网银支付;51:银行卡支付
	 */
	private String type;
	/**
	 * 文案
	 */
	private String aliasName;
	/**
	 * 图标url
	 */
	private String iconUrl;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}
