/**
 * Project Name:coremodule-bank
 * File Name:BankCompanyResVo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年11月26日下午4:54:15
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:BankCompanyResVo Reason: ADD REASON. Date: 2015年11月26日 下午4:54:15
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class BankCompanyResVo implements Serializable {

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 8643690965239318475L;
	private long companyId;// 公司主键
	private String companyName;// 公司名称

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
