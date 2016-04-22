package com.froad.cbank.coremodule.module.normal.bank.exception;

import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;

/**
 * 异常
 * @ClassName BankException
 */
public class BankException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码
	 */
	private String code;
	
	/**
	 * 错误信息
	 */
	private String message;
	
	public BankException(){
		super();
	}
	
	public BankException(EnumTypes e){
		super(e.getMessage());
		this.code = e.getCode();
		this.message = e.getMessage();
	}
	
	public BankException(String message){
		super(message);
		this.code = "1";
		this.message = message;
	}
	
	public BankException(String code,String message){
		super(message);
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
	
}
