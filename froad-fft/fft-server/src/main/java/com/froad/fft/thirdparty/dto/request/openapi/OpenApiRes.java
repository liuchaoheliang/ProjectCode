package com.froad.fft.thirdparty.dto.request.openapi;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: OpenApiRes.java </p>
 *<p> 描述: *-- <b>OpenApi响应结果</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月15日 下午2:58:34 </p>
 ********************************************************
 */
public class OpenApiRes {

	private String resultCode;//结果
	private String resultDesc;//结果描述
	
	private String checkResultCode;//校验结果码   00: 存在/成功|01: 不存在/失败
	private String checkResultContent;//校验结果信息
	private String paymentURL; //支付宝返回的表单内容
	private String froadBillNo;
	
	
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
