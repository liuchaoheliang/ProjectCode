package com.froad.CB.po.bill;


	/**
	 * 类描述：积分提现通知
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 3, 2014 2:41:06 PM 
	 */
public class WithdrawNotice {

	private String cashPointsNo;//积分提现编号

	private String objectNo;//订单号

	private String transferId;//转账成功的编号
		
	private String points;//提现的积分数

	private String stateCode;//状态码 成功|失败

	private String remark;//提现失败原因

	private String partnerNo;//商户编号

	private String resultCode;//结果码

	private String receiveReqTime;//接收到请求的时间

	private String charset;//编码方式

	private String signType;//签名类型

	private String signMsg;//签名字符串

	public String getCashPointsNo() {
		return cashPointsNo;
	}

	public void setCashPointsNo(String cashPointsNo) {
		this.cashPointsNo = cashPointsNo;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReceiveReqTime() {
		return receiveReqTime;
	}

	public void setReceiveReqTime(String receiveReqTime) {
		this.receiveReqTime = receiveReqTime;
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

	
}
