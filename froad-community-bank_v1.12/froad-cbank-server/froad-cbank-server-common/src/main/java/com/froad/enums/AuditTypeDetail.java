package com.froad.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 审核类型 
 * @author ll 20150818 
 *
 */
public enum AuditTypeDetail {

	add("0", "新增"),
	update("1", "修改"),
	;
	
	private String type;
	
	private String describe;

	private AuditTypeDetail(String type, String describe) {
		this.type = type;
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public String getDescribe() {
		return describe;
	}
	
	public static AuditTypeDetail getByType(String type){
		if(StringUtils.isNotBlank(type)){
			for(AuditTypeDetail b : values()){
				if(b.getType().equals(type)){
					return b;
				}
			}
		}
		return null;
	}
}
