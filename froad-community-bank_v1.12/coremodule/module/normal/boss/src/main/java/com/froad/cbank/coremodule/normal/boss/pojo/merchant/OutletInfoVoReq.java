package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 类描述：门店请求实体类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-5-3下午4:00:33
 */
public class OutletInfoVoReq extends Page {
	/**
	 * 客户端ID
	 */
	private String clientId; 
	/**
	 * 商户ID
	 */
	private String merchantId; 
	/**
	 * 商户名
	 */
	private String merchantName;
	/**
	 * 地区ID
	 */
	private String areaId; 

	/**
	 * 地区名称
	 */
	private String areaName; 
	
	private String orgCodes;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}
	
}
