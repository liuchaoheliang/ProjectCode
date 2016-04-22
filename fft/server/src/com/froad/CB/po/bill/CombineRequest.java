package com.froad.CB.po.bill;

import java.util.*;

/**
 * 合并支付请求参数
 */
public class CombineRequest {
	private List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

	// 订单参数区
	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 订单总金额
	 */
	private String totalAmount;
	
	private String orderDisplay;

	/**
	 * 订单货币
	 */
	private String orderCurrency = "1";

	/**
	 * 订单提交时间
	 */
	private Date orderSubmitTime;

	/**
	 * 订单失效时间
	 */
	private Date orderFailureTime;

	/**
	 * 订单扩展信息
	 */
	private String orderRemark;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 手机验证码
	 */
	private String mobileToken;

	/**
	 * 签约协议号
	 */
	private String fftSignNo;

	/**
	 * 支付机构
	 */
	private String payOrg;

	/**
	 * 付款方会员标识
	 */
	private String payerMember;

	/**
	 * 付款方会员身份
	 */
	private String payerIdentity;

	/**
	 * 收款方会员标识
	 */
	private String payeeMember;

	/**
	 * 收款方会员身份
	 */
	private String payeeIdentity;

	/**
	 * 设置收款方在方付通的会员身份。
	 *	10：方付通（默认值）  20：方付通商户
	 */
	private String payDirect;

	// 通知参数区域

	/**
	 * 消息通知地址
	 */
	private String noticeUrl;

	/**
	 * 设置商户方用于接收异步响应通知的地址。
	 */
	private String returnUrl;

	// 系统参数区域

	/**
	 * 请求编号
	 */
	private String reqID;

	/**
	 * 合作伙伴ID
	 */
	private String partnerID;

	/**
	 * 编码方式
	 */
	private String charset;

	/**
	 * 签名类型
	 */
	private String signType;

	/**
	 * 签名字符串
	 */
	private String signMsg;

	/**
	 * 客户端编号
	 */
	private String client;

	/**
	 * 版本号
	 */
	private String version;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderCurrency() {
		return orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public Date getOrderSubmitTime() {
		return orderSubmitTime;
	}

	public void setOrderSubmitTime(Date orderSubmitTime) {
		this.orderSubmitTime = orderSubmitTime;
	}

	public Date getOrderFailureTime() {
		return orderFailureTime;
	}

	public void setOrderFailureTime(Date orderFailureTime) {
		this.orderFailureTime = orderFailureTime;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMobileToken() {
		return mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	public String getFftSignNo() {
		return fftSignNo;
	}

	public void setFftSignNo(String fftSignNo) {
		this.fftSignNo = fftSignNo;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}

	public String getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(String payerMember) {
		this.payerMember = payerMember;
	}

	public String getPayerIdentity() {
		return payerIdentity;
	}

	public void setPayerIdentity(String payerIdentity) {
		this.payerIdentity = payerIdentity;
	}

	public String getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(String payeeMember) {
		this.payeeMember = payeeMember;
	}

	public String getPayeeIdentity() {
		return payeeIdentity;
	}

	public void setPayeeIdentity(String payeeIdentity) {
		this.payeeIdentity = payeeIdentity;
	}

	public String getPayDirect() {
		return payDirect;
	}

	public void setPayDirect(String payDirect) {
		this.payDirect = payDirect;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getOrderDisplay() {
		return orderDisplay;
	}

	public void setOrderDisplay(String orderDisplay) {
		this.orderDisplay = orderDisplay;
	}
}
