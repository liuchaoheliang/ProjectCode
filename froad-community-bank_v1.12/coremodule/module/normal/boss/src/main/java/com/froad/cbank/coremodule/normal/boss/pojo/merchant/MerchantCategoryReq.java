package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.io.Serializable;

/**
 * 
 * 类描述:商户列表查询vo
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年9月17日 上午9:24:54
 * @email "chenzhangwei@f-road.com.cn"
 */
public class MerchantCategoryReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -72914532555179073L;
	private String clientId;
	private String userId;
	private String token;
	/**
	 * 是否包含禁用的分类
	 */
	private  static final boolean includeDisable=true;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public boolean isIncludeDisable() {
		return includeDisable;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
