package com.froad.CB.po.lottery;


	/**
	 * 类描述：话费充值的通知
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 18, 2013 8:35:26 PM 
	 */
public class HFCZNotice {

	private String respCode;
	
	private String respMsg;
	
	private String version;
	
	private String merchantNo;
	
	private String signType;
	
	private String signMsg;
	
	private String receiveReqTime;
	
	private String busiCode;
	
	private String tranID;
	
	private String salePrice;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getTranID() {
		return tranID;
	}

	public void setTranID(String tranID) {
		this.tranID = tranID;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
}
