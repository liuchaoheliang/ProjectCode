package com.froad.CB.AppException;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.DocumentException;

public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(Exception ex) {
		super(ex.getMessage());
		errType = "";
		errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		myException = ex;
		errMsg = ex.getMessage();
	}
	public AppException(HttpException ex) {
		super(ex.getMessage());
		errType = "";
		errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		myException = ex;
		errMsg = ex.getMessage();
	}
	
	public AppException(DocumentException ex) {
		super(ex.getMessage());
		errType = "";
		errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		myException = ex;
		errMsg = ex.getMessage();
	}
	public AppException(String str) {
		super(str);
		errType = "";
		errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		errMsg = str;
	}

	public AppException(int errCode, String errType, String errMsg) {
		super(errMsg);
		this.errType = "";
		this.errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		this.errCode = errCode;
		this.errType = errType;
		this.errMsg = errMsg;
	}

	public AppException(int errCode, String errType, String errMsg, Throwable e) {
		super(e.getMessage());
		this.errType = "";
		this.errMsg = "";
		contextMsg = "";
		cusMsg = "";
		Msg = "";
		this.errCode = errCode;
		this.errType = errType;
		this.errMsg = errMsg;
		myException = e;
	}

	public AppException(int errCode, String errType, String errMsg,
			String contextMsg, String cusMsg, Throwable e) {
		super(e.getMessage());
		this.errType = "";
		this.errMsg = "";
		this.contextMsg = "";
		this.cusMsg = "";
		Msg = "";
		this.errMsg = errMsg;
		this.errType = errType;
		this.contextMsg = contextMsg;
		this.cusMsg = cusMsg;
		myException = e;
	}

	public AppException(int errCode, String errType, String errMsg,
			String contextMsg, String cusMsg) {
		super(errMsg);
		this.errType = "";
		this.errMsg = "";
		this.contextMsg = "";
		this.cusMsg = "";
		Msg = "";
		this.errMsg = errMsg;
		this.errType = errType;
		this.contextMsg = contextMsg;
		this.cusMsg = cusMsg;
	}

	public void printStackTrace() {
		if (myException != null)
			myException.printStackTrace();
	}

	public String msgFormat() {
		StringBuffer msg = new StringBuffer();
		msg.append(errType);
		msg.append(errCode);
		msg.append(contextMsg);
		msg.append(errMsg);
		msg.append(cusMsg);
		return msg.toString();
	}

	public String getContextMsg() {
		return contextMsg;
	}

	public String getCusMsg() {
		return cusMsg;
	}

	public long getErrCode() {
		return (long) errCode;
	}

	public void setContextMsg(String contextMsg) {
		this.contextMsg = contextMsg;
	}

	public void setCusMsg(String cusMsg) {
		this.cusMsg = cusMsg;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	protected Throwable myException;

	protected String errType;

	protected int errCode;

	protected String errMsg;

	protected String contextMsg;

	protected String cusMsg;

	protected String Msg;
}
