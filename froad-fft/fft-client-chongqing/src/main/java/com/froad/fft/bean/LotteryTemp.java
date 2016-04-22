package com.froad.fft.bean;

import com.o2obill.dto.LotteryWinnerDto;

public class LotteryTemp {
	//抽奖活动展示相关信息临时保存bean
	
	private String lotteryWinnerDtoId;
	
	private LotteryWinnerDto lotteryWinnerDto;     //抽奖活动中奖人信息
	
	private String lotteryName;  //活动名称
	
	private String startTime;    //抽奖开始时间
	
	private String endTime;     //抽奖结束时间
	
	private String lotteryTerm;     //中奖等级
	
	private String awardInfo;   //奖品信息
	
	private String state ;     //状态（未领取、已领取）
	
	private String isConsume;    //针对电影票验证的状态是否被消费

	public LotteryWinnerDto getLotteryWinnerDto() {
		return lotteryWinnerDto;
	}

	public void setLotteryWinnerDto(LotteryWinnerDto lotteryWinnerDto) {
		this.lotteryWinnerDto = lotteryWinnerDto;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
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

	public String getLotteryTerm() {
		return lotteryTerm;
	}

	public void setLotteryTerm(String lotteryTerm) {
		this.lotteryTerm = lotteryTerm;
	}

	public String getAwardInfo() {
		return awardInfo;
	}

	public void setAwardInfo(String awardInfo) {
		this.awardInfo = awardInfo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLotteryWinnerDtoId() {
		return lotteryWinnerDtoId;
	}

	public void setLotteryWinnerDtoId(String lotteryWinnerDtoId) {
		this.lotteryWinnerDtoId = lotteryWinnerDtoId;
	}

	public String getIsConsume() {
		return isConsume;
	}

	public void setIsConsume(String isConsume) {
		this.isConsume = isConsume;
	}
	
	
	
}
