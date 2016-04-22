/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:MerchantInfoResp.java
 * Package Name:com.froad.po.resp
 * Date:2015年12月2日下午3:19:05
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.resp;

/**
 * ClassName:MerchantInfoResp Reason: Date: 2015年12月2日 下午3:19:05
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class MerchantInfoResp {

	/**
	 * 商户数
	 */
	private Integer merchantCount; // optional
	/**
	 * 解约商户数
	 */
	private Integer merchantCancelContract; // optional
	/**
	 * 累计商户数
	 */
	private Integer merchantCumulation; // optional
	/**
	 * 门店数
	 */
	private Integer outletSum; // optional
	/**
	 * 累计门店数
	 */
	private Integer outletCumulation; // optional

	public Integer getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(Integer merchantCount) {
		this.merchantCount = merchantCount;
	}

	public Integer getMerchantCancelContract() {
		return merchantCancelContract;
	}

	public void setMerchantCancelContract(Integer merchantCancelContract) {
		this.merchantCancelContract = merchantCancelContract;
	}

	public Integer getMerchantCumulation() {
		return merchantCumulation;
	}

	public void setMerchantCumulation(Integer merchantCumulation) {
		this.merchantCumulation = merchantCumulation;
	}

	public Integer getOutletSum() {
		return outletSum;
	}

	public void setOutletSum(Integer outletSum) {
		this.outletSum = outletSum;
	}

	public Integer getOutletCumulation() {
		return outletCumulation;
	}

	public void setOutletCumulation(Integer outletCumulation) {
		this.outletCumulation = outletCumulation;
	}

}
