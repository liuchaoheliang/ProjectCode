package com.froad.common.beans;

import java.io.Serializable;
import java.util.Date;

public class PaymentQueryBean implements Serializable{
	
	private static final long serialVersionUID = -6800682589963751042L;

	private Long id; //主键id
	
	private Date createTime; //创建时间
	
	private String paymentId; //
	
	private String clientId; //客户端编号
	
	private String orderId; //关联订单
	
	private String billNo;  //bill支付Num 或者积分支付的payPointNo
	
	private Integer paymentType; //支付类型  1-方付通积分 2-现金 3-银行积分
	
	private Integer paymentValue; //支付额
	
	private Integer paymentTypeDetails;//支付详细   --> 同cashType枚举  0 代表积分支付
	
	private Integer step; //当前步骤  1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果
	
	private Boolean isEnable; //是否有效
	
	private String paymentStatus; //支付状态 1、等待支付 2、支付请求发送成功 3、支付请求发送异常  4、支付成功 5、支付失败 
	
	private String paymentOrgNo; //支付机构号
	
	private String fromAccountName; 
	
	private String fromAccountNo;
	
	private String toAccountName;
	
	private String toAccountNo;
	
	private String fromPhone;
	
	private String toPhone;
	
	private String fromUserName;
	
	private String toUserName;
	
	private String resultCode;
	
	private String resultDesc;
	
	private String remark;
	
	private String autoRefund; //0 未发生自动退款 1 自动退款失败 2 自动退款成功

	private Integer pointRate; //积分比例
	
	private String paymentReason; //0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
	
	private String isDisposeException; //异常支付流水是否处理   0 - 默认    1 - 已处理
	
	private String refundPointsBillNo; //退积分的refundPointsNo
	
	
	//---
	private Date startTime;
	private Date endTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public Integer getPaymentTypeDetails() {
		return paymentTypeDetails;
	}

	public void setPaymentTypeDetails(Integer paymentTypeDetails) {
		this.paymentTypeDetails = paymentTypeDetails;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPointRate() {
		return pointRate;
	}

	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public Integer getPaymentValue() {
		return paymentValue;
	}

	public void setPaymentValue(Integer paymentValue) {
		this.paymentValue = paymentValue;
	}

	public String getAutoRefund() {
		return autoRefund;
	}

	public void setAutoRefund(String autoRefund) {
		this.autoRefund = autoRefund;
	}

	public String getIsDisposeException() {
		return isDisposeException;
	}

	public void setIsDisposeException(String isDisposeException) {
		this.isDisposeException = isDisposeException;
	}

	public String getRefundPointsBillNo() {
		return refundPointsBillNo;
	}

	public void setRefundPointsBillNo(String refundPointsBillNo) {
		this.refundPointsBillNo = refundPointsBillNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
