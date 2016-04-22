package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class UserSummaryBankResVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2523109278876782044L;
	/** 机构号 */
	private String orgCode;
	/** 机构名称 */
	private String orgName;
	/** 新增用户数 */
	private Integer addCount;
	/** 动账用户数 */
	private Integer changeCount;
	/** 结余用户数 */
	private Integer totalCount;
	/** 新增商户占比 */
	private Double percent;
	/** 订单数 */
	private Integer orderCount;
	/** 消费金额 */
	private Double totalAmount;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getAddCount() {
		return addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	public Integer getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
