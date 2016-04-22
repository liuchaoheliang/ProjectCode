package com.froad.action.web.tran;

import com.froad.client.hfcz.Hfcz;
import com.froad.client.lottery.Lottery;

public class TransAddtionalInfoVo {
	private Lottery lottery;
	private String lotteryPeriod;
	private String lotteryPlayType;// 玩法
	private String lotteryContent;
	private String lotteryContentRedBall;
	private String lotteryContentBlueBall;
	private Hfcz hfcz;
	private String phone;
	private String confirmedPhone;
	private String accountNo;
	private String accountName;
	private String noticePhone;
	
	public String getConfirmedPhone() {
		return confirmedPhone;
	}
	public void setConfirmedPhone(String confirmedPhone) {
		this.confirmedPhone = confirmedPhone;
	}
	public String getLotteryPlayType() {
		return lotteryPlayType;
	}
	public void setLotteryPlayType(String lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}
	public String getLotteryContent() {
		return lotteryContent;
	}
	public void setLotteryContent(String lotteryContent) {
		this.lotteryContent = lotteryContent;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getNoticePhone() {
		return noticePhone;
	}
	public void setNoticePhone(String noticePhone) {
		this.noticePhone = noticePhone;
	}
	public String getLotteryPeriod() {
		return lotteryPeriod;
	}
	public void setLotteryPeriod(String lotteryPeriod) {
		this.lotteryPeriod = lotteryPeriod;
	}
	public String getLotteryContentRedBall() {
		return lotteryContentRedBall;
	}
	public void setLotteryContentRedBall(String lotteryContentRedBall) {
		this.lotteryContentRedBall = lotteryContentRedBall;
	}
	public String getLotteryContentBlueBall() {
		return lotteryContentBlueBall;
	}
	public void setLotteryContentBlueBall(String lotteryContentBlueBall) {
		this.lotteryContentBlueBall = lotteryContentBlueBall;
	}
	public Lottery getLottery() {
		return lottery;
	}
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	public Hfcz getHfcz() {
		return hfcz;
	}
	public void setHfcz(Hfcz hfcz) {
		this.hfcz = hfcz;
	}
	
	
}
