package com.froad.enums;

import com.froad.util.Checker;

public enum ActivityTypeEnum {
	
	merchant("1", "商户活动"),
	outlet("2", "门店活动"),
	product("3", "商品活动");
	
	
	private ActivityTypeEnum(String type, String desc){
		this.type = type;
		this.desc = desc;
	}
	
	private String type;
	private String desc;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static ActivityTypeEnum getByType(String type){
		if(Checker.isNotEmpty(type.trim())){
			for(ActivityTypeEnum e : values()){
				if(e.getType().equals(type)){
					return e;
				}
			}
		}
		return null;
	}
}
