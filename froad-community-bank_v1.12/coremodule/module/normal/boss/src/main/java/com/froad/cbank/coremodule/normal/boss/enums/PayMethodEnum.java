package com.froad.cbank.coremodule.normal.boss.enums;

public enum PayMethodEnum {

    /**
     * 方付通积分支付
     */
    froadPoints("1", "联盟积分支付"),
    /**
     * 现金
     */
    cash("2", "现金支付"),
    /**
     * 银行积分支付 
     */
    bankPoints("3", "银行积分支付"),
	;
    
	PayMethodEnum(String code,String description){
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
		for(PayMethodEnum e : PayMethodEnum.values()){
			if(e.getCode().equals(code)){
				return e.getDescription();
			}
		}
		return "";
	}
}
