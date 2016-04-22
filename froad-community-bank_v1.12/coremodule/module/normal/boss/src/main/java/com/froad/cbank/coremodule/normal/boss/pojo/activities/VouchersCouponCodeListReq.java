package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 红包券码明细-请求
 * @author liaopeixin
 *	@date 2015年12月1日 下午2:36:53
 */
public class VouchersCouponCodeListReq extends Page{
	/**
	 * 活动Id
	 */
	private String activeId;
	/**
	 * 客户端Id
	 */
	private String clientId;
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
