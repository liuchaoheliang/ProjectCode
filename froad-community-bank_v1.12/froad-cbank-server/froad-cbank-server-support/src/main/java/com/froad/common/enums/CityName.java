package com.froad.common.enums;


public enum CityName {
    SHAI("上海"),
    TJIAN("天津"),
    BJING("北京"),
    CQING("重庆");
    
	
	 String cityName;

    CityName(String cityName){
    	this.cityName=cityName;
    }
	public String getCityName() {
		return cityName;
	}

	
	
	
	
	
}
