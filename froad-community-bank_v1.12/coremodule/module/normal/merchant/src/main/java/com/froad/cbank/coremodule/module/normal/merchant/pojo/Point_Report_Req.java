package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Point_Report_Req extends BasePojo {

	private String startDate;// 结算开始时间
	private String endDate;// 结算结束时间

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
