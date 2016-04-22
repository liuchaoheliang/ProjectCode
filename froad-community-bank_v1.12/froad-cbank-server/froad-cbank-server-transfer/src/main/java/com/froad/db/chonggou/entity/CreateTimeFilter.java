package com.froad.db.chonggou.entity;

import java.io.Serializable;

public class CreateTimeFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long begTime; // required
	private Long endTime; // required
	public Long getBegTime() {
		return begTime;
	}
	public void setBegTime(Long begTime) {
		this.begTime = begTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}
