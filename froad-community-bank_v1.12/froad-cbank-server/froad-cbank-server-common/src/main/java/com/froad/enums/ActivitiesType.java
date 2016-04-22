package com.froad.enums;

public enum ActivitiesType {
	
	vip("0","VIP价"),
	point("1","赠送积分"),
	limit("2","限购");
	  
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
	
	private ActivitiesType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static ActivitiesType getType(String code){
	    for(ActivitiesType type : ActivitiesType.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
