package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class PercentResVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038750080223333968L;
	
	/**类型*/
	private String type;
	/**比例*/
	private Double percent;
	/** 类型名称 */
	private String typeName;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}	
