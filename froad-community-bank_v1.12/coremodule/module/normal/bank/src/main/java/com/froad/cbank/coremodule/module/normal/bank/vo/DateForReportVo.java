package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class DateForReportVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long beginDate;
	private Long endDate;

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

}
