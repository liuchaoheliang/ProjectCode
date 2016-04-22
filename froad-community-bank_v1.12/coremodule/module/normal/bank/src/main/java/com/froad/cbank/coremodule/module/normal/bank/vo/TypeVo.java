package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class TypeVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String type;       //类型
	private String typeName;   //类型名称
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
