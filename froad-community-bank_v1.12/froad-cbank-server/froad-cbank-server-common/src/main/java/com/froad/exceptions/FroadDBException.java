package com.froad.exceptions;

import java.util.UUID;

/**
 * 自定义异常，目的在于防止DB层的具体可能包含的敏感错误直接被调用方捕获，取消向上级传递大量的Throwable错误值栈描述信息
* <p>Function: DBFroadException</p>
* <p>Description: 新增uuid，调用方可以通过抛出的错误编号在DB层快速定位错误日志信息</p>
* @author zhaoxy@thankjava.com
* @date 2014年12月26日 下午1:59:49
* @version 1.0
 */
public class FroadDBException extends Exception {

	private static final long serialVersionUID = -6357761909839770063L;

	public FroadDBException() {
		super(geExceptionCode());
	}

	private static String geExceptionCode(){
		StringBuffer sb = new StringBuffer();
		sb.append("请求DB异常，相信错误信息参见DB日志 编号[");
		sb.append(UUID.randomUUID().toString().replace("-", "").substring(6).toUpperCase());
		sb.append("]");
		return sb.toString();
	}
	
	public FroadDBException(String message, Throwable cause) {
		super(message != null ? message + geExceptionCode() : message);
	}

	public FroadDBException(String message) {
		super(message != null ? message + geExceptionCode() : message);
	}

	public FroadDBException(Throwable cause) {
		super(geExceptionCode());
		cause.printStackTrace();
	}
}
