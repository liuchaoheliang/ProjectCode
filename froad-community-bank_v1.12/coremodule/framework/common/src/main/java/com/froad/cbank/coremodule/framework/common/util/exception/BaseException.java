package com.froad.cbank.coremodule.framework.common.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 ***********************************************
 * Copyright (c)  by soap
 * All right reserved.
 * Create Date: 2011-12-31
 * Create Author: huangxin
 * File Name:  异常处理基类
 * Last version:  1.0
 * Function:
 * Last Update Date:
 * Change Log:
 *************************************************
 */
public abstract class BaseException extends RuntimeException {
	
	final Logger logger = LoggerFactory.getLogger(BaseException.class);
	
	private static final long serialVersionUID = -7126114767624436789L;
	
	public BaseException(String message){
		super(message);
	}
	
	public BaseException(){
		super();
	}
	
	/**
	 * 异常编号 项目编码（2位）+模块编号(1级2位+2级2位)+类编号+方法编号+异常编号
	 */
	private String className;
	
	private String methodName;
	
	private String msg="";
	/**
	 * 异常信息
	 */
	private String err_msg = "";
	
	public String getErr_msg() {
		return err_msg;
	}


	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}


	/**
	 * 异常构造方法
	 * @param className 抛出异常的类
	 * @param methodName 抛出异常的方法
	 * @param msg 异常信息
	 */
	public BaseException(String className, String methodName, String errmsg) {
		super("操作失败");
		this.msg="操作失败";
		this.className = className;
		this.methodName = methodName;
		this.err_msg=errmsg;
		logger.error(getMessage()+"-"+this.err_msg);
	}
	
	
	/**
	 * 异常构造方法
	 * @param err_num 异常编号
	 * @param msg 提示信息
	 * @param errmsg 异常对象
	 */
	public BaseException(String className, String methodName, String msg, String errmsg) {
		super(msg);
		this.className = className;
		this.methodName = methodName;
		this.msg = msg;
		this.err_msg=errmsg;
		logger.error(getMessage()+"-"+this.err_msg);
	}
	
	/**
	 * 异常构造方法
	 * @param err_num 异常编号
	 * @param msg 提示信息
	 * @param errmsg 异常对象
	 */
	public BaseException(String className, String methodName, String msg, String errmsg,Exception e) {
		super(msg);
		this.className = className;
		this.methodName = methodName;
		this.msg = msg;
		this.err_msg=errmsg;
		logger.error(getMessage()+"-"+this.err_msg+"--"+e.getMessage());
	}
	
	/**
	 * 获取异常信息
	 */
	public String getOMessage() {
		return  this.msg;
	}
	
	/**
	 * 获取异常信息
	 */
	public String getMessage() {
		String s="("+this.className+"."+this.methodName+")";
			s+="("+this.className+")";
		return s+ this.msg;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	

}
