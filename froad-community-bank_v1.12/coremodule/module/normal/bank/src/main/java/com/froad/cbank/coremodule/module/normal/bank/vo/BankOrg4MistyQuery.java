/**
 * Project Name:coremodule-bank
 * File Name:BankOrg4MistyQuery.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年11月27日上午11:33:18
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * ClassName:BankOrg4MistyQuery Reason: TODO ADD REASON. Date: 2015年11月27日
 * 上午11:33:18
 * 
 * @author chenmingcan@froad.com.cn
 * @version
 * @see
 */
public class BankOrg4MistyQuery implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 6521454726287764240L;

	private String orgCode;// 机构编号
	private String orgName;// 机构名称
	private String partenOrgCode;// 上级机构编号

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPartenOrgCode() {
		return partenOrgCode;
	}

	public void setPartenOrgCode(String partenOrgCode) {
		this.partenOrgCode = partenOrgCode;
	}

}
