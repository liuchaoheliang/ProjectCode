package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * 类描述:删除商户(或者按照id查询时也会使用到该类)
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年9月17日 上午9:40:15
 * @email "chenzhangwei@f-road.com.cn"
 */
public class DeleteMerchantReq {
	
	private String clientId;
	private String userId;
	/**
	 * 商户id
	 */
	@NotEmpty(value = "删除商户分类id不能为空")
	private String merchantCategoryId;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMerchantCategoryId() {
		return merchantCategoryId;
	}
	public void setMerchantCategoryId(String merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}
	
	
}
