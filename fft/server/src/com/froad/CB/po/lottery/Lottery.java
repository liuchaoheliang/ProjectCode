package com.froad.CB.po.lottery;

import java.util.ArrayList;
import java.util.List;




public class Lottery {
	
	private String body;			//请求响应内容
	private String other;			//商户自用内容
	private String mac;				//报文结构校验码
	private String rd;				//流水号
	private String resCode;			//响应代码
	private String message;			//返回信息提示
	private String transDateTime;	//传输日期时间
	private String period;			//当期销售期
	private String orderID;			//投注订单
	private String lotteryType;		//彩票种类
	private String lotteryNo;		//彩种编码
	private String playType;		//玩法  1为直选；2为组选3
	private String numType;			//单复和合形式  服务提供方约定
	private String amount;			//投注金额
	private String buyamount;		//投注倍数
	private String content;			//投注号码内容
	private String createTime;		//下单时间
	private String openTime;		//当期开奖时间
	private String canBuy;			//当前期能否交易
	private String status;			//订单状态
	private String flowId;			//渠道操作流水号
	private String awdAmount;		//中奖金额 税后
	private String winAmount;		//中奖金额
	private String prizeGrade;		//中奖等级
	private String prizeCount;		//中奖注数
	private String description;		//备注
	private String userId;			//渠道ID号，即商户在无线投注系统的分配的购彩代理人ID
	private String zqamount;		//追号期数
	private String isHit;			//中奖取消追号
	private String unitAmount;		//单价
	private String numCount;		//注数
	private String respCode;		//响应代码 0000表示用户接收成功
	private String respMsg;
	private List<Period> pList=new ArrayList<Period>();
	private String redball;	
	private String blueball;
	private String ID;
	//条件
	private String tranID ;
	private String providerID;
	
	private String mobilephone; //联系电话
	private String trangoodsID;
	private String checkType; //校验类型
	private String checkContent; //校验信息内容
	private String reqID; //请求编号
	private String checkResultContent; //校验结果信息
	private String userID; //用户的userid
	private String accountName; //用户的户名
	private String bankCardNum; //用户的银行卡号
	private String mobile; //贴膜卡绑定手机号
	private String startTime;//查询派奖记录中的开始时间
	private String endTime;//查询派奖记录中的结束时间
	private String startdate;
	private String enddate;
	private String rewardStatus;//中奖状态
	
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getTrangoodsID() {
		return trangoodsID;
	}
	public void setTrangoodsID(String trangoodsID) {
		this.trangoodsID = trangoodsID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getTranID() {
		return tranID;
	}
	public void setTranID(String tranID) {
		this.tranID = tranID;
	}
	public String getRedball() {
		return redball;
	}
	public void setRedball(String redball) {
		this.redball = redball;
	}
	public String getBlueball() {
		return blueball;
	}
	public void setBlueball(String blueball) {
		this.blueball = blueball;
	}
	public List<Period> getPList() {
		return pList;
	}
	public void setPList(List<Period> list) {
		pList = list;
	}
	public String getUnitAmount() {
		return unitAmount;
	}
	public void setUnitAmount(String unitAmount) {
		this.unitAmount = unitAmount;
	}
	public String getNumCount() {
		return numCount;
	}
	public void setNumCount(String numCount) {
		this.numCount = numCount;
	}
	public String getIsHit() {
		return isHit;
	}
	public void setIsHit(String isHit) {
		this.isHit = isHit;
	}
	public String getZqamount() {
		return zqamount;
	}
	public void setZqamount(String zqamount) {
		this.zqamount = zqamount;
	}
	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getRd() {
		return rd;
	}
	public void setRd(String rd) {
		this.rd = rd;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTransDateTime() {
		return transDateTime;
	}
	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBuyamount() {
		return buyamount;
	}
	public void setBuyamount(String buyamount) {
		this.buyamount = buyamount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCanBuy() {
		return canBuy;
	}
	public void setCanBuy(String canBuy) {
		this.canBuy = canBuy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
//	public String getCallBackAddr() {
//		return callBackAddr;
//	}
//	public void setCallBackAddr(String callBackAddr) {
//		this.callBackAddr = callBackAddr;
//	}
//	public String getCallBackAddr2() {
//		return callBackAddr2;
//	}
//	public void setCallBackAddr2(String callBackAddr2) {
//		this.callBackAddr2 = callBackAddr2;
//	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getPrizeGrade() {
		return prizeGrade;
	}
	public void setPrizeGrade(String prizeGrade) {
		this.prizeGrade = prizeGrade;
	}
	public String getPrizeCount() {
		return prizeCount;
	}
	public void setPrizeCount(String prizeCount) {
		this.prizeCount = prizeCount;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	public String getReqID() {
		return reqID;
	}
	public void setReqID(String reqID) {
		this.reqID = reqID;
	}
	public String getCheckResultContent() {
		return checkResultContent;
	}
	public void setCheckResultContent(String checkResultContent) {
		this.checkResultContent = checkResultContent;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankCardNum() {
		return bankCardNum;
	}
	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWinAmount() {
		return winAmount;
	}
	public void setWinAmount(String winAmount) {
		this.winAmount = winAmount;
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
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getRewardStatus() {
		return rewardStatus;
	}
	public void setRewardStatus(String rewardStatus) {
		this.rewardStatus = rewardStatus;
	}
	public void setAwdAmount(String awdAmount) {
		this.awdAmount = awdAmount;
	}
}
