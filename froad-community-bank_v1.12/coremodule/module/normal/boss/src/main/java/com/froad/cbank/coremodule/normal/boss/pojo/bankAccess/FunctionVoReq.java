package com.froad.cbank.coremodule.normal.boss.pojo.bankAccess;

/**
 * 功能模块实体类
 * @author yfy
 * @date: 2015年9月18日 上午09:33:03
 */
public class FunctionVoReq {
	
	/**
	 * 商户模块类型:1:特惠商户(默认);2:特惠商品(默认);3:精品预售;4:扫码支付(默认);5:积分兑换;
	 */
	private String type;
	/**
	 * 功能模块别名
	 */
	public String aliasName;
	/**
	 * 图标url
	 */
	public String iconUrl;
	/**
	 * 排序值
	 */
	public String sortValue;
	
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
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	  
}
