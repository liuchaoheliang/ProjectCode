package com.froad.po;

import java.io.Serializable;

import com.froad.enums.ResultCode;

public class Result implements Serializable {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Result() {
		super();
		this.resultCode = ResultCode.success.getCode();
		this.resultDesc = ResultCode.success.getMsg();
	}

	public Result(String resultCode, String resultDesc) {
		super();
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}
	
	public Result(ResultCode code) {
		super();
		this.resultCode = code.getCode();
		this.resultDesc = code.getMsg();
	}
	
	public Result(ResultCode code, String appendMsg){
		super();
		this.resultCode = code.getCode();
		this.resultDesc = code.getMsg()+appendMsg;
	}
	
	/**
	   * 返回码 : 0000:成功 ,失败：xxxx
	   */
	  public String resultCode; // required
	  /**
	   * 返回信息
	   */
	  public String resultDesc; // required
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}	
	public void setResult(ResultCode code) {
		this.resultCode = code.getCode();
		this.resultDesc = code.getMsg();
	}
	  
	
}
