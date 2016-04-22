package com.froad.enums;

public enum ActivitiesStatus {
	
	no("0","未生效"),
	yes("1","已生效"),
	repeal("2","已作废"),
	delete("3","已删除");
	  
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
	
	private ActivitiesStatus(String code, String describe) {
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
