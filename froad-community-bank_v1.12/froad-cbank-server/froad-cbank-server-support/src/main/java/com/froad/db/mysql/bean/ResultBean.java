package com.froad.db.mysql.bean;

import com.froad.enums.ResultCode;


/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: ResultBean
 * @Description: 通用的结果体
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月19日 上午9:12:55
 */
public class ResultBean {

	/**
	 * 结果码
	 */
	private String resultCode;
	
	/**
	 * 结果码描述信息
	 */
	private String resultDesc;
	
	
	/**
	 * 可能携带的业务参数
	 */
	private Object data;

	
	//------------------封装不包含set 只能通过构造赋值
	public String getResultCode() {
		return resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public Object getData() {
		return data;
	}
	
	
	//----------------- 构造限定ResultCode
   	public ResultBean(ResultCode code) {
		this.resultCode = code.getCode();
		this.resultDesc = code.getMsg();
	}
	public ResultBean(ResultCode code,String msg) {
		this.resultCode = code.getCode();
		this.resultDesc = msg;
	}
	public ResultBean(ResultCode code,Object data) {
		this.resultCode = code.getCode();
		this.resultDesc = code.getMsg();
		this.data = data;
	}
	public ResultBean(ResultCode code,String msg,Object data) {
		this.resultCode = code.getCode();
		this.resultDesc = msg;
		this.data = data;
	}
	
}
