package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 商户查询
 * @ClassName MerchantReq
 * @author zxl
 * @date 2016年1月20日 上午9:37:42
 */
public class MerchantReq extends Page{

	private String clientId;
	
	private String merchantName;
	
	private String orgCodes;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}
	
	
}
