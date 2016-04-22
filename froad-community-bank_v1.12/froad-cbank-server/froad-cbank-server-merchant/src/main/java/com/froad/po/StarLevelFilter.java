package com.froad.po;

import java.io.Serializable;

public class StarLevelFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer starLevel; // required

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

}
