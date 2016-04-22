package com.froad.cbank.coremodule.module.normal.bank.enums;

public enum PayMentMethodEnum {
	
	/**
     * 现金
     */
    cash("1", "现金支付"),

    /**
     * 方付通积分支付
     */
    froadPoints("2", "联盟积分支付"),

    /**
     * 银行积分支付 
     */
    bankPoints("3", "银行积分支付"),

    /**
     * 方付通积分+现金支付
     */
    froadPointsAndCash("4", "联盟积分+现金支付"),

    /**
     * 银行积分+现金支付
     */
    bankPointsAndCash("5", "银行积分+现金支付"),
	;
    
	PayMentMethodEnum(String code,String description){
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
