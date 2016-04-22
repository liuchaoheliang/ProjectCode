package com.froad.enums;

import org.apache.commons.lang.StringUtils;

public enum BankType {

	province_city_county("0", "省联社-市联社-县联社-网点"),
	province_city("1", "省联社-市联社-网点"),
	city("2", "市联社-网点"),
	county("3", "县联社-网点")
	;
	
	private String type;
	
	private String describe;

	private BankType(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static BankType getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(BankType b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}
