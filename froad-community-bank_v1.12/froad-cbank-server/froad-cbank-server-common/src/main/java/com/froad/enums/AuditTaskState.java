package com.froad.enums;

import org.apache.commons.lang.StringUtils;

public enum AuditTaskState {

	onPassage("0", "在途"),
	endFile("1", "归档")
	;
	
	private String type;
	
	private String describe;

	private AuditTaskState(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static AuditTaskState getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(AuditTaskState b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}
