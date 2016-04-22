package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

public class SaleTrendBankResVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586380476710508418L;
	/**周数*/
	private Integer week;
	/** 商品销售数量 */
	private Integer saleProductNumber;
	/**商品销售金额*/
	private Double saleAmountCount;
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getSaleProductNumber() {
		return saleProductNumber;
	}

	public void setSaleProductNumber(Integer saleProductNumber) {
		this.saleProductNumber = saleProductNumber;
	}

	public Double getSaleAmountCount() {
		return saleAmountCount;
	}

	public void setSaleAmountCount(Double saleAmountCount) {
		this.saleAmountCount = saleAmountCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
