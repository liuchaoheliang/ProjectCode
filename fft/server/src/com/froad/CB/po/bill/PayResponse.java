package com.froad.CB.po.bill;


	/**
	 * 类描述：账单平台的响应实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:30:23 PM 
	 */
public class PayResponse {

	private String orderId;

	private String froadBillNo;

	private String resultCode;

	private String signType;

	private String signMsg;
	
	//-------------------------------//
	private String respCode;
	
	private String respMsg;
	
	private String remark;
	
	public PayResponse(){}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFroadBillNo() {
		return froadBillNo;
	}

	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
