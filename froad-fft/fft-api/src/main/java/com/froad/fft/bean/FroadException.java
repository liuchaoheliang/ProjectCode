package com.froad.fft.bean;

/**
 * 异常
 * 
 * @author FQ
 * 
 */
public class FroadException extends RuntimeException {

	public FroadException() {
	}

	public FroadException(String message, Throwable cause) {
		super(message, cause);
	}

	public FroadException(String message) {
		super(message);
	}

	public FroadException(Throwable cause) {
		super(cause);
	}
}
