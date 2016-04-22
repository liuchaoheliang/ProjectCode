package com.froad.fft.thirdparty.dto.response.points;

public class System {

	private String partnerNo;//商户编号
	private String resultCode;//结果编码
	private String charset;//编码方式
	private String signType;//签名类型
	private String signMsg;//签名字符串
	private String receiveReqTime;//接收到请求的时间
	
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
	
	
}
