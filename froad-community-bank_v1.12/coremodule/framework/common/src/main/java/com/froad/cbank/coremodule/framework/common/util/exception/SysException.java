package com.froad.cbank.coremodule.framework.common.util.exception;

import sun.reflect.Reflection;

/**
 ***********************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2011-12-31
 * Create Author: huangxin
 * File Name:  系统异常处理
 * Last version:  1.0
 * Function:
 * Last Update Date:
 * Change Log:
 *************************************************
 */
@SuppressWarnings("restriction")
public class SysException extends BaseException {

	private static final long serialVersionUID = -7951658301578649602L;


	/**
	 * @param msg 异常信息
	 */
	public SysException(String methodName,String msg) {
		super(Reflection.getCallerClass(2).getName(), methodName ,msg);
	}

	/**
	 * 异常构造方法
	 * @param err_num 异常编号
	 * @param msg 提示信息
	 * @param errmsg 异常对象
	 */
	public SysException(String methodName, String msg, String errmsg) {
		super(Reflection.getCallerClass(2).getName(), methodName, msg, errmsg);
		
	}

}
