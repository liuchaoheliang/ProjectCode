package com.froad.cbank.coremodule.normal.boss.enums;

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
    /**
     * 赠送积分
     */
    creditPoints("6", "赠送积分"),
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
	
	public static String getDescriptionByCode(String code){
		for(PayMentMethodEnum e : PayMentMethodEnum.values()){
			if(e.getCode().equals(code)){
				return e.getDescription();
			}
		}
		return "";
	}
}
