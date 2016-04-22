package com.froad.po;

import java.io.Serializable;

public class RecommentNotEmpty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean notEmpty; // required

	public Boolean getNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(Boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

}
