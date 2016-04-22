package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

public class PaymentMongo implements Serializable {

	private static final long serialVersionUID = 7272338008030195518L;
	
	@JSONField(name="_id", serialize=true, deserialize=true)
	private String id;
	
	@JSONField(name="create_time", serialize=true, deserialize=true)
	private Long	createTime; 		//创建时间
	
	@JSONField(name="payment_id", serialize=true, deserialize=true)
	private String 	paymentId; 			//支付ID
	
	@JSONField(name="client_id", serialize=true, deserialize=true)
	private String 	clientId; 			//客户端编号
	
	@JSONField(name="order_id", serialize=true, deserialize=true)
	private String 	orderId; 			//订单ID
	
	@JSONField(name="bill_no", serialize=true, deserialize=true)
	private String 	billNo;  			//积分或者现⾦金⽀支付的⽀支付系统返回号
	
	@JSONField(name="payment_type", serialize=true, deserialize=true)
	private Integer paymentType; 		//支付类型  1-积分 2-现金
	
	@JSONField(name="payment_reason", serialize=true, deserialize=true)
	private String paymentReason;		//支付说明
	
	@JSONField(name="payment_value", serialize=true, deserialize=true)
	private Double 	paymentValue; 		//支付额
	
	@JSONField(name="payment_type_details", serialize=true, deserialize=true)
	private Integer	paymentTypeDetails;	//支付详细   --> 同cashType枚举  0 代表积分支付
	
	@JSONField(name="step", serialize=true, deserialize=true)
	private Integer	step; 				//当前步骤  1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果
	
	@JSONField(name="payment_status", serialize=true, deserialize=true)
	private String	paymentStatus; 		//支付状态 1、等待支付 2、支付请求发送成功 3、支付请求发送异常  4、支付成功 5、支付失败
	
	@JSONField(name="is_enable", serialize=true, deserialize=true)
	private Boolean isEnable; 			//是否有效
	
	@JSONField(name="auto_refund", serialize=true, deserialize=true)
	private String  autoRefund;		//是否自动退款
	
	@JSONField(name="point_rate", serialize=true, deserialize=true)
	private String  pointRate;
	
	@JSONField(name="payment_org_no", serialize=true, deserialize=true)
	private String 	paymentOrgNo; 		//支付机构号
	
	@JSONField(name="from_account_name", serialize=true, deserialize=true)
	private String 	fromAccountName; 	//从账户名（现⾦金）
	
	@JSONField(name="from_account_no", serialize=true, deserialize=true)
	private String 	fromAccountNo;		//从账户号
	
	@JSONField(name="to_account_name", serialize=true, deserialize=true)
	private String 	toAccountName;		//到账户名
	
	@JSONField(name="to_account_no", serialize=true, deserialize=true)
	private String 	toAccountNo;		//到账户号
	
	@JSONField(name="from_phone", serialize=true, deserialize=true)
	private String 	fromPhone;			//从手机银行（贴膜卡）
	
	@JSONField(name="to_phone", serialize=true, deserialize=true)
	private String 	toPhone;			//到手机银行
	
	@JSONField(name="from_user_name", serialize=true, deserialize=true)
	private String 	fromUserName;		//从用户（积分）
	
	@JSONField(name="to_user_name", serialize=true, deserialize=true)
	private String 	toUserName;			//到用户
	
	@JSONField(name="result_code", serialize=true, deserialize=true)
	private String 	resultCode;			//结果码
	
	@JSONField(name="result_desc", serialize=true, deserialize=true)
	private String 	resultDesc;			//结果码描述
	
	@JSONField(name="remark", serialize=true, deserialize=true)
	private String 	remark;				//备注
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}



	public String getPaymentId() {
		return paymentId;
	}



	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}



	public String getClientId() {
		return clientId;
	}



	public void setClientId(String clientId) {
		this.clientId = clientId;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getBillNo() {
		return billNo;
	}



	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}



	public Integer getPaymentType() {
		return paymentType;
	}



	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}



	public String getPaymentReason() {
		return paymentReason;
	}



	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}



	public Double getPaymentValue() {
		return paymentValue;
	}



	public void setPaymentValue(Double paymentValue) {
		this.paymentValue = paymentValue;
	}



	public Integer getPaymentTypeDetails() {
		return paymentTypeDetails;
	}



	public void setPaymentTypeDetails(Integer paymentTypeDetails) {
		this.paymentTypeDetails = paymentTypeDetails;
	}



	public Integer getStep() {
		return step;
	}



	public void setStep(Integer step) {
		this.step = step;
	}



	public String getPaymentStatus() {
		return paymentStatus;
	}



	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}



	public Boolean getIsEnable() {
		return isEnable;
	}



	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}



	public String getAutoRefund() {
		return autoRefund;
	}



	public void setAutoRefund(String autoRefund) {
		this.autoRefund = autoRefund;
	}



	public String getPointRate() {
		return pointRate;
	}



	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}



	public String getPaymentOrgNo() {
		return paymentOrgNo;
	}



	public void setPaymentOrgNo(String paymentOrgNo) {
		this.paymentOrgNo = paymentOrgNo;
	}



	public String getFromAccountName() {
		return fromAccountName;
	}



	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}



	public String getFromAccountNo() {
		return fromAccountNo;
	}



	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}



	public String getToAccountName() {
		return toAccountName;
	}



	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}



	public String getToAccountNo() {
		return toAccountNo;
	}



	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
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



	public String getFromUserName() {
		return fromUserName;
	}



	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}



	public String getToUserName() {
		return toUserName;
	}



	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}



	public String getResultCode() {
		return resultCode;
	}



	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}



	public String getResultDesc() {
		return resultDesc;
	}



	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String toString(){
		return JSonUtil.toJSonString(this);
	}
	
}
