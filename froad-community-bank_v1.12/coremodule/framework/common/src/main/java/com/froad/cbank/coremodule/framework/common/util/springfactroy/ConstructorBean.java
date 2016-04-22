package com.froad.cbank.coremodule.framework.common.util.springfactroy;

/************************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2013-8-16
 * Create Author: hujz
 * File Name:  spring通过构造方式注入
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class ConstructorBean {
	/**
	 * 构造注入值类型 引用
	 */
	public static final String TYPE_REF="ref";
	/**
	 * 构造注入值类型 对象
	 */
	public static final String TYPE_OBJECT="object";
	/**
	 * 类型 ref object
	 */
	private String type;
	/**
	 * 值
	 */
	private Object obj;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
