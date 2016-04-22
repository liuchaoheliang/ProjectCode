package com.froad.jetty.vo;

import java.io.Serializable;

import com.froad.enums.H5ResultCode;

/**
 * 一般响应对象
 * 
 * @author wangzhangxu
 * @date 2015年4月28日 上午10:53:22
 * @version v1.0
 */
public class ResponseVo implements Serializable {
	
	private static final long serialVersionUID = -1630089820782275640L;

	private String code;
	
	private String message;
	
	public ResponseVo(H5ResultCode resultCode){
		this.code = resultCode.getCode();
		this.message = resultCode.getMsg();
	}
	
	public ResponseVo(String code, String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean success(){
		return H5ResultCode.success.getCode().equals(code);
	}
	
}
