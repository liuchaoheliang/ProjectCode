package com.froad.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 支付流水持久数据结构
 * ClassName: Payment
 * Function: TODO ADD FUNCTION
 * date: 2015-9-10 下午1:50:42
 *
 * @author Zxy
 * @version
 */
public class Payment implements Serializable{

	private static final long serialVersionUID = -6800682589963751042L;

	@JSONField(name = "create_time")
	private Date createTime; //数据初始化入库时间
	
	@JSONField(name = "payment_id")
	private String paymentId; //支付流水主键id
	
	@JSONField(name = "client_id")
	private String clientId; //客户端编号
	
	@JSONField(name = "order_id")
	private String orderId; //订单编号
	
	@JSONField(name = "bill_no")
	private String billNo; //支付系统唯一标识
	
	@JSONField(name = "payment_type")
	private Integer paymentType; //支付流水类型
	
	@JSONField(name = "payment_value")
	private Integer paymentValue; //支付值
	
	@JSONField(name = "payment_type_details")
	private Integer paymentTypeDetails; //支付现金类型
	
	@JSONField(name = "step")
	private Integer step; //当前支付进行步骤
	
	@JSONField(name = "is_enable")
	private Boolean isEnable; //当前是否为有效记录
	
	@JSONField(name = "payment_status")
	private String paymentStatus; //当前支付状态
	
	@JSONField(name = "payment_org_no")
	private String paymentOrgNo; //支付机构号
	
	@JSONField(name = "from_user_name")
	private String fromUserName; //支付人会员帐号
	
	@JSONField(name = "from_user_member_code")
	private Long fromUserMemberCode; //支付人会员memberCode
	
	@JSONField(name = "from_account_name")
	private String fromAccountName; //支付人帐户名
	
	@JSONField(name = "from_account_no")
	private String fromAccountNo; //支付人账户号
	
	@JSONField(name = "from_phone")
	private String fromPhone; //支付人贴膜卡手机号码|会员手机号
	
	@JSONField(name = "result_code")
	private String resultCode; //受理code
	
	@JSONField(name = "result_desc")
	private String resultDesc; //受理请求描述
	
	@JSONField(name = "notice_result_code")
	private String noticeResultCode; //收到支付通知结果码（现金，若结果码正常保留state）
	
	@JSONField(name = "notice_result_desc")
	private String noticeResultDesc; //收到支付通知的结果描述
	
	@JSONField(name = "verify_result_code")
	private String verifyResultCode; //主动查询支付结果结果码（现金，若结果码正常保留state）
	
	@JSONField(name = "verify_result_desc")
	private String verifyResultDesc; //主动查询支付结果结果描述
	
	@JSONField(name = "settle_to_account_name")
	private String settleToAccountName; //结算到对应帐户名
	
	@JSONField(name = "settle_to_account_no")
	private String settleToAccountNo; //结算到对应账户帐号
	
	@JSONField(name = "settle_merchant_and_outlet_id")
	private String settleMerchantAndOutletId; //传给OpenAPI的反查参数（用于反查结算收款账户信息）
	
	@JSONField(name = "point_rate")
	private Integer pointRate; //积分比例
	
	@JSONField(name = "payment_reason")
	private String paymentReason; //支付流水业务类型
	
	@JSONField(name = "is_dispose_exception")
	private String isDisposeException; //是否处理过异常  1-已处理 0-默认，未处理
	
	@JSONField(name = "remark")
	private String remark; //备注信息

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

	public Integer getPaymentValue() {
		return paymentValue;
	}

	public void setPaymentValue(Integer paymentValue) {
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

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getFromUserMemberCode() {
		return fromUserMemberCode;
	}

	public void setFromUserMemberCode(Long fromUserMemberCode) {
		this.fromUserMemberCode = fromUserMemberCode;
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

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
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

	public String getNoticeResultCode() {
		return noticeResultCode;
	}

	public void setNoticeResultCode(String noticeResultCode) {
		this.noticeResultCode = noticeResultCode;
	}

	public String getNoticeResultDesc() {
		return noticeResultDesc;
	}

	public void setNoticeResultDesc(String noticeResultDesc) {
		this.noticeResultDesc = noticeResultDesc;
	}

	public String getVerifyResultCode() {
		return verifyResultCode;
	}

	public void setVerifyResultCode(String verifyResultCode) {
		this.verifyResultCode = verifyResultCode;
	}

	public String getVerifyResultDesc() {
		return verifyResultDesc;
	}

	public void setVerifyResultDesc(String verifyResultDesc) {
		this.verifyResultDesc = verifyResultDesc;
	}

	public String getSettleToAccountName() {
		return settleToAccountName;
	}

	public void setSettleToAccountName(String settleToAccountName) {
		this.settleToAccountName = settleToAccountName;
	}

	public String getSettleToAccountNo() {
		return settleToAccountNo;
	}

	public void setSettleToAccountNo(String settleToAccountNo) {
		this.settleToAccountNo = settleToAccountNo;
	}

	public String getSettleMerchantAndOutletId() {
		return settleMerchantAndOutletId;
	}

	public void setSettleMerchantAndOutletId(String settleMerchantAndOutletId) {
		this.settleMerchantAndOutletId = settleMerchantAndOutletId;
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

	public String getIsDisposeException() {
		return isDisposeException;
	}

	public void setIsDisposeException(String isDisposeException) {
		this.isDisposeException = isDisposeException;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
