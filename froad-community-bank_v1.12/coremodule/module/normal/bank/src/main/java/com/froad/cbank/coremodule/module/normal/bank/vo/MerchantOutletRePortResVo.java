/**
 * Project Name:coremodule-bank
 * File Name:MerchantOutletRePortResVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年9月1日上午11:41:12
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:MerchantOutletRePortResVo
 * Reason:	 ADD REASON.
 * Date:     2015年9月1日 上午11:41:12
 * @author   明灿
 * @version  
 * @see 	 
 */
public class MerchantOutletRePortResVo implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 支行名称 *
	 */
	public String orgName;
	/**
	 * 网点名称 *
	 */
	public String outletName;
	/**
	 * 网点地址 *
	 */
	public String outletAddress;
	/**
	 * 商户名称 *
	 */
	public String merchantName;
	/**
	 * 商户门店数 *
	 */
	public int merchantOutletCount;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletAddress() {
		return outletAddress;
	}

	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getMerchantOutletCount() {
		return merchantOutletCount;
	}

	public void setMerchantOutletCount(int merchantOutletCount) {
		this.merchantOutletCount = merchantOutletCount;
	}

}
