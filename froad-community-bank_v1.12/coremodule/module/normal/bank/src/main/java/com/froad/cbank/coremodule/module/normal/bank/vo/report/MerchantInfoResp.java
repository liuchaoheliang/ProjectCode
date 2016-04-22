/**
 * Project Name:froad-cbank-server-coremodule
 * File Name:MerchantInfoResp.java
 * Package Name:com.froad.po.resp
 * Date:2015年12月2日下午3:19:05
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo.report;

import java.io.Serializable;

/**
 * ClassName:MerchantInfoResp Reason: Date: 2015年12月2日 下午3:19:05
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class MerchantInfoResp  implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = -4699126314541692999L;
	/**
	 * 商户数
	 */
	private int merchantCount; // optional
	/**
	 * 解约商户数
	 */
	private int merchantCancelContract; // optional
	/**
	 * 累计商户数
	 */
	private int merchantCumulation; // optional
	/**
	 * 门店数
	 */
	private int outletSum; // optional
	/**
	 * 累计门店数
	 */
	private int outletCumulation; // optional

	public int getMerchantCount() {
		return merchantCount;
	}

	public void setMerchantCount(int merchantCount) {
		this.merchantCount = merchantCount;
	}

	public int getMerchantCancelContract() {
		return merchantCancelContract;
	}

	public void setMerchantCancelContract(int merchantCancelContract) {
		this.merchantCancelContract = merchantCancelContract;
	}

	public int getMerchantCumulation() {
		return merchantCumulation;
	}

	public void setMerchantCumulation(int merchantCumulation) {
		this.merchantCumulation = merchantCumulation;
	}

	public int getOutletSum() {
		return outletSum;
	}

	public void setOutletSum(int outletSum) {
		this.outletSum = outletSum;
	}

	public int getOutletCumulation() {
		return outletCumulation;
	}

	public void setOutletCumulation(int outletCumulation) {
		this.outletCumulation = outletCumulation;
	}

}
