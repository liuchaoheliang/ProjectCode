package com.froad.fft.persistent.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 创建日期
	 */
	private Date createTime;
	
	/**
	 * 修改日期
	 */
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
