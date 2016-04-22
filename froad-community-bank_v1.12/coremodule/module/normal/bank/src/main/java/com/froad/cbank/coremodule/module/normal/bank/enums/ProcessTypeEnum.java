package com.froad.cbank.coremodule.module.normal.bank.enums;

/**
 * 
 * ClassName: ProcessTypeEnum
 * Function: 待审核类型枚举
 * date: 2015年11月3日 上午11:22:23
 *
 * @author user
 * @version
 */
public enum ProcessTypeEnum {

	MERCHANT("1"), 
	OUTLET("2"), 
	GROUPPRODUCT("3"), 
	PREPRODUCT("4");
	private String code;

	private ProcessTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
