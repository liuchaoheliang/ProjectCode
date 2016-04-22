package com.froad.cbank.coremodule.normal.boss.exception;

import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;

public class BossException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码
	 */
	private String code;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	public BossException(){
		super();
	}
	
	public BossException(ErrorEnums e){
		super(e.getMsg());
		this.code = e.getCode();
		this.msg = e.getMsg();
	}
	
	public BossException(String msg){
		super(msg);
		this.code = Constants.RESULT_FAIL;
		this.msg = msg;
	}
	
	public BossException(String code,String msg){
		super(msg);
		this.code = code;
		this.msg = msg;
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
