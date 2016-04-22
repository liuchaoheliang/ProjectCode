package com.froad.enums;


/**
 * 二维码前缀
 */
public enum QrcodePrefix {

	GROUP("00","预售"),
	PRESELL("01","团购"),
	FACE2FACE("10","面对面"),
	TICKET("11","券");
	
	private String code;
	
	private String description;
	
	private QrcodePrefix(String code,String description){
		this.code=code;
		this.description=description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
        return this.code;
    }

}
