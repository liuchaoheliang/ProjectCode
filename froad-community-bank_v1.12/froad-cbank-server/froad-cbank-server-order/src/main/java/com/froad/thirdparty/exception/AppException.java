package com.froad.thirdparty.exception;

/**
 * 异常
 * 
 * @author FQ
 * 
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = -6357761909839770063L;

	public AppException() {
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}
}
