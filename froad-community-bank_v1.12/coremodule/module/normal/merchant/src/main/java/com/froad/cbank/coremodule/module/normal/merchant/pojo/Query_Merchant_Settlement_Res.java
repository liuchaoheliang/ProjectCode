package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Merchant_Settlement_Res {

	/**
	   * 今日团购总数
	   */
	  public long groupTodayTotalCount; // required
	  /**
	   * 今日团购总金额（含未消费）
	   */
	  public double groupTodayTotalMoney; // required
	  /**
	   * 今日团购已提货笔数
	   */
	  public long groupTodayTakeCount; // required
	  /**
	   * 今日团购已结算金额
	   */
	  public double groupTodaySettleMoney; // required
	  /**
	   * 今日面对面总笔数
	   */
	  public long f2fTodayTotalCount; // required
	  /**
	   * 今日面对面总金额
	   */
	  public double f2fTodayTotalMoney; // required
	  /**
	   * 本月团购总数
	   */
	  public long groupMonthTotalCount; // required
	  /**
	   * 本月团购总金额（含未消费）
	   */
	  public double groupMonthTotalMoney; // required
	  /**
	   * 本月团购已提货笔数
	   */
	  public long groupMonthTakeCount; // required
	  /**
	   * 本月团购已结算金额
	   */
	  public double groupMonthSettleMoney; // required
	  /**
	   * 本月面对面总笔数
	   */
	  public long f2fMonthTotalCount; // required
	  /**
	   * 本月面对面总金额
	   */
	  public double f2fMonthTotalMoney; // required
	  /**
	   * 本月结算总笔数（团购+面对面）
	   */
	  public long monthTotalSettleCount; // required
	  /**
	   * 本月结算总金额（团购+面对面）
	   */
	  public double monthTotalSettleMoney; // required
	public long getGroupTodayTotalCount() {
		return groupTodayTotalCount;
	}
	public void setGroupTodayTotalCount(long groupTodayTotalCount) {
		this.groupTodayTotalCount = groupTodayTotalCount;
	}
	public double getGroupTodayTotalMoney() {
		return groupTodayTotalMoney;
	}
	public void setGroupTodayTotalMoney(double groupTodayTotalMoney) {
		this.groupTodayTotalMoney = groupTodayTotalMoney;
	}
	public long getGroupTodayTakeCount() {
		return groupTodayTakeCount;
	}
	public void setGroupTodayTakeCount(long groupTodayTakeCount) {
		this.groupTodayTakeCount = groupTodayTakeCount;
	}
	public double getGroupTodaySettleMoney() {
		return groupTodaySettleMoney;
	}
	public void setGroupTodaySettleMoney(double groupTodaySettleMoney) {
		this.groupTodaySettleMoney = groupTodaySettleMoney;
	}
	public long getF2fTodayTotalCount() {
		return f2fTodayTotalCount;
	}
	public void setF2fTodayTotalCount(long f2fTodayTotalCount) {
		this.f2fTodayTotalCount = f2fTodayTotalCount;
	}
	public double getF2fTodayTotalMoney() {
		return f2fTodayTotalMoney;
	}
	public void setF2fTodayTotalMoney(double f2fTodayTotalMoney) {
		this.f2fTodayTotalMoney = f2fTodayTotalMoney;
	}
	public long getGroupMonthTotalCount() {
		return groupMonthTotalCount;
	}
	public void setGroupMonthTotalCount(long groupMonthTotalCount) {
		this.groupMonthTotalCount = groupMonthTotalCount;
	}
	public double getGroupMonthTotalMoney() {
		return groupMonthTotalMoney;
	}
	public void setGroupMonthTotalMoney(double groupMonthTotalMoney) {
		this.groupMonthTotalMoney = groupMonthTotalMoney;
	}
	public long getGroupMonthTakeCount() {
		return groupMonthTakeCount;
	}
	public void setGroupMonthTakeCount(long groupMonthTakeCount) {
		this.groupMonthTakeCount = groupMonthTakeCount;
	}
	public double getGroupMonthSettleMoney() {
		return groupMonthSettleMoney;
	}
	public void setGroupMonthSettleMoney(double groupMonthSettleMoney) {
		this.groupMonthSettleMoney = groupMonthSettleMoney;
	}
	public long getF2fMonthTotalCount() {
		return f2fMonthTotalCount;
	}
	public void setF2fMonthTotalCount(long f2fMonthTotalCount) {
		this.f2fMonthTotalCount = f2fMonthTotalCount;
	}
	public double getF2fMonthTotalMoney() {
		return f2fMonthTotalMoney;
	}
	public void setF2fMonthTotalMoney(double f2fMonthTotalMoney) {
		this.f2fMonthTotalMoney = f2fMonthTotalMoney;
	}
	public long getMonthTotalSettleCount() {
		return monthTotalSettleCount;
	}
	public void setMonthTotalSettleCount(long monthTotalSettleCount) {
		this.monthTotalSettleCount = monthTotalSettleCount;
	}
	public double getMonthTotalSettleMoney() {
		return monthTotalSettleMoney;
	}
	public void setMonthTotalSettleMoney(double monthTotalSettleMoney) {
		this.monthTotalSettleMoney = monthTotalSettleMoney;
	}

}
