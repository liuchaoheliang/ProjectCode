package com.froad.common.beans;

import com.alibaba.fastjson.JSONObject;
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
	private String code;
	
	/**
	 * 结果码描述信息
	 */
	private String msg;
	
	
	/**
	 * 可能携带的业务参数
	 */
	private Object data;

	
	//------------------封装不包含set 只能通过构造赋值
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}
	
	
	//----------------- 构造限定ResultCode
   	public ResultBean(ResultCode code) {
		this.code = code.getCode();
		this.msg = code.getMsg();
	}
	public ResultBean(ResultCode code,String msg) {
		this.code = code.getCode();
		this.msg = msg;
	}
	public ResultBean(ResultCode code,Object data) {
		this.code = code.getCode();
		this.msg = code.getMsg();
		this.data = data;
	}
	public ResultBean(ResultCode code,String msg,Object data) {
		this.code = code.getCode();
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 结果是否成功
	 * @return boolean true 成功， false-失败
	 *<pre>
	 *
	 * @Description: 判断返回结果是否成功 
	 * @version V1.0 修改人：Arron 日期：2015年4月15日 上午11:05:35 
	 *
	 *</pre>
	 */
	public boolean isSuccess() {
	    boolean isSucc = false;
	    if(ResultCode.success.getCode().equals(this.code)) {
	        isSucc = true;
	    }
	    return isSucc;
	}
	
	
	@Override
	public String toString(){
		return "code: " + this.code + "|msg: " + this.msg + "|dataJson: " + JSONObject.toJSONString(this.data);
	}
}
