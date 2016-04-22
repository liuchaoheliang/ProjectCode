package com.froad.CB.po;

public class Pay {

	private Integer id;
	private String trackNo;//业务跟踪号
	private String transId;//交易ID
	private String type;//支付类型(00:资金 01:积分)与RuleDetailCategory一致
	private String fromAccountName; //支付人姓名
	private String fromAccountNo;	//支付号
	private String payValue;		//支付值
	private String toAccountName;	//收款人姓名
	private String toAccountNo;		//收款人号
	private String state;  //支付状态 00-支付成功 10-支付创建 20-支付失败						
	private String step;   //步骤 1 2 3'
	private String resultCode;//结果码
	private String resultDesc;//结果码描述
	private String createTime;
	private String updateTime;
	private String remark;	
	
	private String fromAccountType;//从账户类型
	private String toAccountType;//到账户类型
	private String fromPhone;//从电话号码
	private String toPhone;//到电话号码
	private String fromId;//从哪个卖家/买家/银行/fft
	private String toId;//到哪个卖家/买家/银行/fft

	private String fromUsername;//从哪个用户名
	private String toUsername;//到哪个用户名

	private String channelType;//渠道类型
	private String channelId;//渠道ID
	private FundsChannel fundsChannel;

	private String ruleType;//规则类型
	private String ruleId;//规则ID
	private String ruleDetailId;//规则明细id
	private String isIntime;//是否及时
	private String typeDetail;//与RuleDetailType的值相同
	private String fromRole;
	private String toRole;
	
	private String goodsId;//交易品Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}

	public String getFromRole() {
		return fromRole;
	}

	public void setFromRole(String fromRole) {
		this.fromRole = fromRole;
	}

	public String getToRole() {
		return toRole;
	}

	public void setToRole(String toRole) {
		this.toRole = toRole;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo == null ? null : trackNo.trim();
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId == null ? null : transId.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getFromAccountName() {
		return fromAccountName;
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName == null ? null : fromAccountName
				.trim();
	}

	public String getFromAccountNo() {
		return fromAccountNo;
	}

	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo == null ? null : fromAccountNo
				.trim();
	}

	public String getPayValue() {
		return payValue;
	}

	public void setPayValue(String payValue) {
		this.payValue = payValue == null ? null : payValue.trim();
	}

	public String getToAccountName() {
		return toAccountName;
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName == null ? null : toAccountName
				.trim();
	}

	public String getToAccountNo() {
		return toAccountNo;
	}

	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo == null ? null : toAccountNo.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step == null ? null : step.trim();
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode == null ? null : resultCode.trim();
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc == null ? null : resultDesc.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getFromAccountType() {
		return fromAccountType;
	}

	public void setFromAccountType(String fromAccountType) {
		this.fromAccountType = fromAccountType;
	}

	public String getToAccountType() {
		return toAccountType;
	}

	public void setToAccountType(String toAccountType) {
		this.toAccountType = toAccountType;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleDetailId() {
		return ruleDetailId;
	}

	public void setRuleDetailId(String ruleDetailId) {
		this.ruleDetailId = ruleDetailId;
	}

	public String getIsIntime() {
		return isIntime;
	}

	public void setIsIntime(String isIntime) {
		this.isIntime = isIntime;
	}

	public FundsChannel getFundsChannel() {
		return fundsChannel;
	}

	public void setFundsChannel(FundsChannel fundsChannel) {
		this.fundsChannel = fundsChannel;
	}
}