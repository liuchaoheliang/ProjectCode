package com.froad.po;

import java.io.Serializable;

public class FindAdvertisingResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result; // Result结果
	private Advertising advertising; // 结果

	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Advertising getAdvertising() {
		return advertising;
	}
	public void setAdvertising(Advertising advertising) {
		this.advertising = advertising;
	}
		

}
