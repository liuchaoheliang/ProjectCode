package com.froad.CB.po.lottery;

import java.util.List;


public class Period {

	private String startTime;//当期开始销售时间
	private String endTime;//当期停止销售时间
	private String openTime;//当期开奖时间
	private String period;
	//足彩
	private String bssj;			//比赛时间
	private String lotteryNo;//彩种编码
	private String lotteryName;//彩种名称
	private String content;//奖级信息、奖池金额、销售总额
	private String tznum;//当期开奖号码
	/* 足彩 */
	private List<Ct> ct;				//足彩信息列表

	
	public String getBssj() {
		return bssj;
	}
	public void setBssj(String bssj) {
		this.bssj = bssj;
	}
	public List<Ct> getCt() {
		return ct;
	}
	public void setCt(List<Ct> ct) {
		this.ct = ct;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTznum() {
		return tznum;
	}
	public void setTznum(String tznum) {
		this.tznum = tznum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLotteryNo() {
		return lotteryNo;
	}
	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}
	
}
