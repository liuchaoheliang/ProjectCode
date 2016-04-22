/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.po;

public class CheckVIPExistWhiteListRes {
	/**
	 * 返回结果
	 */
	private Result result; // required
	/**
	 * 是否存在于白名单中
	 */
	private Boolean existWhiteList; // required

	public CheckVIPExistWhiteListRes() {
		super();
	}

	public CheckVIPExistWhiteListRes(Result result, Boolean existWhiteList) {
		super();
		this.result = result;
		this.existWhiteList = existWhiteList;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Boolean getExistWhiteList() {
		return existWhiteList;
	}

	public void setExistWhiteList(Boolean existWhiteList) {
		this.existWhiteList = existWhiteList;
	}

}
