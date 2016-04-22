/**
 * Project Name:coremodule-bank
 * File Name:ResultEnum.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.enums
 * Date:2015年10月27日下午3:24:41
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.enums;
/**
 * ClassName:ResultEnum
 * Reason:	 
 * Date:     2015年10月27日 下午3:24:41
 * @author   asus
 * @version  
 * @see 	 
 */
public enum ResultEnum {

	FAIL("code", "9999"), 
	SUCCESS("code", "0000"),
	MESSAGE("message",""),
	CODE("code", "");
	private String code;
	private String descrition;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescrition() {
		return descrition;
	}
	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}
	private ResultEnum(String code, String descrition) {
		this.code = code;
		this.descrition = descrition;
	}
	
}
