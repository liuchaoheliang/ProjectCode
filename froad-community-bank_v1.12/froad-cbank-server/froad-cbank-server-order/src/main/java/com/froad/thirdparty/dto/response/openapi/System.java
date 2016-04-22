package com.froad.thirdparty.dto.response.openapi;

public class System {

	private String resultCode;

	private String partnerID;

	private String charset;

	private String signType;

	private String signMsg;

	private String errorMsg;
	
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
}
