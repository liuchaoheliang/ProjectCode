package com.froad.po;

import java.io.Serializable;

public class FindAdLocationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result; // Result结果
	private AdLocation adLocation; // 结果

	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public AdLocation getAdLocation() {
		return adLocation;
	}
	public void setAdLocation(AdLocation adLocation) {
		this.adLocation = adLocation;
	}

}
