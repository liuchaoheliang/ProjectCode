package com.froad.enums;

public enum AdvertisingType {
	
	text("0","文本"),
	image("1","图片"),
	flash("2","flash"),
	link("3","链接");
	  
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
	
	private AdvertisingType(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}
	
	/**
	 * 通过code取得类型
	 * @param code
	 * @return
	 */
	public static AdvertisingType getType(String code){
	    for(AdvertisingType type : AdvertisingType.values()){
	        if(type.getCode().equals(code)){
	            return type;
	        }
	    }
	    return null;
	 }
	
}
