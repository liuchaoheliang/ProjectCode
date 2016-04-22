package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 我的理财产品预算信息</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-15下午04:24:40</p>
 * <p>作者: 吴菲</p>
 */
public class MyProductReckonInfoPojo {

	/** 累计年化收益率  */
	private double historyGeneralRate; 
	
	/** 累计收益金额  */
	private double historyTotalProfit;
	
	/** 预期年化收益率  */
	private double currentGeneralRate;
	
	/** 预期收益金额    */
	private double currentTotalProfit;
	
	
	public MyProductReckonInfoPojo(double historyTotalProfit, double currentTotalProfit) {
		this.historyTotalProfit = 0;
		this.currentTotalProfit = 0;
	}


	public double getHistoryGeneralRate() {
		return historyGeneralRate;
	}


	public void setHistoryGeneralRate(double historyGeneralRate) {
		this.historyGeneralRate = historyGeneralRate;
	}


	public double getHistoryTotalProfit() {
		return historyTotalProfit;
	}


	public void setHistoryTotalProfit(double historyTotalProfit) {
		this.historyTotalProfit = historyTotalProfit;
	}


	public double getCurrentGeneralRate() {
		return currentGeneralRate;
	}


	public void setCurrentGeneralRate(double currentGeneralRate) {
		this.currentGeneralRate = currentGeneralRate;
	}


	public double getCurrentTotalProfit() {
		return currentTotalProfit;
	}


	public void setCurrentTotalProfit(double currentTotalProfit) {
		this.currentTotalProfit = currentTotalProfit;
	}
	
	
}
