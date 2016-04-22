/**
 * Project Name:coremodule-bank
 * File Name:BankCarCheckReq.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.vo
 * Date:2015年9月21日上午11:44:09
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * ClassName:BankCarCheckReq
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月21日 上午11:44:09
 * @author   wm
 * @version  
 * @see 	 
 */
public class BankCarCheckReq extends BaseVo implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(value="账户名为空")
	private String accountName;
	@NotEmpty(value="账号不能为空")
	private String accountNo;
	@NotEmpty(value="证件类型不能为空")
	private String certificateType;
	@NotEmpty(value="证件号不能为空")
	private String certificateNo;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
}
