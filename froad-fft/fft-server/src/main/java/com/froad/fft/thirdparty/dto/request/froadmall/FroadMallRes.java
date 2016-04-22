package com.froad.fft.thirdparty.dto.request.froadmall;

import java.util.List;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: FroadMallRes.java </p>
 *<p> 描述: *-- <b>方付通商场的响应参数</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月17日 上午10:13:40 </p>
 ********************************************************
 */
public class FroadMallRes {

	
	private String resultCode;
	
	private String resultDesc;

	private String transID;
	
	private String description;
	
	private List<Lottery> lotteryList; //彩票期号
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public List<Lottery> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<Lottery> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
