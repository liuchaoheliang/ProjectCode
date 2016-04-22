package com.froad.cbank.coremodule.normal.boss.enums;

public enum MerchantDisableStatusEnum {
	
	normal("0","正常"),
	disable("1","禁用"),
	unregistered("2","解约")
	;
	  
	private String code;
	private String describe;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	private MerchantDisableStatusEnum(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static MerchantDisableStatusEnum getType(String code){
	    for(MerchantDisableStatusEnum type : MerchantDisableStatusEnum.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
