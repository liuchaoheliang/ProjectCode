package com.froad.cbank.coremodule.module.normal.user.utils;


/**
 * 请求结果封装Bean
 * @author Liebert
 *
 */
public class ResultBean {

	/**
	 * 结果码
	 */
	private String code;
	/**
	 * 结果信息
	 */
	private String msg;
	/**
	 * 是否成功标志
	 */
	private boolean success;
	/**
	 * 数据
	 */
	private Object data;
	
	public ResultBean(){
		this.success = false;
		this.code = null;
		this.msg = null;
		this.data = null;
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
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	

	public ResultBean(boolean isSuccess, String msg){
		this.success = isSuccess;
		this.msg = msg;
	}
}
