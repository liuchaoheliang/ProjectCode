package com.froad.CB.po.transaction;

import java.io.Serializable;

/**
 * 类描述：积分返利响应实体
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Dec 10, 2012 2:11:00 PM
 */
public class Rebate implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String accountMarkedFrom;//userid

	private String accountMarkedTypeFrom;
	
	private String accountMarkedTo;//userid

	private String accountMarkedTypeTo;

	private String fillAndPresendInfoNo;

	private String objectNo;

	private String orgNo;

	private String pointsCateNo;

	private String orgPoints;

	private String points;

	private String fromAccountId;
	
	private String toAccountId;

	private String partnerNo;

	private String requestNo;

	private String resultCode;

	private String charset;

	private String signType;

	private String signMsg;

	private String receiveReqTime;
	
	/**积分返利请求报文新增字段**/
	private String fromPhone;
	
	private String toPhone;

	public Rebate() {
	}

	public String getFillAndPresendInfoNo() {
		return fillAndPresendInfoNo;
	}

	public void setFillAndPresendInfoNo(String fillAndPresendInfoNo) {
		this.fillAndPresendInfoNo = fillAndPresendInfoNo;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getPointsCateNo() {
		return pointsCateNo;
	}

	public void setPointsCateNo(String pointsCateNo) {
		this.pointsCateNo = pointsCateNo;
	}

	public String getOrgPoints() {
		return orgPoints;
	}

	public void setOrgPoints(String orgPoints) {
		this.orgPoints = orgPoints;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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

	public String getReceiveReqTime() {
		return receiveReqTime;
	}

	public void setReceiveReqTime(String receiveReqTime) {
		this.receiveReqTime = receiveReqTime;
	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public String getAccountMarkedFrom() {
		return accountMarkedFrom;
	}

	public void setAccountMarkedFrom(String accountMarkedFrom) {
		this.accountMarkedFrom = accountMarkedFrom;
	}

	public String getAccountMarkedTypeFrom() {
		return accountMarkedTypeFrom;
	}

	public void setAccountMarkedTypeFrom(String accountMarkedTypeFrom) {
		this.accountMarkedTypeFrom = accountMarkedTypeFrom;
	}

	public String getAccountMarkedTo() {
		return accountMarkedTo;
	}

	public void setAccountMarkedTo(String accountMarkedTo) {
		this.accountMarkedTo = accountMarkedTo;
	}

	public String getAccountMarkedTypeTo() {
		return accountMarkedTypeTo;
	}

	public void setAccountMarkedTypeTo(String accountMarkedTypeTo) {
		this.accountMarkedTypeTo = accountMarkedTypeTo;
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

}
