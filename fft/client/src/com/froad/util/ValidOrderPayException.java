package com.froad.util;

public class ValidOrderPayException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidOrderPayException() {
	  super();
	}
	
	public ValidOrderPayException(String msg) {
	  super(msg);
	}
	 
	public ValidOrderPayException(String msg, Throwable cause) {
	  super(msg, cause);
	}
	 
	public ValidOrderPayException(Throwable cause) {
	  super(cause);
	}
}
