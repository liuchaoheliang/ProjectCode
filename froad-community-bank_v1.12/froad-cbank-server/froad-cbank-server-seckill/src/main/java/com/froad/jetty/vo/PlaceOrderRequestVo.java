package com.froad.jetty.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 下单请求
 * 
 * @author wangzhangxu
 * @date 2015年4月16日 下午1:44:35
 * @version v1.0
 */
public class PlaceOrderRequestVo implements Serializable {
	
	private static final long serialVersionUID = 4502893012900884492L;
	
	/** 会员编号 */
	private Long memberCode;
	
	/** 登录令牌 */
	private String token;
	
	/** 客户ID */
	private String clientId;
	
	/** 秒杀商品ID */
	private String productId;
	
	/** 购买数量 */
	private Integer buyNum;
	
	/** 支付类型 */
	private Integer payType;
	
	/** 现金支付类型 */
	private Integer cashType;
	
	/** 积分机构号 */
	private String pointOrgNo;
	
	/** 现金机构号 */
	private String cashOrgNo;
	
	/** 积分值 */
	private Double pointAmount;
	
	/** 现金值 */
	private Double cashAmount;
	
	/** 贴膜卡卡号 */
	private String foilCardNum;
	
	/** 支付密码 */
	private String payPwd;
	
	/** 订单来源 */
	private String source;
	
	/** 短信验证码令牌 */
	private String smsToken;
	
	/** 短信验证码 */
	private String smsCode;
	
	/* ************* 填充冗余信息 ************* */
	/** 会员名称 */
	private String memberName;
	
	/** 是否是VIP用户 */
	private boolean isVip;
	
	/* ************* 收货信息 ************* */
	/** 提货类型 */
	private String deliveryType;
	/** 收货信息编号 */
	private String recvId;
	/** 接手券和短信的手机号 */
	private String phone;
	/** 预售商品自提网点|线下积分兑换网点 */
	private String orgCode;
	/** 预售商品自提网点名称|线下积分兑换网点名称 */
	private String orgName;
	
	
	/** 针对压力测试设置的参数 */
	private String mode;
	
	public PlaceOrderRequestVo(){}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getCashType() {
		return cashType;
	}

	public void setCashType(Integer cashType) {
		this.cashType = cashType;
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

	public Double getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Double pointAmount) {
		this.pointAmount = pointAmount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getFoilCardNum() {
		return foilCardNum;
	}

	public void setFoilCardNum(String foilCardNum) {
		this.foilCardNum = foilCardNum;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSmsToken() {
		return smsToken;
	}

	public void setSmsToken(String smsToken) {
		this.smsToken = smsToken;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getRecvId() {
		return recvId;
	}

	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
