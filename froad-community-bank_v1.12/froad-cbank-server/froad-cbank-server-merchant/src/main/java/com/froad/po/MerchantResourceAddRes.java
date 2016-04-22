package com.froad.po;

import java.io.Serializable;

public class MerchantResourceAddRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	private Long id;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
