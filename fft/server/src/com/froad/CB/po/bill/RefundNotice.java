package com.froad.CB.po.bill;

/**
 * 类描述：退款通知
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Mar 23, 2013 1:33:54 PM
 */
public class RefundNotice {

	private String trackNo;
	private String refundOrderID;
	private String refundAmount;
	private String stateCode;//状态码
	private String resultCode;//结果码
	private String partnerID;
	private String charset;
	private String signType;
	private String signMsg;
	/**用于临时存放通知报文**/
	private String noticeXML;

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getRefundOrderID() {
		return refundOrderID;
	}

	public void setRefundOrderID(String refundOrderID) {
		this.refundOrderID = refundOrderID;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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

	public String getNoticeXML() {
		return noticeXML;
	}

	public void setNoticeXML(String noticeXML) {
		this.noticeXML = noticeXML;
	}
}
