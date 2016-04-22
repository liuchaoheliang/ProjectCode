package com.froad.fft.bean;

import java.io.Serializable;


	/**
	 * 类描述：方法的返回值
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 25, 2013 10:14:51 AM 
	 */
public class Result implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	/*****成功的code*****/
	public static final String SUCCESS="success";
	
	/*****失败的code*****/
	public static final String FAIL="fail";	

	/******返回码********/
	private String code;
	
	/******返回信息********/
	private String msg;
	
	public Result(){}
	
	public Result(String code,String msg){
		this.code=code;
		this.msg=msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

