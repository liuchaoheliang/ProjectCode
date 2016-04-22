package com.froad.cbank.coremodule.module.normal.user.vo;

/**
 * 订单支付信息
 */
public class PayOrdersVo {

	private String clientId;

	/**
	 * 会员ID
	 */
	private long memberCode;
	/**
	 * 聚合订单Id
	 */
	private String orderId;
	
	/**
	 * 会员名
	 */
	private String userName;
	/**
	 * 积分机构号
	 */
	private String pointOrgNo;
	/**
	 * 现金机构号
	 */
	private String cashOrgNo;
	/**
	 * 支付类型
	 */
	private int payType;
	
	/**
	 * 现金支付类型
	 */
	private int cashType;
	/**
	 * 积分值
	 */
	private double pointAmount;
	/**
	 * 现金值
	 */
	private double cashAmount;
	/**
	 * 贴膜卡号码
	 */
	private String foilCardNum;
	/**
	 * 
	 */
	private String token;
	
	/**
	 * 短信验证码
	 */
	private String sms;
	/**
	 * 密码
	 */
	private String ciphertextPwd;
	/**
	 * 订单来源 ： 100-pc,200-android,300-iphone,400-ipad
	 */
	private String createSource;
	
	/**
	 * 1.联盟积分（纯积分） 2.银行积分（纯积分）3.联盟积分+银行快捷 4.银行积分+银行快捷 
	 * 5.联盟积分+贴膜卡支付 6.银行积分+贴膜卡支付 
	 * 7.纯现金快捷支付  8.纯贴膜卡支付
	 * 9.纯卡密支付 
	 * 10 联盟积分+卡密支付  11 银行积分+卡密支付
	 */
	private int type;
	
	/**
	 * couponsNo:优惠券号
	 */
	private String couponsNo;
	/**
	 * redPacketNo:红包券号
	 */
	private String redPacketNo;
	
	/**
	 * qrCode:二维码扫内容
	 */
	private String qrCode;
	

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPointOrgNo() {
		return pointOrgNo;
	}

	public void setPointOrgNo(String pointOrgNo) {
		this.pointOrgNo = pointOrgNo;
	}

	public String getCashOrgNo() {
		return cashOrgNo;
	}

	public void setCashOrgNo(String cashOrgNo) {
		this.cashOrgNo = cashOrgNo;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getCashType() {
		return cashType;
	}

	public void setCashType(int cashType) {
		this.cashType = cashType;
	}

	public double getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(double pointAmount) {
		this.pointAmount = pointAmount;
	}

	public double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getFoilCardNum() {
		return foilCardNum;
	}

	public void setFoilCardNum(String foilCardNum) {
		this.foilCardNum = foilCardNum;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getCiphertextPwd() {
		return ciphertextPwd;
	}

	public void setCiphertextPwd(String ciphertextPwd) {
		this.ciphertextPwd = ciphertextPwd;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCouponsNo() {
		return couponsNo;
	}

	public void setCouponsNo(String couponsNo) {
		this.couponsNo = couponsNo;
	}

	public String getRedPacketNo() {
		return redPacketNo;
	}

	public void setRedPacketNo(String redPacketNo) {
		this.redPacketNo = redPacketNo;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

}
