package com.froad.po;

import java.io.Serializable;

public class BossUserCheckRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	private BossUser bossUser;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public BossUser getBossUser() {
		return bossUser;
	}

	public void setBossUser(BossUser bossUser) {
		this.bossUser = bossUser;
	}

}
