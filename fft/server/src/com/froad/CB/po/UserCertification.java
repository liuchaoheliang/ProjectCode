package com.froad.CB.po;

public class UserCertification {

	private Integer id;

	private String remark;

	private String state;//状态 同Command里的通用状态

	private String updateTime;

	private String createTime;

	private String phone;//手机号

	private String certificationResult;//认证结果：1：成功 2：失败

	private String certificationType;//认证类型：见TranCommand里的checkType

	private String accountNo;//账户号

	private String accountName;//账户名
	
	private String userIdCard;//用户身份证号

	private String userId;//用户编号
	
	private String channelId;//对应fundsChannel的id
	
	private FundsChannel fundsChannel;
	
	/**用于认证通过时存放买家及买家渠道信息**/
	private Buyers buyer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getCertificationResult() {
		return certificationResult;
	}

	public void setCertificationResult(String certificationResult) {
		this.certificationResult = certificationResult == null ? null
				: certificationResult.trim();
	}

	public String getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType == null ? null
				: certificationType.trim();
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName == null ? null : accountName.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Buyers getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyers buyer) {
		this.buyer = buyer;
	}

	public FundsChannel getFundsChannel() {
		return fundsChannel;
	}

	public void setFundsChannel(FundsChannel fundsChannel) {
		this.fundsChannel = fundsChannel;
	}

	public String getUserIdCard() {
		return userIdCard;
	}

	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
		
}