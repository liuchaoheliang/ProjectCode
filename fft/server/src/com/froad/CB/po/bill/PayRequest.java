package com.froad.CB.po.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.froad.CB.common.constant.TranCommand;
import com.froad.util.Util;

/**
 * 类描述：请求支付的实体类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Jan 11, 2013 10:42:57 AM
 */
public class PayRequest implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	

	private String orderID;//订单号

	private String orderType;//订单类型 1201：直接优惠  1202：返利积分

	private String orderAmount;//订单金额

	private String orderCurrency;//币种 1：RMB

	private String orderDisplay;//用于显示的描述文字

	private String orderSubmitTime;//订单提交时间 格式：20111030225235

	private String orderFailureTime;//订单失效时间

	private String orderRemark;//备注

	/** 支付参数* */
	private String payType;//支付类型 20：贴膜卡

	private String payOrg;//支付机构号

	/** 支付-付款方参数* */
	private String payerType;//付款方类型 参考paycommand里的交易者类型

	private String payerWay;//付款方支付渠道 参考TranCommand里的支付渠道

	/**
	 * 格式：手机号码|账户号|账户名
	 * （手机号码）客户：15026409159|NONE|NONE
	 * （银行帐号）客户、商户、银行、方付通：NONE|62000123|方付通
	 **/
	private String payerMember;//付款方

	private String payerAmount;//付款金额

	/**
	 * Des加密后的字符串  密钥: froad
	 * 加密格式：账户号<账户名>
	 * 如：0000053868464012<张三>
	 * **/
	private String payerMemberMsg;//付款方信息加密串

	/**支付-收款方参数**/
	private String payeeType;

	private String payeeWay;

	private String payeeMember;

	private String payeeAmount;

	private String payeeMemberMsg;

	/**通知参数**/
	private String noticeUrl;

	private String returnUrl;

	/**系统参数**/
	private String reqID;

	private String version;

	private String client;

	private String partnerID;

	private String charset;

	private String signType;

	private String signMsg;
	
	/**不直接参与组报文**/
	private String fromAccountName;
	
	private String fromAccountNum;
	
	private String toAccountName;
	
	private String toAccountNum;
	
	/***传递账号信息时,不用传此字段,否则要传此字段****/
	private String fromPhone;
	
	/***传递账号信息时,不用传此字段,否则要传此字段****/
	private String toPhone;
	
	private String requestURL;
	
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderCurrency() {
		return orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public String getOrderDisplay() {
		return orderDisplay;
	}

	public void setOrderDisplay(String orderDisplay) {
		this.orderDisplay = orderDisplay;
	}

	public String getOrderSubmitTime() {
		return orderSubmitTime;
	}

	public void setOrderSubmitTime(String orderSubmitTime) {
		this.orderSubmitTime = orderSubmitTime;
	}

	public String getOrderFailureTime() {
		return orderFailureTime;
	}

	public void setOrderFailureTime(String orderFailureTime) {
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

	public String getPayOrg() {
		return payOrg;
	}

	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}

	public String getPayerType() {
		return payerType;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public String getPayerWay() {
		return payerWay;
	}

	public void setPayerWay(String payerWay) {
		this.payerWay = payerWay;
	}

	public String getPayerMember() {
		if(TranCommand.PAY_CHANNEL_PHONE.equals(this.payerWay)){//手机支付
			this.payerMember=fromPhone+"|NONE|NONE";
		}else if(TranCommand.PAY_CHANNEL_CARD.equals(this.payerWay)){//银行卡支付
			this.payerMember="NONE|"+this.getFromAccountNum()+"|"+this.getFromAccountName();
		}
		return payerMember;
	}

	public String getPayerMemberMsg() {
		if(TranCommand.PAY_CHANNEL_PHONE.equals(this.payerWay)){//手机支付
			this.payerMemberMsg="";
		}else if(TranCommand.PAY_CHANNEL_CARD.equals(this.payerWay)){//银行卡支付
			this.payerMemberMsg=this.fromAccountNum+"<"+this.fromAccountName+">";
		}
		return payerMemberMsg;
	}
	
	public String getPayerAmount() {
		if(payerAmount==null||"".equals(payerAmount.trim())){
			return payerAmount;
		}
		return Util.formatMoney(payerAmount.trim()).toString();
	}

	public void setPayerAmount(String payerAmount) {
		this.payerAmount = payerAmount;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public String getPayeeWay() {
		return payeeWay;
	}

	public void setPayeeWay(String payeeWay) {
		this.payeeWay = payeeWay;
	}

	public String getPayeeMember() {
		if(TranCommand.PAY_CHANNEL_CARD.equals(this.payeeWay)){
			this.payeeMember="NONE|"+this.toAccountNum+"|"+this.toAccountName;
		}else if(TranCommand.PAY_CHANNEL_PHONE.equals(this.payeeWay)){
			this.payeeMember=toPhone+"|NONE|NONE";
		}
		return payeeMember;
	}
	
	public String getPayeeMemberMsg() {
		if(TranCommand.PAY_CHANNEL_CARD.equals(this.payeeWay)){
			this.payeeMemberMsg=this.toAccountNum+"<"+this.toAccountName+">";
		}else if(TranCommand.PAY_CHANNEL_PHONE.equals(this.payeeWay)){
			this.payeeMemberMsg="";
		}
		return payeeMemberMsg;
	}

	public String getPayeeAmount() {
		if(payeeAmount==null||"".equals(payeeAmount.trim())){
			return payeeAmount;
		}
		return Util.formatMoney(new BigDecimal(payeeAmount.trim())).toString();
	}

	public void setPayeeAmount(String payeeAmount) {
		this.payeeAmount = payeeAmount;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public String getFromAccountName() {
		return fromAccountName;
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}

	public String getFromAccountNum() {
		return fromAccountNum;
	}

	public void setFromAccountNum(String fromAccountNum) {
		this.fromAccountNum = fromAccountNum;
	}

	public String getToAccountName() {
		return toAccountName;
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}

	public String getToAccountNum() {
		return toAccountNum;
	}

	public void setToAccountNum(String toAccountNum) {
		this.toAccountNum = toAccountNum;
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

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	
	
}
