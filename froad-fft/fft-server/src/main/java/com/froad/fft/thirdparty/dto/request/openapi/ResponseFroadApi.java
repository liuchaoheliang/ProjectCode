package com.froad.fft.thirdparty.dto.request.openapi;

public class ResponseFroadApi {

	private String refundOrderID;

	private String resultCode;

	private String checkResultCode;
	
	private String checkResultContent;
	
	private String trackNo;

	private String remark;

	private String signType;

	private String signMsg;

	private String transferID;
	
	private String orderID;
	
	private String froadBillNo;
	
	private String paymentURL; //支付宝返回的表单内容
	
	public String getRefundOrderID() {
		return refundOrderID;
	}

	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCheckResultCode() {
		return checkResultCode;
	}

	public void setCheckResultCode(String checkResultCode) {
		this.checkResultCode = checkResultCode;
	}

	public String getCheckResultContent() {
		return checkResultContent;
	}

	public void setCheckResultContent(String checkResultContent) {
		this.checkResultContent = checkResultContent;
	}

	public String getTransferID() {
		return transferID;
	}

	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getFroadBillNo() {
		return froadBillNo;
	}

	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}

	public String getPaymentURL() {
		return paymentURL;
	}

	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
	}

	
}
