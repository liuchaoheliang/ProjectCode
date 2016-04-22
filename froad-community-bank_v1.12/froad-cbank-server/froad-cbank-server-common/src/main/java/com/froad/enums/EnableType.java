package com.froad.enums;

/**
 * 启用类型
 * @author  <mailto>luofan@f-road.com.cn</mailto>
 *
 */
public enum EnableType {

	stop(-1, "不启用"),
	init(0,  "初始化"),
	enable(1,"启用");
	
	private int code;
	private String typeName;
	
	private EnableType(int code, String typeName){
		this.code = code;
		this.typeName = typeName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
