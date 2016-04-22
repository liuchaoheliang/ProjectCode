/**
 * 文件名：PointsCard.java
 * 版本信息：Version 1.0
 * 日期：2014年8月18日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.thirdparty.dto.request.points;

/**
 * 类描述：充值卡信息
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年8月18日 上午10:50:43
 */
public class PointsCard {
	private String cardPassword;// 充值卡卡密
	private String cardNo;// 充值卡卡号

	private String expTime;// 过期时间
	private String createTime;// 创建时间

	private String cardChannel;// 充值渠道
	private String amount;// 金额
	private String usableAmount;// 可用余额

	private String depositAmount;// 充值金额

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpTime() {
		return expTime;
	}

	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCardChannel() {
		return cardChannel;
	}

	public void setCardChannel(String cardChannel) {
		this.cardChannel = cardChannel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUsableAmount() {
		return usableAmount;
	}

	public void setUsableAmount(String usableAmount) {
		this.usableAmount = usableAmount;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

}
