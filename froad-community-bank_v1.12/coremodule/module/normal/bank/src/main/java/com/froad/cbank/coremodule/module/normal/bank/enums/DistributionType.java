package com.froad.cbank.coremodule.module.normal.bank.enums;

public enum DistributionType {
	
	HOME("0","送货上门"),
	TAKE("1","网点自提"),
	HOME_OR_TAKE("2","配送或自提")
	;
	DistributionType(String code,String description){
		this.code = code;
		this.description = description;
	}
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
}

