package com.froad.cbank.coremodule.normal.boss.pojo.pay;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;



/**
 * 类描述：支付记录查询实体请求类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-5-5上午10:25:52
 */
public class PaymentInfoVoReq extends Page {
	private Long id;
	private Long createTime;
	private String paymentId;//支付ID
	private String clientId;//客户端ID
	private String orderId;//订单编号
	private String billNo; //账单号
	private Integer paymentType;//
	private Double paymentValue;
	private Integer paymentTypeDetails;
	private Integer step;
	private Boolean isEnable;
	private String paymentStatus;//支付状态
	private String paymentOrgNo;
	private String fromAccountName;
	private String fromAccountNo;
	private String toAccountName;
	private String toAccountNo;
	private String fromPhone;
	private String toPhone;
	private String fromUserName;//
	private String toUserName;
	private String resultCode;
	private String resultDesc;
	private String remark;
	private String autoRefund;
	private Integer pointRate;
	private Integer type;
	private String isDisposeException;
	private String refundPointsBillNo;
	private String paymentReason;//支付流水类型
	private String beginTime;//
	private String endTime;//
	
	private String orgCodes;
	
	public String getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getPaymentStatus() {
		return paymentStatus;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAutoRefund() {
		return autoRefund;
	}
	public void setAutoRefund(String autoRefund) {
		this.autoRefund = autoRefund;
	}
	public Integer getPointRate() {
		return pointRate;
	}
	public void setPointRate(Integer pointRate) {
		this.pointRate = pointRate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getPaymentReason() {
		return paymentReason;
	}
	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
