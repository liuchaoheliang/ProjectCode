package com.froad.cbank.coremodule.framework.common.util.exception;

import sun.reflect.Reflection;

/**
 ***********************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2011-12-31
 * Create Author: huangxin
 * File Name:  业务异常处理
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
 *************************************************
 */
@SuppressWarnings("restriction")
public class BusinessException extends BaseException{
	
	private static final long serialVersionUID = 7537976633438034302L;
	
	public BusinessException(){
		super();
	}
	
	public BusinessException(String message){
		super(message);
	}
	
	/**
	 * 异常构造方法
	 * @param methodName 抛出异常的方法
	 * @param msg 异常信息
	 */
	public BusinessException(String methodName,String msg) {
		super(Reflection.getCallerClass(2).getName(), methodName ,msg);
	}
	
	/**
	 * 异常构造方法
	 * @param methodName 抛出异常的方法
	 * @param msg 提示异常信息
	 * @param errmsg 异常对象
	 */
	public BusinessException(String methodName, String msg, String errmsg) {
		super(Reflection.getCallerClass(2).getName(), methodName ,msg, errmsg);
	}
	public BusinessException(String methodName, String msg, String errmsg,Exception e) {
		super(Reflection.getCallerClass(2).getName(), methodName ,msg, errmsg,e);
	}

}
