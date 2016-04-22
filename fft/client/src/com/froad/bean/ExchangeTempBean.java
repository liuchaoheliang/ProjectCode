package com.froad.bean;

import com.froad.client.user.User;
import com.froad.common.TranCommand;

/** 
 * @author FQ 
 * @date 2013-4-6 下午01:11:48
 * @version 1.0
 * 积分兑换临时bean
 */
public class ExchangeTempBean {
	private String goodsRackId;//上架商品ID
	private String goodsCategoryId;//分类ID
	private String sellerId;//卖家ID
	private String payMethod;//支付方式
	private String payChannel;//支付渠道
	private Integer buyNumber=1;//购买数量
	private String usePointRaioValue;//使用积分数
	private String mobile;//充值号码 或 彩票中彩联系电话
	private String period;//期号
	private String lotteryValue;//投注号码
	/*******临时变量,用于积分兑换的传值********/
	private String totalAmount;//需要支付的金额
	private String totalPoints;//需要支付的积分
	/********页面暂未转递的参数(需要从页面传过来)*******/
	private String lotteryNo="FC_SSQ";//彩票编码
	private String playType="1";//玩法
	private String numType="1";////单复和合胆
	private String buyAmount="1";//投注倍数
	private String amount="2";//投注金额
	private String numCount="1";//投注注数
	
	/**session或常量类里的值**/
	private String clientType;//客户端类型
	private String userId;//用户编号
	private User user;
	
	/***从数据库里查出来的值，页面不需要为这些字段设值**/
	private String merchantId;//商户编号
	private String cashPricing;//现金定价
	private String fftPointPricing;//分分通积分定价
	private String bankPointPricing;//银行积分定价
	private String fftpointCashPricing;//(分分通积分+现金)定价
	private String bankpointCashPricing;//(银行积分+现金)定价
	private String fftpointcashRatioPricing;//(分分通积分+现金)范围
	private String bankpointcashRatioPricing;//(银行积分+现金)范围
	private String presentPointsValue;//增送积分数(积分兑换商品)
	private Integer presentRuleId;//积分增送规则编号
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	public String getGoodsRackId() {
		return goodsRackId;
	}
	public void setGoodsRackId(String goodsRackId) {
		this.goodsRackId = goodsRackId;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Integer getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLotteryValue() {
		return lotteryValue;
	}
	public void setLotteryValue(String lotteryValue) {
		this.lotteryValue = lotteryValue;
	}
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public String getUsePointRaioValue() {
		return usePointRaioValue;
	}
	public void setUsePointRaioValue(String usePointRaioValue) {
		this.usePointRaioValue = usePointRaioValue;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getPayChannel() {
		if(payMethod==null||"".equals(payMethod)){
			return "";
		}
		if(payMethod.equals(TranCommand.CASH)||
				payMethod.equals(TranCommand.POINTS_FFT_CASH)||
				payMethod.equals(TranCommand.POINTS_BANK_CASH)||
				payMethod.equals(TranCommand.POINTS_FFT_CASH_SCOPE)||
				payMethod.equals(TranCommand.POINTS_BANK_CASH_SCOPE)){
			return TranCommand.PAY_CHANNEL_PHONE;
		}
		if(payMethod.equals(TranCommand.POINTS_FFT_ALPAY_SCOPE)||
				payMethod.equals(TranCommand.POINTS_BANK_ALPAY_SCOPE)||
						payMethod.equals(TranCommand.ALPAY)){
			return TranCommand.PAY_CHANNEL_ALIPAY;
		}
		return "";
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCashPricing() {
		return cashPricing;
	}
	public void setCashPricing(String cashPricing) {
		this.cashPricing = cashPricing;
	}
	public String getFftPointPricing() {
		return fftPointPricing;
	}
	public void setFftPointPricing(String fftPointPricing) {
		this.fftPointPricing = fftPointPricing;
	}
	public String getBankPointPricing() {
		return bankPointPricing;
	}
	public void setBankPointPricing(String bankPointPricing) {
		this.bankPointPricing = bankPointPricing;
	}
	public String getFftpointCashPricing() {
		return fftpointCashPricing;
	}
	public void setFftpointCashPricing(String fftpointCashPricing) {
		this.fftpointCashPricing = fftpointCashPricing;
	}
	public String getBankpointCashPricing() {
		return bankpointCashPricing;
	}
	public void setBankpointCashPricing(String bankpointCashPricing) {
		this.bankpointCashPricing = bankpointCashPricing;
	}
	public String getFftpointcashRatioPricing() {
		return fftpointcashRatioPricing;
	}
	public void setFftpointcashRatioPricing(String fftpointcashRatioPricing) {
		this.fftpointcashRatioPricing = fftpointcashRatioPricing;
	}
	public String getBankpointcashRatioPricing() {
		return bankpointcashRatioPricing;
	}
	public void setBankpointcashRatioPricing(String bankpointcashRatioPricing) {
		this.bankpointcashRatioPricing = bankpointcashRatioPricing;
	}
	public String getLotteryNo() {
		return lotteryNo;
	}
	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public String getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(String buyAmount) {
		this.buyAmount = buyAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getNumCount() {
		return numCount;
	}
	public void setNumCount(String numCount) {
		this.numCount = numCount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPresentPointsValue() {
		return presentPointsValue;
	}
	public void setPresentPointsValue(String presentPointsValue) {
		this.presentPointsValue = presentPointsValue;
	}
	public Integer getPresentRuleId() {
		return presentRuleId;
	}
	public void setPresentRuleId(Integer presentRuleId) {
		this.presentRuleId = presentRuleId;
	}
}
